package com.example.kotlindemo.jetpack.paging3

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kotlindemo.utils.transformJsonStringToArray

/**
 *  Paging3页面数据生成
 *  Int: 页数的数据类型
 *  Repo：页面每一项目的数据model
 */
class RepoPagingSource(private val gitHubService: GitHubService) : PagingSource<Int, Repo>() {
    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            val repoResponse = /*gitHubService.searchRepos(page, pageSize)*/getMockExamSkillList(page)
            val repoItems = repoResponse.items

            //如果当前页面在第一页，上一个页面和下一个页面页数为设为null
            val preKey = if (page > 1) page - 1 else null
            //为什么当前页面数据为空代表当前页面为最后一页？？？
//            val nextKey = if (repoItems.isNotEmpty()) page + 1 else null
            val nextKey = if (page == 3) null else page + 1
            LoadResult.Page(repoItems, preKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun getMockExamSkillList(page: Int):  RepoResponse {
        val repo = RepoResponse()
        val list = arrayListOf<Repo>()
        for (i in 0..29) {
            val skillInfo = Repo(page * 100 + i, "标题 $i", " 描述", page + i)

            list.add(skillInfo)
        }
        repo.items = list

        return repo
    }





}