package com.example.kotlindemo.study.mvi

import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/19
 */
class MviListSampleViewModel(
    private val repository: MviSampleRepository = MviSampleRepository()
) : MviBaseViewModel<MviSampleState, MviSampleEvent>() {

    override fun createInitialState() = MviSampleState()

    fun requestListData(isLoadMore: Boolean) {
        viewModelScope.launch(exceptionHandler) {
            val list = mutableListOf<MviListItemState>()
            if (isLoadMore) {
                // 加载更多
                val loadMoreList = repository.requestLoadMoreListData().map { itemState ->
                    itemState.copy(
                        clickState = { clickState, mviListItemState ->
                            handleItemClick(clickState, mviListItemState)
                        }
                    )
                }
                list.addAll(loadMoreList)
            } else {
                // 全量刷新
                val fullList = repository.requestListData().map { itemState ->
                    itemState.copy(
                        clickState = { clickState, mviListItemState ->
                            handleItemClick(clickState, mviListItemState)
                        }
                    )
                }
                repository.itemList = fullList.toMutableList()
                list.addAll(fullList)
            }
            delay(1000)
            setState {
                copy(
                    loadList = list,
                    pageState = if (list.isEmpty()) PageState.Empty else PageState.Content
                )
            }
            setEvent(MviSampleEvent.ShowLoadingSuccessToast, MviSampleEvent.ShowLoadingSuccessToast)
        }
    }

    fun requestTopCardData() {
        viewModelScope.launch {
            val text = repository.requestTopData()
            setState {
                copy(
                    topCardContent = text
                )
            }
        }
    }

    private fun handleItemClick(type: ClickState, item: MviListItemState) {
        when(type) {
            ClickState.Update -> repository.update(item)
            ClickState.Remove -> repository.remove(item)
        }

        val newList = mutableListOf<MviListItemState>().apply { addAll(repository.itemList) }
        setState {
            copy(
                loadList = newList
            )
        }
    }

}