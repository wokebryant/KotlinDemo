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
   MircoResumeJobBean(jobName = "1销售经理"),
   MircoResumeJobBean(jobName = "2销售专员"),
   MircoResumeJobBean(jobName = "3Java开发"),
   MircoResumeJobBean(jobName = "4大数据开发"),
   MircoResumeJobBean(jobName = "5产品经理"),
   MircoResumeJobBean(jobName = "6测试工程师"),
   MircoResumeJobBean(jobName = "7测试工程师"),
   MircoResumeJobBean(jobName = "8测试工程师"),
   MircoResumeJobBean(jobName = "9测试工程师"),
   MircoResumeJobBean(jobName = "10测试工程师"),
)