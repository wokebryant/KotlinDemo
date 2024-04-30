package com.example.kotlindemo.task.blueedit.bean

import com.example.kotlindemo.task.linkage.KeepProtocol

/**
 * @Description 蓝领简历编辑问题Bean
 * @Author LuoJia
 * @Date 2024/04/26
 */
data class BlueResumeEditQuestionBean(
    val durationInMonthsQuestion: DurationInMonthBean?,
    val workPreferenceQuestionList: List<WorkPreferenceBean>?
) : KeepProtocol

/**
 * 工作时长Bean
 */
data class DurationInMonthBean(
    val title: String?,
    val userSaveMonths: TagState?,
    val hasThisQuestion: Boolean?,
    val durationInMonthsList: List<TagState>?
) : KeepProtocol {

    data class TagState(
        val durationInMonthsValue: String?,
        val name: String?
    ) : KeepProtocol

}

/**
 * 工作优势Bean (单页)
 */
data class WorkPreferenceBean (
    val title: String?,
    val questionId: String?,
    val type: Int?,
    val questionType: String?,
    val level: String?,
    val must: Boolean?,
    val max: Int?,
    val answerList: List<Answer>?
) : KeepProtocol {

    data class Answer(
        val name: String?,
        val id: String?,
        val selected: Boolean?,
        // List长度为1
        val subQuestionList: List<SubQuestion>?
    ) : KeepProtocol

    data class SubQuestion(
        val title: String?,
        val questionId: String?,
        val level: String?,
        val max: Int?,
        val must: Boolean?,
        val parentQuestionId: String?,
        val parentAnswerId: String?,
        val questionType: String?,
        // List长度为1
        val subAnswerList: List<SubAnswer>?
    ) : KeepProtocol

    data class SubAnswer(
        val name: String?,
        val id: String?,
        val selected: Boolean?
    ) : KeepProtocol

}

