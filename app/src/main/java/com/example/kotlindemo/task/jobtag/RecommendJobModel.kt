package com.example.kotlindemo.task.jobtag

import com.example.kotlindemo.task.jobtag.card.JobFlowLayoutUIState
import com.example.kotlindemo.task.jobtag.card.RecommendJobCardState
import java.util.jar.Attributes.Name

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/5
 */

/**
 * 职位推荐卡UI State
 */
data class JobRecommendCardState(
    // B: 竖排，C: 横排
    val type: CardDirect,
    val title: String,
    val tagList: List<RelatedLabelDTO>,
    val jobList: List<JobItemState>
)

data class RelatedLabelDTO(
    val code: String,
    val name: String,
    var selected: Boolean = false
)

sealed class CardDirect {
    object Horizontal : CardDirect()
    object Vertical : CardDirect()
}


/**
 * 职位推荐卡职位模块UI State
 */
data class JobItemState(
    val jobName: String,
    val salary: String,
    val companyName: String,
    val companyStrength: String,
    val companySize: String,
    val recommendReason: String,
    val address: String,
    val skillTagList: List<TagItem> = emptyList()
) {
    data class TagItem(
       val value: String,
       val state: Int = 1
    )
}



