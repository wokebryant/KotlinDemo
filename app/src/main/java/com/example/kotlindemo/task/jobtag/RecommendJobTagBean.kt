package com.example.kotlindemo.task.jobtag

import com.example.kotlindemo.task.linkage.KeepProtocol

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/15
 */
data class RecommendJobTagBean (
    val title: String?,
    val cardList: List<RecommendJobCard>?
) : KeepProtocol

data class RecommendJobCard(
    val title: String,
    val tagList: List<RecommendJobTag>
) : KeepProtocol

data class RecommendJobTag(
    val name: String
) : KeepProtocol