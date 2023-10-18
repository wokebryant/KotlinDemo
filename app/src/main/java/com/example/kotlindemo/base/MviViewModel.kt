package com.example.kotlindemo.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiIntent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.zhaopin.social.module_common_util.log.LogKitty
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * @Description MVI ViewModel基类
 * @Author LuoJia
 * @Date 2023/4/24
 */
abstract class MviViewModel<UiState : IUiState, UiEvent : IUiEvent, UiIntent : IUiIntent> : ViewModel() {

    /** 异常处理 */
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            LogKitty.e("MviViewModel", " exception= ${throwable.message}")
        }

    /**
     * UiState的初始状态，交由子类实现
     */
    private val initialUiState: UiState by lazy { createInitialUiState() }

    /** 页面持有的状态 */
    private val _uiStateFlow = MutableStateFlow(initialUiState)
    val uiStateFlow: StateFlow<UiState> = _uiStateFlow.asStateFlow()

    /** 页面产生的一次性事件，如弹一个Toast */
    private val _uiEventFlow: Channel<UiEvent> = Channel()
    val uiEventFlow: Flow<UiEvent> = _uiEventFlow.receiveAsFlow()

    /** 页面和ViewModel进行的交互 */
    private val _uiIntentFlow = MutableSharedFlow<UiIntent>()
    private val uiIntentFlow: SharedFlow<UiIntent> = _uiIntentFlow.asSharedFlow()

    /**
     * 发送UiState
     */
    protected fun sendUiState(reduce: UiState.() -> UiState) {
        _uiStateFlow.update { reduce(_uiStateFlow.value) }
    }

    /**
     * 发送Event
     */
    protected fun sendUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEventFlow.send(uiEvent)
        }
    }

    /**
     * 发送Intent
     */
    fun sendUiIntent(uiIntent: UiIntent) {
        viewModelScope.launch {
            _uiIntentFlow.emit(uiIntent)
        }
    }

    init {
        viewModelScope.launch {
            uiIntentFlow.collect {
                LogKitty.i("MviViewModel", " intent= $it")
                handleUiIntent(it)
            }
        }
    }

    protected abstract fun createInitialUiState(): UiState

    protected abstract fun handleUiIntent(intent: IUiIntent)

}