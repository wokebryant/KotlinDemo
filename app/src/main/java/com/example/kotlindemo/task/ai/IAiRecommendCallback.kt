package com.example.kotlindemo.task.ai

import android.view.View

/**
 * @Description Ai求职回调
 * @Author LuoJia
 * @Date 2024/1/11
 */
interface IAiRecommendCallback {

    /** TopBar更多功能按钮点击 */
    fun onMoreClick(anchor: View)

    /** 打招呼 */
    fun onGreetClick()

    /** 负反馈点击 */
    fun onFeedbackClick(index: Int, item: AiJobState)
}