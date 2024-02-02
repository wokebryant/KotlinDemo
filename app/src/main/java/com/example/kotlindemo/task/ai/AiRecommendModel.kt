package com.example.kotlindemo.task.ai

import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.example.kotlindemo.task.ai.bean.AiRecommendListBean

/**
 * @Description Ai求职推荐列表State
 * @Author LuoJia
 * @Date 2024/1/9
 */

/** UI State */
data class AiRecommendState (
    /** 推荐列表 */
    val list: List<Any> = emptyList(),
    /** 页面状态 */
//    val pageState: StateType = StateType.Loading,
) : IUiState

/** UI Event */
sealed class AiRecommendEvent : IUiEvent {
    /** 添加Item */
    data class AddItem(val type: AiRecommendCardType, val index: Int, val item: Any): AiRecommendEvent()
    /** 删除Item */
    data class RemoveItem(val type: AiRecommendCardType, val index: Int, val item: Any): AiRecommendEvent()
    /** 更新Item */
    data class UpdateItem(val type: AiRecommendCardType, val index: Int, val item: Any): AiRecommendEvent()
    object MoveToNext: AiRecommendEvent()

}

/**
 * Ai求职卡片类型
 */
sealed class AiRecommendCardType {
    /** 职位卡 */
    object Job: AiRecommendCardType()
    /** 问题卡 */
    object Question: AiRecommendCardType()
}

/**
 * 1. 职位卡UI State
 * https://wiki.zhaopin.com/pages/viewpage.action?pageId=160685445
 */
data class AiJobState (
    val jobName: String,
    val salary: String,
    val companyName: String,
    val companySize: String,
    val address: String,
    val distance: String,
    val skillLabelList: List<String>,
    val jobInterpretLottie: String,
    val jobInterpretTitle: String,
    val jobInterpretList: List<JobInterpretState>,
    val hrAvatar: String,
    val hrOnline: Boolean,
    val hrName: String,
    val hrJob: String,
    val hrActive: String,
    val onItemClick: (Int, AiJobState) -> Unit
)

/**
 * 职位解读
 */
data class JobInterpretState(
    val title: String,
    // 标题拼接的标签
    val titleLabel: List<String>,
    // 内容类别
    val contentType: Int,
    // 内容标签
    val labelList: List<String>,
    // 纯文本内容（无高亮）
    val textContent: String,
    // 高亮内容
    val spanList: List<SpanContent>
) {
    data class SpanContent(
        val text: String,
        val highLight: String
    )
}

/**
 * 2. 问题卡UI State
 * https://wiki.zhaopin.com/pages/viewpage.action?pageId=160681648
 */
data class AiQuestionState (
    val title: String,
    // 问题
    val question: String,
    // 选项
    val optionList: List<Option>,
    // 是否多选
    val multi: Boolean,
    // 关闭点击
    val onDeleteClick: () -> Unit,
    // 确认点击
    val onSureClick: () -> Unit
) {
    data class Option(
        val text: String,
        val code: String,
        val isSelected: Boolean,
        val onItemClick: (Int, Set<Int>) -> Unit
    )
}


sealed class AiMoreClickType{
    // 反馈建议
    object FeedBack: AiRecommendCardType()
    // 用户协议
    object UserProtocol: AiRecommendCardType()
}

/**
 * AI推荐列表响应数据
 */
sealed class AiRecommendListResponse {
    // 请求成功有数据
    data class Complete(
        val data: AiRecommendListBean
    ) : AiRecommendListResponse()
    // 请求为空
    object Empty : AiRecommendListResponse()
    // 请求错误
    object Error : AiRecommendListResponse()
}




