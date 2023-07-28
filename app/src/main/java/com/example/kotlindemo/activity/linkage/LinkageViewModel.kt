package com.example.kotlindemo.activity.linkage

import com.example.kotlindemo.study.mvi.core.IUiIntent
import com.example.kotlindemo.base.mvi.MviViewModel
import com.example.kotlindemo.activity.linkage.origin.TagState

/**
 * @Description 筛选页数据ViewModel
 * @Author LuoJia
 * @Date 2023/6/30
 */
class LinkageViewModel(
    private val repository: LinkageRepository = LinkageRepository()
) : MviViewModel<LinkageUiState, LinkageEvent, LinkageIntent>() {

    override fun createInitialUiState() = LinkageUiState()

    override fun handleUiIntent(intent: IUiIntent) {
        when(intent) {
            // 请求筛选数据
            is LinkageIntent.RequestLinkageData -> requestLinkageData()
            // 联动页Tag点击
            is LinkageIntent.LinkageTagClick -> handleSelectedTag(intent.items, intent.state)
            // 删除顶部选中Tag
            is LinkageIntent.RemoveSelectedTag -> {
                handleSelectedTag(intent.items, TagState.Remove)
                deleteLinkageTag(intent.items)
            }
            // 行业筛选数据变化
            is LinkageIntent.IndustrySelectedItemsChange -> industrySelectedItemsChange(intent.items)
            // 清除
            is LinkageIntent.ClearAll -> {
                handleSelectedTag(emptySet(), TagState.Clear)
                clearLinkageTag()
            }
            // 提交筛选数据
            is LinkageIntent.SubmitFilterData -> submitFilterData()
        }
    }

    /**
     * 请求筛选数据
     */
    private fun requestLinkageData() {
        val linkageList = repository.requestFilterData()
        val selectedList = mutableListOf<LinkageChildItem>().apply { addAll(repository.selectedList) }
        sendUiState {
            copy(
                selectedList = Pair(selectedList, TagState.Init),
                linkageList = linkageList
            )
        }
    }

    /**
     * 处理顶部选择条数据
     */
    private fun handleSelectedTag(items: Set<LinkageChildItem>, type: TagState) {
        when(type) {
            TagState.Add -> {
                repository.selectedList.addAll(items)
            }
            TagState.Remove -> repository.selectedList.removeAll(items)
            TagState.Update -> {
                val preItem = items.toList().getOrNull(1)
                repository.selectedList.remove(preItem)
                val currentItem = items.toList().getOrNull(0).takeIf { it != LinkageChildItem() } ?: return
                repository.selectedList.add(currentItem)
            }
            TagState.Clear -> repository.selectedList.clear()
            TagState.Slider -> {
                // 工资条滑动比较特殊,单独处理
                val salaryItem = items.toList().getOrNull(0).takeIf { it != LinkageChildItem() } ?: return
                // 先删除之前的工资Item
                val preSalaryItemList = repository.selectedList.filter { it.parentType == "salary_for_search" }
                if (preSalaryItemList.isNotEmpty()) {
                    repository.selectedList.remove(preSalaryItemList[0])
                }
                // 再添加当前的工资Item
                repository.selectedList.add(salaryItem)
            }
        }
        // 删除 不限/全部 选项
        val filterSelectedList = repository.selectedList.filter { it.code != "-1" }
        val newSelectedList = mutableListOf<LinkageChildItem>().apply { addAll(filterSelectedList) }
        sendUiState {
            copy(
                selectedList = Pair(newSelectedList, type),
                linkageList = mutableListOf()
            )
        }
    }

    /**
     * 删除二级联动页选中的Tag
     */
    private fun deleteLinkageTag(items: Set<LinkageChildItem>) {
        val childItem = items.toList()[0]
        val deleteIndexData = repository.getDeleteItemIndexData(childItem)
        sendUiEvent(LinkageEvent.OnLinkageItemRemove(
            data = deleteIndexData
        ))
    }

    /**
     * 从全部行业页面返回行业筛选数据更新
     */
    private fun industrySelectedItemsChange(data: MutableList<LinkageChildItem>) {
        repository.addBaseFilterDataToLocalData(data.toSet())
        val industrySelectedIndexList = repository.getIndustrySelectedIndexList(data.toSet())
        val filterSelectedList = repository.selectedList.filter { it.code != "-1" }
        val newSelectedList = mutableListOf<LinkageChildItem>().apply { addAll(filterSelectedList) }
        sendUiState {
            copy(
                selectedList = Pair(newSelectedList, TagState.Add),
                linkageList = mutableListOf()
            )
        }
        sendUiEvent(
            LinkageEvent.OnLinkageItemAdd(
                items = Pair(repository.industryItemIndex, industrySelectedIndexList)
            )
        )
    }

    /**
     * 清除二级联动页选中的Tag
     */
    private fun clearLinkageTag() {
        val listSize = repository.getFilterListSize()
        sendUiEvent(LinkageEvent.OnLinkageItemClear(
            size = listSize
        ))
    }

    /**
     * 提交筛选数据
     */
    private fun submitFilterData() {
        val filterData = repository.selectedList
        // TODO
    }

}