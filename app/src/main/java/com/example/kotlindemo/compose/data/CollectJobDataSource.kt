package com.example.kotlindemo.compose.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.delay
import java.lang.Exception

/**
 * @Description https://juejin.cn/post/7010900020027359240?searchId=2023121517071607D82695AA4DB6A2B92E
 * @Author LuoJia
 * @Date 2023/12/15
 */
class CollectJobDataSource : PagingSource<Int, CollectJobItem>() {

    override fun getRefreshKey(state: PagingState<Int, CollectJobItem>) = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectJobItem> = try {
        //页码未定义置为1
        val currentPage = params.key ?: 1
        //仓库层请求数据
        Log.d("请求页码标记", "请求第${currentPage}页")
        val jobData = getJobData(currentPage)
        delay(2000)

        //如果当前页面在第一页，上一个页面和下一个页面页数为设为null
        val preKey = if (currentPage > 1) currentPage - 1 else null
        //当前页码 小于 总页码 页面加1
//        val nextKey = if (jobData.list.isNotEmpty()) currentPage + 1 else null
        val nextKey = if (currentPage == 5) null else currentPage + 1

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
        for (i in 0 until 10) {
            val item = CollectJobItem(
                isOffline = i == 3,
                jobName = "职位名称哈哈哈哈哈哈哈哈哈哈哈哈 $pageIndex $i",
                firstTagUrl = "https://img09.zhaopin.cn/2012/other/mobile/capp/position/ui21/tag_JD_daizhao_3x.png?w=84&h=48&r=3",
                salary = "1万-3万",
                companyName = if (pageIndex == 1 && i == 0) "智联招聘" else "哈哈哈哈",
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