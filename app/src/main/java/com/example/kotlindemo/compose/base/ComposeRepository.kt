package com.example.kotlindemo.compose.base

import com.example.kotlindemo.study.mvi.core.IUiEffect
import com.zhaopin.social.module_common_util.log.LogKitty
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * @Description Compose Repository基类
 * @Author LuoJia
 * @Date 2024/06/24
 */
abstract class ComposeRepository <Effect : IUiEffect>(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
) {

    /** 异常处理 */
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            LogKitty.e("ComposeRepository", " exception= ${throwable.message}")
        }

    /** 协程 */
    protected val scope by lazy { CoroutineScope(SupervisorJob() + dispatcher + exceptionHandler) }

    /** repo发送的消息，viewModel接受 */
    private val _effectFlow = MutableSharedFlow<Effect>()
    val effectFlow = _effectFlow.asSharedFlow()

    /**
     * 发送Event
     */
    protected fun setEffect(event: Effect) {
        scope.launch(exceptionHandler) {
            _effectFlow.emit(event)
        }
    }


}