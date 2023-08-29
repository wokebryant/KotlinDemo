package com.example.kotlindemo.study.mvi

import com.example.kotlindemo.task.jobtag.RecommendJobCard
import com.example.kotlindemo.task.jobtag.RecommendJobTag
import com.example.kotlindemo.task.jobtag.RecommendJobTagBean

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/31
 */
object MockDataStore {

    val mockJobKeyWordsBean = JobKeyWordBean(
        guide = true,
        title = "以下是您为该职位添加的关键词",
        subTitle = "修改后，可重新匹配相关人才",
        keywords = mutableListOf<JobKeyWordItem>().apply {
            val item = JobKeyWordItem(
                pathId = "1",
                type = 1,
                tags = mutableListOf<JobKeyWordTagItem>().apply {
                    val tag1 = JobKeyWordTagItem(
                        id = "0",
                        name = "数据",
                        standard = false
                    )
                    val tag2 = JobKeyWordTagItem(
                        id = "0",
                        name = "产品经理",
                        standard = false
                    )
                    val tag3 = JobKeyWordTagItem(
                        id = "0",
                        name = "交互设计",
                        standard = false
                    )
                    val tag4 = JobKeyWordTagItem(
                        id = "0",
                        name = "Android",
                        standard = false
                    )
                    val tag5 = JobKeyWordTagItem(
                        id = "0",
                        name = "开发工程师",
                        standard = false
                    )
                    add(tag1)
                    add(tag2)
                    add(tag3)
                    add(tag4)
                    add(tag5)
                }
            )
            add(item)
        }
    )

    val mockJobTypeCardBean = RecommendJobTagBean(
        title = "选择标签，为你推荐",
        cardList = mutableListOf(
            RecommendJobCard(
                title = "职位类别",
                tagList = mutableListOf(
                    RecommendJobTag(name = "社区/社群运营"),
                    RecommendJobTag(name = "平台运营"),
                    RecommendJobTag(name = "新媒体运营"),
                    RecommendJobTag(name = "跨境电商运营"),
                    RecommendJobTag(name = "数据运营"),
                    RecommendJobTag(name = "产品运营")
                )
            ),
            RecommendJobCard(
                title = "薪资范围",
                tagList = mutableListOf(
                    RecommendJobTag(name = "2.4千-4.8千"),
                    RecommendJobTag(name = "1.2万-1.4万"),
                )
            ),
            RecommendJobCard(
                title = "城市",
                tagList = mutableListOf(
                    RecommendJobTag(name = "北京"),
                    RecommendJobTag(name = "上海"),
                    RecommendJobTag(name = "武汉"),
                    RecommendJobTag(name = "南京"),
                    RecommendJobTag(name = "深圳"),
                )
            ),

        )
    )
}