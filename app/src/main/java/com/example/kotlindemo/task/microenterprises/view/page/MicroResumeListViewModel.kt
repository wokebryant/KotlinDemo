package com.example.kotlindemo.task.microenterprises.view.page

import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.microenterprises.MicroResumeListEvent
import com.example.kotlindemo.task.microenterprises.MicroResumeListState
import com.example.kotlindemo.task.microenterprises.data.page.MicroResumeListRepository

/**
 * @Description 小微企业简历列表ViewModel
 * @Author LuoJia
 * @Date 2023/8/29
 */
class MicroResumeListViewModel(
    private val repository: MicroResumeListRepository = MicroResumeListRepository()
) : MviBaseViewModel<MicroResumeListState, MicroResumeListEvent>() {

    override fun createInitialState() = MicroResumeListState()

    fun requestList() {

    }

}