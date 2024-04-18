package com.example.kotlindemo.compose.widget.stateLayout.state

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/12
 */
data class EmptyState(
    val emptyImage: Int,
    val emptyTip: String,
)

val defaultEmptyState = EmptyState(
    emptyImage = 0,
    emptyTip = "空页面"
)