package com.example.kotlindemo.task.microenterprises.bean

import com.example.kotlindemo.activity.linkage.KeepProtocol

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/29
 */
data class MicroCandidateBean (
    val name: String?
) : KeepProtocol

val mockMircoResumeList = mutableListOf<Any>(
    MicroResumeListToBePaidBean(content = "您有一个待支付订单"),
    MicroCandidateBean(name = "吴先生"),
    MicroCandidateBean(name = "李先生"),
    MicroCandidateBean(name = "刘先生"),
    MicroCandidateBean(name = "曹先生"),
    MicroCandidateBean(name = "陈先生"),
    MicroCandidateBean(name = "罗先生"),
    MicroCandidateBean(name = "王先生"),
    MicroCandidateBean(name = "吴先生"),
    MicroCandidateBean(name = "赵先生"),
    MicroCandidateBean(name = "吴先生"),
    MicroCandidateBean(name = "钱先生"),
)