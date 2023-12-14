package com.example.kotlindemo.task.jobdetail

import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState

/**
 * @Description 职位详情页State
 * @Author LuoJia
 * @Date 2023/11/22
 */

/** UI State */
data class JobDetailState (
    /** 页面状态 */
    val pageState: Int= 0,
) : IUiState

/** UI Event */
sealed class JobDetailEvent : IUiEvent {

}

/**
 * header: 投后商业化UI State
 */
data class CommercializeState (
    val titleBg: Int,
    val title: String,
    val content: String,
    val subContent: String,
    val buttonContent: String,
    val icon: Int,
    val avatarList: List<String>
)

/**
 * 1. 简介卡UI State
 */
data class IntroState (
    val jobName: String,
    val salary: String,
    val tagList: List<IntroTagData>,
    val street: String,
    val routeTime: String
)

data class IntroTagData(
    val icon: Int,
    val tagName: String
)

/**
 * 2. HR卡UI State
 */
data class HrState (
    val avatarUrl: String,
    val isOnline: Boolean,
    val name: String,
    val job: String,
    val companyName: String,
    val tagList: List<String>
)

/**
 * 3. 职位描述卡UI State
 */
data class DescState (
    val skillTagList: List<String>,
    val descContent: String,
    val bonusPerformance: String,
    val workTime: String,
    val blessTagList: List<String>,
)

/**
 * 4. 公司卡UI State
 */
data class CompanyState (
    val title: String
)

/**
 * 5. 简历代投卡UI State
 */
data class ResumeState (
    val title: String
)

/**
 * 6. 求职安全卡UI State
 */
data class SafeState (
    val title: String
)

/**
 * 7. 用工单位卡UI State
 */
data class EmployState (
    val title: String
)



