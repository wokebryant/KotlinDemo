package com.zhaopin.social.app.position.assistant.viewmodel

import androidx.lifecycle.viewModelScope
import com.zhaopin.social.app.position.assistant.model.AiRecommendEvent
import com.zhaopin.social.app.position.assistant.model.AiRecommendState
import com.zhaopin.social.app.position.assistant.repo.AiRecommendRepository
import com.zhaopin.social.base.mvi.viewmodel.MviBaseViewModel
import kotlinx.coroutines.launch

/**
 * @Description AI求职推荐列表ViewModel
 * @Author LuoJia
 * @Date 2024/1/9
 */
class AiRecommendViewModel(
    private val repository: AiRecommendRepository = AiRecommendRepository()
) : MviBaseViewModel<AiRecommendState, AiRecommendEvent>() {

    override fun createInitialState() = AiRecommendState()

    init {
        viewModelScope.launch {

        }
    }

}