package com.example.kotlindemo.task.resume

import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.appcommon.c.OsUtils
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 简历点后推荐页面管理类
 * @Author LuoJia
 * @Date 2024/03/20
 */
object ResumeRecommendManager {

    /**
     * 获取简历卡片高度
     */
    fun getResumeCardHeight(): Int {
        // 屏幕高度
        val screenHeight = OsUtils.getScreenHeight(currentActivity())
        // 状态栏高度
        val statusBardHeight = OsUtils.getStatusBarHeight()
        // AppBar高度
        val appBarHeight = 44.dp
        // 底部卡片高度
        val nextCardHeight = 110.dp
        return screenHeight - statusBardHeight - appBarHeight - nextCardHeight
    }


}