package com.example.kotlindemo.compose.widget.stateLayout.state

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/12
 */
data class ErrorState(
    val errorImage: Int,
    val errorTip: String,
    val btnText: String
)

val defaultErrorState = ErrorState(
    errorImage = 0,
    errorTip = "错误页",
    btnText = "重试"
)