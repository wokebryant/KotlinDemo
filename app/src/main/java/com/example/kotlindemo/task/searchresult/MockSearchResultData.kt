package com.example.kotlindemo.task.searchresult

import com.example.kotlindemo.task.jobtag.card.JobFlowLayoutUIState
import com.example.kotlindemo.task.searchresult.bean.PositionSearchResultFilterTag

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/18
 */
object MockSearchResultData {

    fun getMockJobListData(): List<ResultJobState> {
        val list = mutableListOf<ResultJobState>()
        for (i in 0..20) {
            val job = ResultJobState(title = "测试文本$i")
            list.add(job)
        }
        return list
    }

    fun getMockFilterMoreList(): List<ResultFilterMoreState> {
        return mutableListOf(
            ResultFilterMoreState(
                title = "位置距离",
                type = ResultFilterMoreType.Location,
            ),
            ResultFilterMoreState(
                title = "智能排序",
                type = ResultFilterMoreType.Sort,
            ),
            ResultFilterMoreState(
                title = "职位类型",
                type = ResultFilterMoreType.JobType,
            ),
            ResultFilterMoreState(
                title = "更多筛选",
                type = ResultFilterMoreType.MoreFilter,
            ),
        )
    }

    fun getMockFilterTagList(): List<JobFlowLayoutUIState> {
        val tagList = mutableListOf(
            PositionSearchResultFilterTag(
                code = "",
                name = "未查看",
                type = ""
            ),
            PositionSearchResultFilterTag(
                code = "",
                name = "工作双休",
                type = ""
            ),
            PositionSearchResultFilterTag(
                code = "",
                name = "10km以内",
                type = ""
            ),
            PositionSearchResultFilterTag(
                code = "",
                name = "有五险一金",
                type = ""
            ),
            PositionSearchResultFilterTag(
                code = "",
                name = "7日内发布",
                type = ""
            ),
            PositionSearchResultFilterTag(
                code = "",
                name = "hr近三日活跃",
                type = ""
            ),
        )
        return tagList.toSearchFilterTagUiState()
    }

    private fun List<PositionSearchResultFilterTag>.toSearchFilterTagUiState() = this.map {
        JobFlowLayoutUIState(
            name = it.name,
            selected = false,
            title = "",
            enable = true,
            itemClick = { index, state ->

            }
        )
    }

    private fun List<String>.toFlowUiState() = this.map {

    }
}