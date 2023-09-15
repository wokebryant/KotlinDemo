package com.example.kotlindemo.task.microenterprises

import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.example.kotlindemo.task.microenterprises.bean.MicroResumeOnlineJobBean
import com.example.kotlindemo.task.microenterprises.bean.MircoResumeJobBean
import com.zhaopin.list.multitype.loadmore.model.LoadMoreStatus

/**
 * @Description 小微企业简历列表数据模型
 * @Author LuoJia
 * @Date 2023/8/29
 */
data class MicroResumeHomeState (
    val jobList: List<MircoResumeJobBean> = emptyList()
) : IUiState

sealed class MicroResumeHomeEvent : IUiEvent {

}

data class MicroResumeListState (
    val loadList: List<Any> = emptyList(),
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

sealed class MicroResumeListEvent : IUiEvent {

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