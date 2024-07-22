package com.example.kotlindemo.compose.paging.sample

import com.example.kotlindemo.compose.paging.ComposeItem
import com.example.kotlindemo.compose.paging.PagingState
import com.example.kotlindemo.compose.paging.datasource.FetchState
import com.example.kotlindemo.study.mvi.PageState
import com.example.kotlindemo.study.mvi.core.IUiEffect
import com.example.kotlindemo.study.mvi.core.IUiState
import com.zhaopin.list.multitype.loadmore.model.LoadMoreStatus

data class PagingState(
    val title: String = "Compose分页示例",
    val list: List<ComposeItem> = listOf(),
    val pagingSate: PagingState = PagingState.Content,
) : IUiState

data class PagingItem (
    val name: String,
) : ComposeItem

sealed class PagingEffect : IUiEffect {

}