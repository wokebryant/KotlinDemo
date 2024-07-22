package com.example.kotlindemo.compose.paging

import com.example.kotlindemo.compose.paging.datasource.DataSource
import com.example.kotlindemo.compose.paging.datasource.FetchState
import com.example.kotlindemo.study.mvi.core.IUiEffect
import com.zhaopin.social.module_common_util.log.LogKitty
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Compose分页库Repository层
 */
open class ComposePagingRepository<Effect : IUiEffect>(
    private val coroutineScope: CoroutineScope,
    private val invalidate: Boolean = true,
    private val enableDefaultState: Boolean = true
) : DataSource<ComposeItem>(coroutineScope) {

    /** 异常处理 */
    protected val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            LogKitty.e("ComposePagingRepository", " exception= ${throwable.message}")
        }

    /** repo发送的消息，viewModel接受 */
    private val _effectFlow = MutableSharedFlow<Effect>()
    val effectFlow = _effectFlow.asSharedFlow()

    /**
     * 发送Event
     */
    protected fun setEffect(event: Effect) {
        coroutineScope.launch(exceptionHandler) {
            _effectFlow.emit(event)
        }
    }

    /**
     * 加载状态回调（判断是否进行加载更多）
     */
    private val loadAround: (Int) -> Unit = { dispatchLoadAround(it) }

    /**
     * 列表数据
     */
    private val _dataFlow = MutableStateFlow<List<ComposeItem>>(emptyList())
    val dataFlow = _dataFlow.onSubscription {
        if (invalidate) {
            invalidate(false)
        }
    }

    /**
     * 页面状态
     */
    val pagingState = MutableStateFlow(PagingState.Content)

    /**
     * 更新列表数据
     */
    override fun notifySubmitList(submitNow: Boolean) {
        _dataFlow.update {
            DataList(toList(), loadAround)
        }
    }

    /**
     * 请求状态改变回调
     */
    override fun onFetchStateChanged(newState: FetchState) {
        if (enableDefaultState) {
            setState(ComposeStateItem(newState, ::retry))
        }
    }
}