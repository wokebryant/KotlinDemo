package com.example.kotlindemo.task.ai.bean

import com.example.kotlindemo.task.linkage.KeepProtocol

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/13
 */
data class AiRecommendListBean (
    val list: List<Any>
) : KeepProtocol


data class AiRecommendQuestionBean (
    val title: String?,
    val questionId: Long?,
    val questionText: String?,
    val answers: List<Option>?
) : KeepProtocol {

    data class Option (
        val text: String?,
        val code: String?
    )

}