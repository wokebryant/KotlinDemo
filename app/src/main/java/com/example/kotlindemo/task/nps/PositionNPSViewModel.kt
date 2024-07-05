package com.example.kotlindemo.task.nps

import com.example.kotlindemo.study.mvi.core.MviBaseViewModel

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/07/05
 */
class PositionNPSViewModel : MviBaseViewModel<NPSState, NPSEvent>() {

    override fun createInitialState() = NPSState()


}