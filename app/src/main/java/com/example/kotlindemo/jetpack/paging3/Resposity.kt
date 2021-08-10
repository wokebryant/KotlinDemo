package com.example.kotlindemo.jetpack.paging3

import androidx.paging.*
import com.example.kotlindemo.retrofit.ServiceCreator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object Resposity {

    private const val PAGE_SIZE = 30        //每页包含的数据条数

    private val gitHubService = ServiceCreator.create<GitHubService>(ServiceType.GITHUB)

    fun getPagingData(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingSource(gitHubService)
            }

        ).flow/*.map { value: PagingData<Repo> ->
            //数据转换
            value.map {
                it.name = "标题转换"
                it
            }
            //数据过滤
//            value.filter { repo ->
//                repo.name = "flutter"
//                repo.starCount = 29104
//                true
//            }
        }*/
    }
}