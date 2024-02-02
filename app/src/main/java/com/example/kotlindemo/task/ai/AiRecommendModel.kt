package com.zhaopin.social.app.position.assistant.model

import com.zhaopin.social.base.mvi.inter.IUiEvent
import com.zhaopin.social.base.mvi.inter.IUiState
import com.zhaopin.social.common.mvvm.widgets.pagestate.state.StateType

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
    val pageState: StateType = StateType.Loading,
) : IUiState

/** UI Event */
sealed class AiRecommendEvent : IUiEvent {
    /** 添加Item */
    data class AddItem(val type: AiRecommendCardType): AiRecommendEvent()
}

/**
 * Ai求职卡片类型
 */
sealed class AiRecommendCardType {
    /** 职位卡 */
    object Job: AiRecommendCardType()
    /** 问题卡 */
    object Question: AiRecommendCardType()
    /** 代打招呼简介卡 */
    object GreetIntro: AiRecommendCardType()
    /** 代打招呼服务卡 */
    object GreetServer: AiRecommendCardType()
}

/**
 * header: 代打招呼简介卡UI State
 */
data class AiGreetIntroState (
    val time: String,
    val count: Int
)

/**
 * 1. 职位卡UI State
 */
data class AiJobState (
    val jobName: String
)

/**
 * 2. 问题卡UI State
 */
data class AiQuestionState (
    val title: String
)

/**
 * 3. 代打招呼服务卡UI State
 */
data class AiGreetServerState (
    val title: String
)



