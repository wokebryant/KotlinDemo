package com.example.kotlindemo.task.searchresult.bean

import com.example.kotlindemo.task.linkage.KeepProtocol

/**
 * @Description 职位搜索结果页筛选标签Bean
 * @Author LuoJia
 * @Date 2023/10/19
 */
data class PositionSearchResultFilterTagsBean (
    val list: List<PositionSearchResultFilterTag>
) : KeepProtocol

data class PositionSearchResultFilterTag (
    val code: String,
    val name: String,
    val type: String
) : KeepProtocol


