package com.example.kotlindemo.task.negavition

import com.example.kotlindemo.activity.linkage.KeepProtocol
import java.io.Serializable

/**
 * @Description 负反馈数据Bean
 * @Author LuoJia
 * @Date 2023/8/11
 */
data class NegativeFeedbackBean (
    val state: Int?,
    val title: String?,
    val labelList: MutableList<Any>?
) : KeepProtocol, Serializable

data class FeedbackLabel(
    val style: String,
    val state: Int,
    val title: String,
    val linkText: String,
    val link: String,
    val itemList: List<LabelItem>
) : KeepProtocol

data class EditLabel(
    val style: String,
    val title: String
)

data class LabelItem(
    val code: String,
    val name: String,
    val keyword: String
) : KeepProtocol



fun getMockData(): NegativeFeedbackBean {
    val originData = NegativeFeedbackBean(
        state = 1,
        title = "选择不满意原因，为您优化推荐结果",
        labelList = mutableListOf(
            FeedbackLabel(
                style = "reduce",
                state = 1,
                title = "减少推荐此类职位",
                linkText = "",
                link = "",
                itemList = reduceItemList
            ),
            FeedbackLabel(
                style = "no",
                state = 1,
                title = "不再推荐此类职位",
                linkText = "",
                link = "",
                itemList = noItemList
            ),
            FeedbackLabel(
                style = "reason",
                state = 1,
                title = "其他原因",
                linkText = "",
                link = "",
                itemList = emptyList()
            ),
        )
    )

    originData.labelList?.forEachIndexed { index, label ->
        if (label is FeedbackLabel && label.style == "reason") {
            originData.labelList.remove(index)
            originData.labelList.add(EditLabel(style = label.style, title = label.title))
        }
    }

    return originData
}

val reduceItemList = mutableListOf(
//    LabelItem(code = "1", name = "不喜欢「{0}」", keyword = "代理商销售哈哈哈"),
    LabelItem(code = "2", name = "「{0}」行业", keyword = "云计算/大数据1"),
    LabelItem(code = "3", name = "「{0}」薪资低", keyword = "1万"),
//    LabelItem(code = "4", name = "不看「{0}」行业", keyword = "生物工程哈哈哈哈哈哈")
)

val noItemList = mutableListOf(
//    LabelItem(code = "5", name = "「{0}」公司", keyword = "北京网安通科技有限公司"),
    LabelItem(code = "3", name = "「{0}」薪资低", keyword = "1.1万-2.1万"),
//    LabelItem(code = "6", name = "「{0}」职位", keyword = "代招/派遣"),
)