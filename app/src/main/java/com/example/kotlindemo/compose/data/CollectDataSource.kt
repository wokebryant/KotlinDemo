package com.example.kotlindemo.compose.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.lang.Exception

/**
 * @Description https://juejin.cn/post/7010900020027359240?searchId=2023121517071607D82695AA4DB6A2B92E
 * @Author LuoJia
 * @Date 2023/12/15
 */
class CollectDataSource : PagingSource<Int, CollectJobItem>() {

    override fun getRefreshKey(state: PagingState<Int, CollectJobItem>) = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectJobItem> = try {
        //页码未定义置为1
        val currentPage = params.key ?: 1
        //仓库层请求数据
        Log.d("请求页码标记", "请求第${currentPage}页")
        val jobData = getJobData(currentPage)

        //如果当前页面在第一页，上一个页面和下一个页面页数为设为null
        val preKey = if (currentPage > 1) currentPage - 1 else null
        //当前页码 小于 总页码 页面加1
        val nextKey = if (jobData.list.isNotEmpty()) currentPage + 1 else null

        LoadResult.Page(
            data = jobData.list,
            prevKey = preKey,
            nextKey = nextKey
        )

    } catch (e: Exception) {
        LoadResult.Error(throwable = e)
    }

    private fun getJobData(pageIndex: Int): CollectJobState {
        val jobList = mutableListOf<CollectJobItem>()
        for (i in 0 until 20) {
            val item = CollectJobItem(
                jobName = "职位名称 $pageIndex $i",
                salary = "1万-3万",
                companyName = "智联招聘",
                companyStrength = "上市公司",
                companySize = "9999人以上",
                skillList = listOf("1-3年", "大专", "企业客户", "电话销售"),
                hrAvatar = "",
                hrOnline = true,
                hrName = "李女士",
                publishDate = "8月21号"
            )
            jobList.add(item)
        }
        return CollectJobState(
            list =  jobList
        )
    }

}