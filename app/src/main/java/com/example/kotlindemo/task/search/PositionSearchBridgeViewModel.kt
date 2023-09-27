package com.example.kotlindemo.task.search

import com.example.kotlindemo.study.mvi.core.MviBaseViewModel

/**
 * @Description 职位搜索桥页面ViewModel
 * @Author LuoJia
 * @Date 2023/9/21
 */
class PositionSearchBridgeViewModel : MviBaseViewModel<SearchBridgeState, SearchBridgeEvent>() {

    override fun createInitialState() = SearchBridgeState()

    fun requestHistoryData() {

    }


}