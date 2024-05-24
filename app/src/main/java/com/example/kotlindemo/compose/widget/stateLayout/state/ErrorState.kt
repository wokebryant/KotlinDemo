package com.example.kotlindemo.compose.widget.stateLayout.state

import com.example.kotlindemo.R

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/12
 */

/**
 * 错误页
 */
data class ErrorState(
    val errorImage: Int,
    val errorTip: String,
    val btnText: String
)

val defaultErrorState = ErrorState(
    errorImage = R.drawable.core_common_error,
    errorTip = "网络不太给力，刷新试试吧～",
    btnText = "重试"
)