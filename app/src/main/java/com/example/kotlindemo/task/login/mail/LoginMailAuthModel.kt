package com.example.kotlindemo.task.login.mail

import com.example.kotlindemo.study.mvi.core.IUiEffect
import com.example.kotlindemo.study.mvi.core.IUiState

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/05/30
 */

data class LoginMailAuthState (
    // 验证码
    val code: String ="",
    // title
    val title: String = "请输入邮箱号和验证码",
    // 内容
    val content: String = "若邮箱收不到验证码，请返回至上一步重新选择验证方式",
    // 邮箱号
    val email: String = "邮箱号：123****879@11.com",
    // 按钮是否可点击
    val submitEnable: Boolean = false
) : IUiState

sealed interface LoginMailAuthEffect : IUiEffect {
    // 跳转更换手机号页面
    object JumpChangePhoneNumberPage: LoginMailAuthEffect
    // 跳转验证失败页面
    object JumpErrorPage: IUiEffect, LoginMailAuthEffect
}