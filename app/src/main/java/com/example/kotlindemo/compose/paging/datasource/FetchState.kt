package com.example.kotlindemo.compose.paging.datasource

sealed interface FetchState {
    // 预备请求
    object ReadyToFetch : FetchState
    // 正在请求
    object Fetching : FetchState
    // 完成请求
    object DoneFetching : FetchState
    // 请求错误
    object FetchingError : FetchState
}

