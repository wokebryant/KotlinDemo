package com.example.kotlindemo.study.mvi.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhaopin.social.module_common_util.log.LogKitty
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @Description MVI ViewModel基类
 * @Author LuoJia
 * @Date 2023/7/18
 */
abstract class MviBaseViewModel<State : IUiState, Event : IUiEvent> : ViewModel() {

    /** 异常处理 */
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            LogKitty.e("MviBaseViewModel", " exception= ${throwable.message}")
        }

    /**
     * UiState的初始状态，交由子类实现
     */
    private val initialState: State by lazy { createInitialState() }

    /** 页面持有的状态 */
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    /** 页面产生的一次性事件，如弹一个Toast */
    private val _eventFlow = SharedFlowEvents<Event>()
    val eventFlow = _eventFlow.asSharedFlow()

    /**
     * 发送State
     */
    protected fun setState(reduce: State.() -> State) {
        _stateFlow.update { reduce(_stateFlow.value) }
    }

    /**
     * 提供State
     */
    protected inline fun <R> withState(block: (State) -> R): R =
        stateFlow.value.let(block)

    /**
     * 发送Event
     */
    protected fun setEvent(vararg events: Event) {
        viewModelScope.launch(exceptionHandler) {
            val eventList = events.toList()
            _eventFlow.emit(eventList)
        }
    }

    protected abstract fun createInitialState(): State

}