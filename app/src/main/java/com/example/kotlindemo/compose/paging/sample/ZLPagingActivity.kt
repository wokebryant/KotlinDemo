package com.example.kotlindemo.compose.paging.sample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlindemo.compose.ComposeActivity
import com.example.kotlindemo.compose.paging.FetchStateColumn
import com.example.kotlindemo.compose.paging.PagingState
import com.example.kotlindemo.compose.widget.Screen
import com.example.kotlindemo.compose.widget.refresh.SwipeRefreshLayout
import com.example.kotlindemo.compose.widget.stateLayout.PageState
import com.example.kotlindemo.compose.widget.stateLayout.rememberPageState
import com.example.kotlindemo.study.mvi.core.collectAsStateWithLifecycle
import com.zhaopin.social.compose.ui.ZlColor
import com.zhaopin.social.module_common_util.file.Storage.delete

class ZLPagingActivity : ComposeActivity() {

    @Preview
    @Composable
    override fun MainPage() {
        val viewModel: PagingViewModel = viewModel()
        val state by viewModel.collectAsStateWithLifecycle()
        val pageState = rememberPageState(PageState.Loading())

        when (state.pagingSate) {
            PagingState.Loading -> pageState.showLoading()
            PagingState.Content -> pageState.showContent()
            PagingState.Empty -> pageState.showEmpty()
            PagingState.Error -> pageState.showError()
            else -> { }
        }
        Screen(
            pageState = pageState,
            title = "Compose分页测试",
            onRetry = {
                viewModel.retry()
            }
        ) {
            SwipeRefreshLayout(
                isRefreshing = state.pagingSate == PagingState.Refresh,
                onRefresh = { viewModel.refresh() }
            ) {
                FetchStateColumn(modifier = Modifier.fillMaxSize(), data = state.list) { item, index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(10.dp)
                            .background(ZlColor.C_P3, RoundedCornerShape(8.dp))
                            .padding(start = 10.dp)
                            .clickable {
                                viewModel.deleteItem(index)
//                                viewModel.addItem(0, PagingItem("我是新增的Item"))
//                                viewModel.updateItem(0, (it as PagingItem).copy(name = "我被更新了"))
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        val text = (item as PagingItem).name
                        Text(text = text)
                    }
                }
            }
        }
    }


}
