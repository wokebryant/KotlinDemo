package com.example.kotlindemo.study.mvi

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
}