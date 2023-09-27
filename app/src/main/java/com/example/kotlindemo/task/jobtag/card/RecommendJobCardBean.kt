package com.example.kotlindemo.task.jobtag.card

import java.io.Serializable
import java.io.StringReader

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/9/18
 */
// 不带折叠的数据模型
data class RecommendJobCardState(
    val title: String?,
    val cardList: List<RecommendJobCardItem>?
) : Serializable

data class RecommendJobCardItem(
    val style: String = "salary",
    val title: String,
    val tagList: List<RecommendJobCardTag>?
) : Serializable

data class RecommendJobCardTag(
    val code: String = "1",
    val name: String,
    var selected: Boolean = false,
    var enable: Boolean = true
) : Serializable

data class ClickInfo(
    val style: String,
    val code: String,
    val itemIndex: Int,
    val tagIndex: Int,
)

/**
 * 跳转【更多职位推荐页面】传递的数据
 */
data class LocalJobIntentData(
    val itemIndex: Int = 0,
    val tagIndex: Int = 0,
    val itemList: List<RecommendJobCardItem>? = null
) : Serializable

/**
 * 更新所有数据的选中态
 */
internal fun List<RecommendJobCardItem>.updateSelected(itemIndex: Int, tagIndex: Int) {
    forEachIndexed { itemIndex1, item ->
        item.tagList?.forEachIndexed { tagIndex1, tag ->
            tag.selected = itemIndex == itemIndex1 && tagIndex == tagIndex1 && !tag.selected
        }
    }
}

/**
 * 更新所有数据的置灰态
 */
internal fun List<RecommendJobCardItem>.updateDisable(itemIndex: Int, tagIndex: Int) {
    forEachIndexed { itemIndex1, item ->
        item.tagList?.forEachIndexed { tagIndex1, tag ->
            if (itemIndex == itemIndex1 && tagIndex == tagIndex1 && tag.enable) {
                tag.enable = false
                tag.selected = false
            }
        }
    }
}