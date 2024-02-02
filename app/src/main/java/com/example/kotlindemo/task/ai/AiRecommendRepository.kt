package com.zhaopin.social.app.position.assistant.repo

import com.zhaopin.social.app.position.assistant.datasource.AiRecommendDataSource
import com.zhaopin.social.app.position.assistant.model.AiRecommendEvent
import com.zhaopin.social.base.mvi.repo.MviBaseRepository

/**
 * @Description AI求职推荐列表Repository
 * @Author LuoJia
 * @Date 2024/1/9
 */
class AiRecommendRepository(
    private val dataSource: AiRecommendDataSource = AiRecommendDataSource()
) : MviBaseRepository<AiRecommendEvent>() {


}