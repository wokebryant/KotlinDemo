package com.example.kotlindemo.task.resume

import androidx.annotation.DrawableRes
import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.example.kotlindemo.task.linkage.KeepProtocol

/**
 * @Description 简历点后推荐模型
 * @Author LuoJia
 * @Date 2024/03/19
 */

/** UI State */
data class ResumeRecommendState (
    /** 推荐列表 */
    val list: List<Any> = emptyList(),
) : IUiState

/** UI Event */
sealed class ResumeRecommendEvent : IUiEvent {
    /** 删除Item */
    data class RemoveItem(val index: Int, val item: Any): ResumeRecommendEvent()
    /** 更新Item */
    data class UpdateItem(val index: Int, val item: Any): ResumeRecommendEvent()
    /** 滑动到下一个卡片 */
    object MoveToNext: ResumeRecommendEvent()
}

/**
 * 简历卡UI State
 */
data class ResumeRecommendCardState (
    val list: List<Any>,
    /** 是否展示不合适 */
    val isShowInappropriate: Boolean,
    /** 收藏状态 */
    val isCollect: Boolean,
    /** 打招呼/继续沟通 */
    val isGreet: Boolean,
    /** 是否展示错误页 */
    val showError: Boolean
)

/** 1. 基础信息 */
data class BaseInfoState (
    val name: String,
    val isOnline: Boolean,
    val distance: String,
    val avatar: String,
    val gender: Int,
    val jobAndCompany: String,
    val dutyTime: String,
    val tagList: List<TagState>,
    val desc: String
) {
    data class TagState(
        @DrawableRes val icon: Int,
        val name: String
    )
}

/** 2. 求职期望 */
data class JobExpectState (
    val jobExpectList: List<ItemState>
) {
    data class ItemState (
       val jobName: String,
       val salary: String,
       val desc: String
    )
}

/** 3. 工作经历 */
data class WorkExpState (
    val workExpList: List<ItemState>
) {
    data class ItemState(
        val companyName: String,
        val industryAndTime: String,
        val jobName: String,
        val jobDesc: String,
        val tagList: List<String>,
        val itemSize: Int
    )
}

/** 4. 资格证书 */
data class QualificationCertState (
    val certList: List<String>
)

/** 5. 教育经历 */
data class EducationExpState (
    val eduList: List<ItemState>
) {
    data class ItemState(
        val schoolName: String,
        val schoolTag: String,
        val schoolTime: String,
        val majorName: String,
        val majorDesc: String
    )
}

/** 6. 语言能力 */
data class LanguageSkillState (
    val languageList: List<ItemState>
) {
    data class ItemState (
        val languageName: String,
        val readSkill: String,
        val speakSkill: String
    )
}

/** 7. 所获得证书 */
data class ObtainedCertState (
    val certList: List<ItemState>
) {
    data class ItemState(
        val certName: String,
        val date: String
    )
}

/** 8. 专业技能 */
data class ProSkillState (
    val skillList: List<ItemState>
) {
    data class ItemState(
        val skill: String,
        val year: String
    )
}

/** 9. 履历亮点 */
data class ResumeHighlightState (
    val highlightList: List<String>
)

/** 10. 自我介绍  */
data class SelfIntroState (
    val skillList: List<String>,
    val content: String
)
