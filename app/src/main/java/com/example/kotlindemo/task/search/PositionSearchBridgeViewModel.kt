package com.example.kotlindemo.task.search

import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Description 职位搜索桥页面ViewModel
 * @Author LuoJia
 * @Date 2023/9/21
 */
class PositionSearchBridgeViewModel : MviBaseViewModel<SearchBridgeState, SearchBridgeEvent>() {

    override fun createInitialState() = SearchBridgeState()

    private val repository by lazy {
        PositionSearchBridgeRepository(viewModelScope)
    }

    init {
        viewModelScope.launch {
            repository.historyDataFlow.collect {
                setState {
                    copy(
                        showExpandAll = it.showExpandAll,
                        searchList = it.historyList.toUiList(),
                        pageState = PageState.Loading
                    )
                }
            }
        }
        viewModelScope.launch {
            repository.eventFlow.collect {
                setEvent(it)
            }
        }
    }

    /**
     * 请求历史记录数据
     */
    fun requestHistoryData() {
        viewModelScope.launch(exceptionHandler) {
            repository.requestHistoryData()
        }
    }

    /**
     * 请求职位推荐数据
     */
    fun requestRecommendJobData() {
        viewModelScope.launch(exceptionHandler) {
            val recommendJobData = repository.requestRecommendJobData()
            setState {
                copy(
                    recommendData = SearchRecommendJobSate(
                        title = "职位推荐",
                        itemList = recommendJobData
                    ),
                    pageState = PageState.Content
                )
            }
        }
    }

    /**
     * 请求关键词联想数据
     */
    fun requestKeywordList() {
        val test = mutableListOf("测试1", "测试2", "测试3", "测试4", "测试5", "测试6")
        setState {
            copy(
                keyWordList = test
            )
        }
    }

    /**
     * 清除所有历史记录点击
     */
    fun clearAllHistoryDataClick() {
        repository.clearAllHistoryDataClick()
    }

    /**
     * 清除单个历史记录点击
     */
    private fun clearSingleHistoryDataClick(index: Int, state: SearchItemState) {
        if (state.isSubscribe) {
            setEvent(SearchBridgeEvent.ShowUnsubscribeAndClearDialog(index))
        } else {
            setEvent(SearchBridgeEvent.ClearSingleHistory(index))
        }
    }

    /**
     * 清除历史记录
     */
    fun clearHistoryData(clearType: SearchBridgeEvent) {
        when(clearType) {
            SearchBridgeEvent.ShowClearAllDialog -> {
                repository.deleteAllHistory()
            }
            SearchBridgeEvent.ShowUnsubscribeAndClearAllDialog -> {
                repository.deleteAllHistory()
            }
            is SearchBridgeEvent.ShowUnsubscribeAndClearDialog -> {
                repository.deleteSingleHistory(true, clearType.index)
            }
            is SearchBridgeEvent.ClearSingleHistory -> {
                repository.deleteSingleHistory(false, clearType.index)
            }
        }
    }

    /**
     * 展开历史搜索记录
     */
    fun expandAllHistoryData() {
        repository.expandAllHistoryData()
    }

    private fun List<PositionSearchListItem>.toUiList(): List<SearchItemState> {
        val uiList = this.map {
            SearchItemState(
                id = it.id,
                title = it.title,
                isSubscribe = it.isSubscribe,
                workCity = it.workCityName,
                salary = it.salaryName,
                education = it.educationName,
                workExperience = it.workExperienceName,
                position = it.positionTypeName,
                companyType = it.companyTypeName,
                companyScale = it.companyScaleName,
                industry = it.industryName,
                onItemClick = { index, state ->
                    // TODO 跳转搜索结果页
                },
                onItemDelete = { index, state ->
                    clearSingleHistoryDataClick(index, state)
                }
            )
        }
        return uiList
    }


}