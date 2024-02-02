package com.example.kotlindemo.task.ai.util

import com.example.kotlindemo.task.ai.AiJobState
import com.example.kotlindemo.task.ai.AiQuestionState
import com.example.kotlindemo.task.ai.AiRecommendCardType
import com.example.kotlindemo.task.ai.IAiRecommendCallback
import com.example.kotlindemo.task.ai.JobInterpretState
import com.example.kotlindemo.task.ai.bean.AiRecommendListBean
import com.example.kotlindemo.task.ai.bean.AiRecommendQuestionBean
import com.example.kotlindemo.task.ai.delegate.AiRecommendJobDelegate
import com.example.kotlindemo.task.ai.delegate.AiRecommendQuestionDelegate
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.addItem
import com.zhaopin.list.multitype.adapter.appendHeaderItem
import com.zhaopin.list.multitype.adapter.appendItem
import com.zhaopin.list.multitype.adapter.remove
import com.zhaopin.list.multitype.adapter.removeHeaderItem
import com.zhaopin.list.multitype.adapter.replaceHeaderItem
import com.zhaopin.list.multitype.adapter.setItem

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/11
 */

internal fun MultiTypeAdapter.registerAiRecommendList(callback: IAiRecommendCallback) = this.apply {
    // 职位卡
    register(AiRecommendJobDelegate(callback))
    // 问题卡
    register(AiRecommendQuestionDelegate())
}

/**
 * 添加Ai求职Item
 */
internal fun MultiTypeAdapter.addAiRecommendItem(type: AiRecommendCardType,  index: Int, item: Any) {
    when (AiRecommendManager.isHeader(type)) {
        // 是头部
        true -> {
            if (headerItems.size > index) {
                replaceHeaderItem(index, item, true)
            } else {
                appendHeaderItem(item, true)
            }
        }

        // 不是头部
        false -> {
            if (index < 0) {
                appendItem(item)
            } else {
                if (index <= items.size) addItem(index, item)
            }
        }
    }
}

/**
 * 删除职位详情页Item
 */
internal fun MultiTypeAdapter.removeAiRecommendItem(type: AiRecommendCardType, index: Int, item: Any) {
    when (AiRecommendManager.isHeader(type)) {
        // 是头部
        true -> removeHeaderItem(index, true)

        // 不是头部
        false -> remove(item)
    }
}

/**
 * 更新职位详情页Item
 */
internal fun MultiTypeAdapter.updateAiRecommendItem(type: AiRecommendCardType, index: Int, item: Any) {
    if (index < 0) {
        return
    }
    when (AiRecommendManager.isHeader(type)) {
        // 是头部
        true -> replaceHeaderItem(index, item, true)

        // 不是头部
        false -> setItem(index, item)
    }
}

/**
 * 将接口数据转换成UI State
 */
internal fun AiRecommendListBean.toMockUiList(
    onItemClick: (Int, AiJobState) -> Unit
): List<Any> {
    val jobInterpretList = mutableListOf(
        JobInterpretState(
            title = "【职位概括】：",
            titleLabel = mutableListOf(),
            contentType = 1,
            labelList = mutableListOf("土建工程管理", "现场协调与实施", "工程安全", "符合质量成本要求", "熟悉施工规范", "熟练使用C4D", "土建工程管理", "现场协调与实施", "工程安全", "符合质量成本要求", "熟悉施工规范", "熟练使用C4D", "土建工程管理", "现场协调与实施", "工程安全", "符合质量成本要求", "熟悉施工规范", "熟练使用C4D"),
            textContent = "",
            spanList = emptyList()
        ),
        JobInterpretState(
            title = "【职位亮点】：",
            titleLabel = mutableListOf(),
            contentType = 2,
            labelList = mutableListOf(),
            textContent = "获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗。获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗获得“中国年度最佳雇主”、“高薪技术企业”荣誉称号；有五险一金、补充医疗",
            spanList = emptyList()
        ),
        JobInterpretState(
            title = "【职位亮点】：",
            titleLabel = mutableListOf(),
            contentType = 3,
            labelList = mutableListOf(),
            textContent = "",
            spanList = mutableListOf(
                JobInterpretState.SpanContent(text = "经验优势·超过83%的人", highLight = "83%"),
                JobInterpretState.SpanContent(text = "学历优势·超过93%的人", highLight = "83%"),
                JobInterpretState.SpanContent(text = "经验优势·超过83%的人", highLight = "83%"),
            )
        ),
    )

    return this.list.mapIndexed { index, any ->
        AiJobState(
            jobName = "高级工程建 $index",
            salary = "3-5·14薪",
            companyName = "北京百纳地产有限公司",
            companySize = "500-999人",
            address = "北京·朝阳·望京",
            distance = "距住址3.5km",
            skillLabelList = mutableListOf("5-10年经验", "学历不限", "一级建造工程师"),
            jobInterpretLottie = "",
            jobInterpretTitle = "",
            jobInterpretList = jobInterpretList,
            hrAvatar = "",
            hrOnline = true,
            hrName = "王唯伽",
            hrJob = "招聘主管",
            hrActive = "3分钟前活跃",
            onItemClick = onItemClick
        )
    }
}

internal fun AiRecommendQuestionBean.toQuestionState(
    onOptionClick: (Int, Set<Int>) -> Unit,
    onDeleteClick: () -> Unit,
    onSureClick: () -> Unit
): AiQuestionState {
    return AiQuestionState(
        title = this.title ?: "",
        question = this.questionText ?: "",
        optionList = this.answers?.map {
            AiQuestionState.Option(
                text = it.text ?: "",
                code = it.code ?: "",
                isSelected = false,
                onItemClick = onOptionClick
            )
        } ?: emptyList(),
        multi = true,
        onDeleteClick = onDeleteClick,
        onSureClick = onSureClick
    )
}

