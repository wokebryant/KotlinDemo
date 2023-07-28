package com.example.kotlindemo.study.mvi

import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.zhaopin.list.multitype.loadmore.model.LoadMoreStatus

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/19
 */
data class MviSampleState(
    val loadList: List<Any> = emptyList(),
    val topCardContent: String = "",
    val onItemDelete: (() -> Unit)? = null,
    val onItemUpdate: (() -> Unit)? = null,
    /** 页面状态 */
    val pageState: PageState = PageState.Loading,
    /** 加载状态
     * Complete: 当前页加载完毕
     * Loading: 加载中
     * Fail: 加载失败
     * End: 所有页加载完毕
     * */
    val loadMoreStatus: LoadMoreStatus = LoadMoreStatus.Loading,
) : IUiState

sealed class MviSampleEvent : IUiEvent {
    object ShowLoadingSuccessToast : MviSampleEvent()
}

enum class PageState {
    /** 页面加载（骨架屏） */
    Loading,
    /** 显示内容 */
    Content,
    /** 错误页 */
    Error,
    /** 空页面 */
    Empty,
}

data class MviListItemState (
    var content: String,
    val position: Int,
    var clickState: ((ClickState, MviListItemState) -> Unit)? = null
)

sealed class ClickState {
    object Remove : ClickState()
    object Update : ClickState()
}







