package com.example.kotlindemo.compose.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

class CollectCompanyDataSource : PagingSource<Int, CollectCompanyItem>() {
    override fun getRefreshKey(state: PagingState<Int, CollectCompanyItem>) = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectCompanyItem> = try {
        //页码未定义置为1
        val currentPage = params.key ?: 1
        //仓库层请求数据
        Log.d("请求页码标记", "请求第${currentPage}页")
        val companyData = getCompanyData(currentPage)
//        delay(2000)

        //如果当前页面在第一页，上一个页面和下一个页面页数为设为null
        val preKey = if (currentPage > 1) currentPage - 1 else null
        //当前页码 小于 总页码 页面加1
//        val nextKey = if (jobData.list.isNotEmpty()) currentPage + 1 else null
        val nextKey = if (currentPage == 1) null else currentPage + 1

        LoadResult.Page(
            data = companyData.list,
            prevKey = preKey,
            nextKey = nextKey
        )

    } catch (e: Exception) {
        LoadResult.Error(throwable = e)
    }

    private fun getCompanyData(pageIndex: Int): CollectCompanyState {
        val jobList = mutableListOf<CollectCompanyItem>()
//        for (i in 0 until 5) {
//            val item = CollectCompanyItem(
//                companyName = "智联招聘 $i",
//                companyIcon = "",
//                companyProperty = "其它",
//                companySize = "1000-9999人",
//                industry = "互联网",
//                address = "北京市朝阳区阜荣街10号首开广场F5层"
//            )
//            jobList.add(item)
//        }
        return CollectCompanyState(
            list =  jobList
        )
    }
}