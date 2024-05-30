package com.example.kotlindemo.task.login

import com.example.kotlindemo.base.MviViewModel
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/05/29
 */
class LoginCheckBindPhoneViewModel(
    private val repository: LoginCheckBindPhoneRepository = LoginCheckBindPhoneRepository()
) : MviBaseViewModel<LoginCheckPhoneState, LoginCheckPhoneEvent>() {

    override fun createInitialState(): LoginCheckPhoneState = LoginCheckPhoneState()

    /**
     * 验证手机号
     */
    fun checkPhoneNumber() {

    }

}