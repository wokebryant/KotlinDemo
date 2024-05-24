package com.example.kotlindemo.compose.widget.stateLayout.state

import com.example.kotlindemo.R

/**
 * @Description 错误页面
 * @Author LuoJia
 * @Date 2024/04/12
 */
data class EmptyState(
    val emptyImage: Int,
    val emptyTip: String,
)

val defaultEmptyState = EmptyState(
    emptyImage = R.drawable.core_common_no_data,
    emptyTip = "没有更多信息了～"
)