package com.example.kotlindemo.compose.widget.stateLayout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.kotlindemo.compose.widget.stateLayout.state.EmptyState
import com.example.kotlindemo.compose.widget.stateLayout.state.ErrorState
import com.example.kotlindemo.compose.widget.stateLayout.state.defaultEmptyState
import com.example.kotlindemo.compose.widget.stateLayout.state.defaultErrorState
import com.example.kotlindemo.compose.widget.stateLayout.widget.DefaultEmptyLayout
import com.example.kotlindemo.compose.widget.stateLayout.widget.DefaultErrorLayout
import com.example.kotlindemo.compose.widget.stateLayout.widget.DefaultLoadingLayout

/**
 * @Description Compose通用状态页
 * @Author LuoJia
 * @Date 2024/04/12
 */


/**
 * 通用状态页数据
 */
sealed class PageData {
    data object Loading : PageData()

    data object Content : PageData()

    data class Error(
        val state: ErrorState,
        val retry: (PageData) -> Unit = { }
    ) : PageData()

    data class Empty(
        val state: EmptyState
    ) : PageData()
}

/**
 * 页面状态
 */
class PageState(state: PageData) {

    companion object {
        fun Loading() = PageData.Loading

        fun Content() = PageData.Content

        fun Empty(state: EmptyState = defaultEmptyState) = PageData.Empty(state)

        fun Error(state: ErrorState = defaultErrorState) = PageData.Error(state)
    }

    /** 内部交互的状态 */
    internal var interactionState by mutableStateOf(state)

    /** 供外部获取当前状态 */
    val state: PageData
        get() = interactionState

    /**
     * 展示内容
     */
    fun showContent() {
        interactionState = PageData.Content
    }

    /**
     * 展示加载状态
     */
    fun showLoading() {
        interactionState = PageData.Loading
    }

    /**
     * 展示空页面
     */
    fun showEmpty(state: EmptyState = defaultEmptyState) {
        interactionState = PageData.Empty(state)
    }

    /**
     * 展示错误页
     */
    fun showError(state: ErrorState = defaultErrorState) {
        interactionState = PageData.Error(state)
    }

    /** 是否处于加载态 */
    val isLoading: Boolean
        get() = interactionState is PageData.Loading

}

@Composable
fun rememberPageState(state: PageData = PageData.Loading): PageState {
    return remember {
        PageState(state)
    }
}

/**
 * 智联默认的状态页
 */
@Composable
fun StatePage(
    modifier: Modifier = Modifier,
    pageState: PageState = rememberPageState(),
    retry: (PageData) -> Unit = { },
    loading: @Composable BoxScope.() -> Unit = { DefaultLoadingLayout() },
    empty: @Composable BoxScope.(PageData) -> Unit = { DefaultEmptyLayout(it) },
    error: @Composable BoxScope.(PageData) -> Unit = { DefaultErrorLayout(it) },
    content: @Composable BoxScope.() -> Unit = { }
) {
    CustomStatePage(
        modifier = modifier,
        pageState = pageState,
        retry = retry,
        loading = loading,
        empty = empty,
        error = error,
        content = content
    )
}

/**
 * 如果想完全实现自定义StateLayout，调用这个方法
 */
@Composable
fun CustomStatePage(
    modifier: Modifier = Modifier,
    pageState: PageState,
    retry: (PageData) -> Unit = { },
    loading: @Composable BoxScope.() -> Unit = { },
    empty: @Composable BoxScope.(PageData) -> Unit = { },
    error: @Composable BoxScope.(PageData) -> Unit = { },
    content: @Composable BoxScope.() -> Unit = { }
) {
    Box(modifier = modifier) {
        when (pageState.interactionState) {
            is PageData.Content -> content.invoke(this)
            is PageData.Loading -> loading.invoke(this)
            is PageData.Error -> {
                val errorState = (pageState.interactionState as PageData.Error).copy(
                    retry = retry
                )
                error.invoke(this, errorState)
            }
            is PageData.Empty -> empty.invoke(this, pageState.interactionState)
        }
    }
}





