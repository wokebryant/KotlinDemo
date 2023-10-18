package com.example.kotlindemo.task.microenterprises.bean

import com.example.kotlindemo.task.linkage.KeepProtocol

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/29
 */

/**
 * 待支付
 */
data class MicroResumeListToBePaidBean(
    val content: String?
) : KeepProtocol

data class MicroResumeOnlineJobBean(
    val title: String?,
    val jobName: String?,
    val jobTagList: List<String>
) : KeepProtocol