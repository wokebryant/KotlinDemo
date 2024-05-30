package com.example.kotlindemo.task.login

import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/05/29
 */

data class LoginCheckPhoneState(
    // 是否验证失败
    val isCheckError: Boolean = false
) : IUiState

sealed class LoginCheckPhoneEvent: IUiEvent {
    // 跳转邮箱验证页面
    object JumpMailAuthPage: LoginCheckPhoneEvent()
}