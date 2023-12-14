package com.example.kotlindemo.task.jobdetail

import android.view.View
import com.example.kotlindemo.task.jobdetail.delegate.JobDetailCommercialize
import com.example.kotlindemo.task.jobdetail.delegate.JobDetailDescDelegate
import com.example.kotlindemo.task.jobdetail.delegate.JobDetailHrDelegate
import com.example.kotlindemo.task.jobdetail.delegate.JobDetailIntroDelegate
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.social.module_common_util.ext.throttleClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/28
 */

@JvmOverloads
inline fun View.onJClick(wait: Long = 200, crossinline block: ((View) -> Unit)) {
    // 浏览模式拦截点击事件
//    if (PrivacyHelper.isAppScanMode()) {
//        return
//    }
    setOnClickListener(throttleClick(wait, block))
}

internal fun MultiTypeAdapter.registerJobDetailList() = this.apply {
    // 头部卡片，商业化
    register(JobDetailCommercialize())
    // 职位简介卡
    register(JobDetailIntroDelegate())
    // HR卡
    register(JobDetailHrDelegate())
    // 职位描述卡
    register(JobDetailDescDelegate())
}