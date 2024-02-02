package com.zhaopin.social.app.position.assistant.datasource

import com.zhaopin.social.app.position.common.network.PositionApiSingleton
import com.zhaopin.social.app.position.common.network.PositionNewApiService

/**
 * @Description Ai推荐数据源
 * @Author LuoJia
 * @Date 2024/1/9
 */
class AiRecommendDataSource(
    private val api: PositionNewApiService = PositionApiSingleton.apiService,
) {

}