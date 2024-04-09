package com.example.kotlindemo.task.resume

import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import kotlinx.coroutines.launch

/**
 * @Description 简历点后推荐页面ViewModel
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendViewModel(
    private val repository: ResumeRecommendRepository = ResumeRecommendRepository()
) : MviBaseViewModel<ResumeRecommendState, ResumeRecommendEvent>() {

    override fun createInitialState() = ResumeRecommendState()

    init {
        viewModelScope.launch {
            repository.eventFlow.collect {
                setEvent(it)
            }
        }
    }

    /**
     * 请求简历卡片数据
     */
    fun requestResumeCardData() {
        viewModelScope.launch(exceptionHandler) {
            val list = mutableListOf<Int>()
            repeat(5) {
                list.add(it)
            }
            val mockResponseList = ResumeRecommendBean(
                list = list
            ).toMockUiList()

            setState {
                copy(
                    list = mockResponseList
                )
            }
        }
    }

}