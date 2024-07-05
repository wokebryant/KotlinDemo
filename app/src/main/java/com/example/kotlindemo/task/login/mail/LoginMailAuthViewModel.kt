package com.example.kotlindemo.task.login.mail

import com.example.kotlindemo.compose.base.ComposeViewModel
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.appcommon.c.utils.PhoneStatus

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
     * 开始极验
     */
    fun startGeetest() {
        // 开始倒计时
        setState { copy(startTimeTicker = true) }
    }

    /**
     * 倒计时结束
     */
    fun timeTickerFinish() {
        setState {
            copy(startTimeTicker = false)
        }
    }

    fun clear() {
        setState { copy(code = "") }
    }



    /**
     * 点击提交
     */
    fun submit() {

    }

}