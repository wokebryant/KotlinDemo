package com.example.kotlindemo.task.searchresult

import com.example.kotlindemo.task.search.SearchItemState

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/18
 */

/**
 * 搜索结果页职位Item UIState
 */
data class ResultJobState(
    val title: String
)

const val PAGE_POSITION = 0
const val PAGE_COMPANY = 1

sealed class PositionSearchResultTab {
    /** 职位 */
    object Position: PositionSearchResultTab()
    /** 公司 */
    object Company: PositionSearchResultTab()
}

private val searchResultFilterMoreItems = mutableListOf("位置距离", "智能排序")

/**
 * 搜索结果页筛选更多 UIState
 */
data class ResultFilterMoreState(
    val title: String,
    val type: ResultFilterMoreType,
)
sealed class ResultFilterMoreType {
    /** 位置距离 */
    object Location: ResultFilterMoreType()
    /** 智能排序 */
    object Sort: ResultFilterMoreType()
    /** 职位类型 */
    object JobType: ResultFilterMoreType()
    /** 更多筛选 */
    object MoreFilter: ResultFilterMoreType()
}

/**
 * 搜索结果页筛选更多 UIState
 */
//data class ResultFilterTagType (
//    val
//)

