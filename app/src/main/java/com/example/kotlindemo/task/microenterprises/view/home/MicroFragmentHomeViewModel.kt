package com.example.kotlindemo.task.microenterprises.view.home

import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.microenterprises.MicroResumeHomeEvent
import com.example.kotlindemo.task.microenterprises.MicroResumeHomeState
import com.example.kotlindemo.task.microenterprises.data.home.MicroResumeHomeRepository

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/30
 */
class MicroFragmentHomeViewModel(
    private val repository: MicroResumeHomeRepository = MicroResumeHomeRepository()
) : MviBaseViewModel<MicroResumeHomeState, MicroResumeHomeEvent>() {

    override fun createInitialState() = MicroResumeHomeState()

}