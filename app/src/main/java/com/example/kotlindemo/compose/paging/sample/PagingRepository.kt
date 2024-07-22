package com.example.kotlindemo.compose.paging.sample

import com.example.kotlindemo.compose.paging.ComposeItem
import com.example.kotlindemo.compose.paging.ComposePagingRepository
import com.example.kotlindemo.compose.paging.PagingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update

class PagingRepository(
    coroutineScope: CoroutineScope
) : ComposePagingRepository<PagingEffect>(coroutineScope) {

    private var curPage = 1

    var isDoRefresh = false

    /**
     * 首次加载
     */
    override suspend fun loadFirst(): List<ComposeItem>? {
        curPage = 1
        if (isDoRefresh) {
            pagingState.value = PagingState.Refresh
        } else {
            pagingState.value = PagingState.Loading
        }
        delay(2000)

        // mock数据,模拟网络请求
        val firstPageList = mutableListOf<PagingItem>()
        for (i in 0 until 5) {
            val pagingItem = PagingItem(
                name = "我是Item $i"
            )
            firstPageList.add(pagingItem)
        }
        pagingState.value = PagingState.Content

        // 在这里根据返回的数据判断应该展示什么页面
        // 返回emptyList，代表加载为空
        // 返回null，代表加载错误
        // 返回非空List，代表加载正常有数据
        return firstPageList
    }

    /**
     * 加载更多
     */
    override suspend fun loadMore(): List<ComposeItem>? {
        curPage ++

        delay(2000)
        val morePageList = mutableListOf<PagingItem>()
        for (i in (curPage - 1) * 5 until curPage * 5) {
            val pagingItem = PagingItem(
                name = "我是Item $i"
            )
            morePageList.add(pagingItem)
        }
        if (curPage >= 4) {
            return null
        } else {
            return morePageList
        }
    }


}