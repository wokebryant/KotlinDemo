package com.example.kotlindemo.compose.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.IUiEffect
import com.example.kotlindemo.study.mvi.core.IUiState
import com.example.kotlindemo.study.mvi.core.SharedFlowEffects
import com.example.kotlindemo.study.mvi.core.SharedFlowEvents
import com.zhaopin.social.module_common_util.log.LogKitty
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @Description Compose ViewModel基类
 * @Author LuoJia
 * @Date 2024/05/30
 */
abstract class ComposeViewModel<State : IUiState, Effect : IUiEffect> : ViewModel() {

    /** 异常处理 */
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            LogKitty.e("ComposeViewModel", " exception= ${throwable.message}")
        }

    /**
     * UiState的初始状态，交由子类实现
     */
    private val initialState: State by lazy { createInitialState() }

    protected abstract fun createInitialState(): State

    /** 页面持有的状态 */
    private val _stateFlow = MutableStateFlow(initialState)
    val stateFlow = _stateFlow.asStateFlow()

    /** 页面产生的副作用 */
    private val _effectFlow = SharedFlowEffects<Effect>()
    val effectFlow = _effectFlow.asSharedFlow()

    /**
     * 发送State
     */
    protected fun setState(reduce: State.() -> State) {
        _stateFlow.update { reduce(_stateFlow.value) }
    }

    /**
     * 提供State
     */
    inline fun <R> withState(block: (State) -> R): R =
        stateFlow.value.let(block)

    /**
     * 发送Effect
     */
    protected fun setEffect(vararg effects: Effect) {
        viewModelScope.launch(exceptionHandler) {
            val effectList = effects.toList()
            _effectFlow.emit(effectList)
        }
    }

}