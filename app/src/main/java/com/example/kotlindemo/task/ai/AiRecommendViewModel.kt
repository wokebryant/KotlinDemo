package com.example.kotlindemo.task.ai

import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.ai.util.toMockUiList
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
            repository.eventFlow.collect {
                setEvent(it)
            }
        }
    }

    /**
     * 请求职位列表
     */
    fun requestJobListData() {
        viewModelScope.launch(exceptionHandler) {
            repository.requestJobListData().collect {
                when (it) {
                    is AiRecommendListResponse.Complete -> {
                        setState {
                            copy(
                                list = it.data.toMockUiList(::onJobItemClick)
                            )
                        }
                    }

                    is AiRecommendListResponse.Empty -> {

                    }

                    is AiRecommendListResponse.Error -> {

                    }
                }
            }
        }
    }

    /**
     * 请求问题卡
     */
    fun requestQuestionData() {
        viewModelScope.launch(exceptionHandler) {
            repository.requestQuestionData()
        }
    }

    /**
     * 职位卡点击
     */
    private fun onJobItemClick(index: Int, state: AiJobState) {

    }

    fun removeJDCard(index: Int, item: AiJobState) {
        repository.removeJDCard(index, item)
    }

}