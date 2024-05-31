package com.example.kotlindemo.task.login.mail

import com.example.kotlindemo.compose.base.ComposeViewModel

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/05/30
 */
class LoginMailAuthViewModel : ComposeViewModel<LoginMailAuthState, LoginMailAuthEffect>() {

    override fun createInitialState() = LoginMailAuthState()

    /**
     * 更新输入状态
     */
    fun updateInput(code: String) {
        setState {
            copy(submitEnable = code.isNotEmpty(), code = code)
        }
    }

    /**
     * 点击提交
     */
    fun submit() {

    }

}