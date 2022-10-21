package com.example.kotlindemo.task.appbar

/**
 * @Description 校园首页状态
 * @Author LuoJia
 * @Date 2022-10-13
 */

sealed class CampusHomeState {
    /** 展示简历提示 */
    object Tip : CampusHomeState()
    /** 常规状态 */
    object Normal: CampusHomeState()
    /** 无活动 */
    object NoActivity: CampusHomeState()
    /** 无直播 */
    object NoLive: CampusHomeState()
    /** 无活动和直播 */
    object NoActivityAndLive: CampusHomeState()

}