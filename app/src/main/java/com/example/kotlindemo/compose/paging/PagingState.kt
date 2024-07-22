package com.example.kotlindemo.compose.paging

/**
 * 页面状态
 */
enum class PagingState {
    /** 页面加载（骨架屏） */
    Loading,
    /** 下拉刷新 */
    Refresh,
    /** 显示内容 */
    Content,
    /** 错误页 */
    Error,
    /** 空页面 */
    Empty,
}