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
 * @Description
 * @Author LuoJia
 * @Date 2024/04/12
 */


/**
 * 通用状态页数据
 */
sealed class PageData {
    object Loading : PageData()

    object Success : PageData()

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

    /** 内部交互的状态 */
    internal var interactionState by mutableStateOf(state)

    /** 供外部获取当前状态 */
    val state: PageData
        get() = interactionState

    /** 供外部修改当前状态 */
    fun setState(pageData: PageData) {
        interactionState = pageData
    }

    val isLoading: Boolean
        get() = interactionState is PageData.Loading

    companion object {
        fun loading() = PageData.Loading

        fun success() = PageData.Success

        fun empty(state: EmptyState = defaultEmptyState) = PageData.Empty(state)

        fun error(state: ErrorState = defaultErrorState) = PageData.Error(state)
    }
}

@Composable
fun rememberPageState(state: PageData = PageData.Loading): PageState {
    return remember {
        PageState(state)
    }
}

@Composable
fun CommonStateLayout(
    modifier: Modifier = Modifier,
    pageState: PageState = rememberPageState(),
    retry: (PageData) -> Unit = { },
    loading: @Composable BoxScope.() -> Unit = { DefaultLoadingLayout() },
    empty: @Composable BoxScope.(PageData) -> Unit = { DefaultEmptyLayout(it) },
    error: @Composable BoxScope.(PageData) -> Unit = { DefaultErrorLayout(it) },
    content: @Composable BoxScope.() -> Unit = { }
) {
    StateLayout(
        modifier = modifier,
        pageState = pageState,
        retry = retry,
        loading = loading,
        empty = empty,
        error = error,
        content = content
    )
}

@Composable
fun StateLayout(
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
            is PageData.Success -> content.invoke(this)
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





