package com.example.kotlindemo.task.microenterprises.bean

/**
 * @Description 小微企业简历列表职位数据Bean
 * @Author LuoJia
 * @Date 2023/8/30
 */
data class MircoResumeJobBean (
   val jobNumber: String = "",
   val jobName: String
)

val mockMircoJobList = mutableListOf<MircoResumeJobBean>(
   MircoResumeJobBean(jobName = "销售经理"),
   MircoResumeJobBean(jobName = "销售专员"),
   MircoResumeJobBean(jobName = "Java开发"),
   MircoResumeJobBean(jobName = "大数据开发"),
   MircoResumeJobBean(jobName = "产品经理"),
   MircoResumeJobBean(jobName = "测试工程师"),
)