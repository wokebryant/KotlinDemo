package com.example.kotlindemo.task.search

import android.graphics.pdf.PdfDocument.Page
import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState

/**
 * @Description 职位搜索桥页面State
 * @Author LuoJia
 * @Date 2023/9/21
 */

data class SearchBridgeState (
    /** 搜索列表（搜索历史和订阅记录） */
    val searchList: List<Any>? = emptyList(),
    /** 职位推荐列表 */
    val recommendList: List<Any>? = emptyList(),
    /** 广告Banner列表 */
    val bannerList: List<Any>? = emptyList(),
    /** 搜索结果列表 */
    val searchResultList: List<Any>? = emptyList(),
    /** 页面状态 */
    val pageState: PageState = PageState.Loading
) : IUiState

sealed class SearchBridgeEvent : IUiEvent {
    /** 展示取消订阅并删除全部历史记录弹窗 */
    object ShowUnsubscribeAndClearAllDialog : SearchBridgeEvent()
    /** 展示删除全部历史记录弹窗 */
    object ShowClearAllDialog : SearchBridgeEvent()
    /** 展示取消订阅并删除记录弹窗 */
    object ShowUnsubscribeAndClearDialog : SearchBridgeEvent()
}

enum class PageState {
    /** 页面加载（骨架屏） */
    Loading,
    /** 显示内容 */
    Content,
    /** 错误页 */
    Error,
    /** 空页面 */
    Empty
}

data class SearchItemState(
    val id: String,
    val title: String,
    val isSubscribe: Boolean,
    val workCity: String,
    val salary: String,
    val education: String,
    val workExperience: String,
    val position: String,
    val companyType: String,
    val companyScale: String,
    val industry: String,
    val onItemClick: (Int, SearchItemState) -> Unit,
    val onItemDelete: (Int, SearchItemState) -> Unit
)