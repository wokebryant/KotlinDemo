package com.example.kotlindemo.task.ai.util

import com.example.kotlindemo.task.ai.AiRecommendCardType
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.appcommon.c.OsUtils
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description Ai求职工具类
 * @Author LuoJia
 * @Date 2024/1/16
 */
object AiRecommendManager {

    /**
     * 是否为头部
     */
    fun isHeader(type: AiRecommendCardType) = false

    /**
     * 获取卡片高度
     */
    fun getCardHeight(): Int {
        // 屏幕高度
        val screenHeight = OsUtils.getScreenHeight(currentActivity())
        // 状态栏高度
        val statusBardHeight = OsUtils.getStatusBarHeight()
        // AppBar高度
        val appBarHeight = 44.dp
        // 间距
        val margin = 10.dp
        // 底部卡片高度
        val nextCardHeight = 120.dp
        return screenHeight - statusBardHeight - appBarHeight - margin - nextCardHeight
    }

    /**
     * 获取职位卡片高度
     */
    fun getJobCardHeight(): Int {
        // 屏幕高度
        val screenHeight = OsUtils.getScreenHeight(currentActivity())
        // 状态栏高度
        val statusBardHeight = OsUtils.getStatusBarHeight()
        // AppBar高度
        val appBarHeight = 44.dp
        // 间距
        val margin = 10.dp
        // 底部卡片高度
        val nextCardHeight = 120.dp
        return screenHeight - statusBardHeight - appBarHeight - margin - nextCardHeight
    }

    /**
     * 获取问题卡片高度
     */
    fun getQuestionCardHeight(): Int {
        // 屏幕高度
        val screenHeight = OsUtils.getScreenHeight(currentActivity())
        // 状态栏高度
        val statusBardHeight = OsUtils.getStatusBarHeight()
        // AppBar高度
        val appBarHeight = 44.dp
        // 间距
        val margin = 10.dp
        // 底部卡片高度
        val nextCardHeight = 184.dp
        return screenHeight - statusBardHeight - appBarHeight - margin - nextCardHeight
    }
}