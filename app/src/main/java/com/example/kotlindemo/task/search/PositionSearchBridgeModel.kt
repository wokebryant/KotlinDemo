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
    /** 是否展示展开更多按钮 */
    val showExpandAll: Boolean = false,
    /** 搜索列表（搜索历史和订阅记录） */
    val searchList: List<Any>? = emptyList(),
    /** 职位推荐列表 */
    val recommendData: SearchRecommendJobSate? = null,
    /** 广告Banner列表 */
    val bannerList: List<Any>? = emptyList(),
    /** 关键词联想列表 */
    val keyWordList: List<String>? = emptyList(),
    /** 页面状态 */
    val pageState: PageState = PageState.Loading
): IUiState

sealed class SearchBridgeEvent : IUiEvent {
    /** 展示取消订阅并删除全部历史记录弹窗 */
    object ShowUnsubscribeAndClearAllDialog : SearchBridgeEvent()
    /** 展示删除全部历史记录弹窗 */
    object ShowClearAllDialog : SearchBridgeEvent()
    /** 展示取消订阅并删除记录弹窗 */
    data class ShowUnsubscribeAndClearDialog(val index: Int) : SearchBridgeEvent()
    /** 删除单条历史记录（无订阅） */
    data class ClearSingleHistory(val index: Int) : SearchBridgeEvent()
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

/**
 * 搜索/订阅Item UIState
 */
data class SearchItemState(
    val id: Long,
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

/**
 * 推荐职位 UIState
 */
data class SearchRecommendJobSate(
    val title: String,
    val itemList:  List<SearchRecommendJobItemState>
)

/**
 * 推荐职位 Item UIState
 */
data class SearchRecommendJobItemState(
    val code: String,
    val name: String
)