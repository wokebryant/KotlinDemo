package com.example.kotlindemo.task.jobdetail

import com.example.kotlindemo.R

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/30
 */
object JobDetailMockData {

    fun getMockList(): List<Any> {
        val list = mutableListOf<Any>()
        val commercializeState = CommercializeState(
            titleBg = R.drawable.job_detail_commercialize_purple,
            title = "高成功率小技巧",
            content = "邀请资深大牛优化简历，可大幅提升成功率",
            subContent = "87,293人优化后求职成功",
            buttonContent = "马上提升",
            icon = R.drawable.ic_template,
            avatarList = emptyList()
        )
        list.add(commercializeState)

        val introState = IntroState(
            jobName = "研究院院长",
            salary = "5-10万·16薪",
            tagList = mutableListOf(
                IntroTagData(icon = R.drawable.ic_work_exp, tagName = "5-10年经验"),
                IntroTagData(icon = R.drawable.ic_edu, tagName = "博士"),
                IntroTagData(icon = R.drawable.ic_publish_date, tagName = "职位于今日新发布")
            ),
            street = "朝阳 望京街道",
            routeTime = "1小时10分"
        )
        list.add(introState)

        val hrState = HrState(
            avatarUrl = "https://rd5-public.zhaopin.cn/imgs/avatar_m1.png?x-oss-process=image/resize,l_240/rotate,0",
            isOnline = true,
            name = "王维嘉",
            job = "HRBP",
            companyName = "北京阿里巴巴网络技术有限公司",
            tagList = mutableListOf("今日回复10+", "总回复120+", "总面试120+")
        )
        list.add(hrState)

        val descState = DescState(
            skillTagList = mutableListOf("研究院", "管理", "研究专家", "院长", "高级管理", "项目经理", "市场营销", "财务分析师"),
            descContent = "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。" +
                    "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。",
            bonusPerformance = "公司奖惩制度完善，每年根据业绩及贡献值进行奖励；除每年12薪以外可额外收到1-4个月绩效奖，视公司财务状况决定。",
            workTime = "· 上午08:30-下午05:30 \n· 周末双休",
            blessTagList = mutableListOf("五险一金", "绩效奖金", "补贴", "专项资金")
        )
        list.add(descState)
        list.add(descState)
        list.add(introState)
        list.add(commercializeState)

        return list
    }
}