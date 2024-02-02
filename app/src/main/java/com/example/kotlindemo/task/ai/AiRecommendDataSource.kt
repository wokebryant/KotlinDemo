package com.example.kotlindemo.task.ai

import com.example.kotlindemo.task.ai.bean.AiRecommendListBean
import com.example.kotlindemo.task.ai.bean.AiRecommendQuestionBean

/**
 * @Description Ai推荐数据源
 * @Author LuoJia
 * @Date 2024/1/9
 */
class AiRecommendDataSource(
//    private val api: PositionNewApiService = PositionApiSingleton.apiService,
) {

    /**
     * 获取推荐职位列表
     */
    fun getJobListData(params: MutableMap<String, Any?>, callback: (Result<Any>) -> Unit) {

    }

    /**
     * Mock数据
     */
    fun getMockJobListData(params: MutableMap<String, Any?>, callback: (Result<AiRecommendListBean>) -> Unit) {
        val list = mutableListOf<String>()
        repeat(20) {
            list.add(it.toString())
        }
        callback.invoke(Result.success(AiRecommendListBean(list)))
    }

    /**
     * Mock数据
     */
    fun getMockQuestionData(params: MutableMap<String, Any?>, callback: (Result<AiRecommendQuestionBean>) -> Unit) {
        val bean = AiRecommendQuestionBean(
            title = "目前匹配你条件的岗位较少",
            questionId = 0,
            questionText = "对于上班的通勤距离，你有什么偏好？",
            answers = mutableListOf(
                AiRecommendQuestionBean.Option(
                    text = "3km以内",
                    code = "0"
                ),
                AiRecommendQuestionBean.Option(
                    text = "3-5km",
                    code = "0"
                ),
                AiRecommendQuestionBean.Option(
                    text = "5-10km",
                    code = "0"
                ),
            )
        )
        callback.invoke(Result.success(bean))
    }
}