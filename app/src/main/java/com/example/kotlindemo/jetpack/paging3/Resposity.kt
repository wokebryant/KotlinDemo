package com.example.kotlindemo.jetpack.paging3

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kotlindemo.retrofit.ServiceCreator
import kotlinx.coroutines.flow.Flow

object Resposity {

    private const val PAGE_SIZE = 50        //每页包含的数据条数

    private val gitHubService = ServiceCreator.create<GitHubService>(ServiceType.GITHUB)

    fun getPagingData(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {
                RepoPagingSource(gitHubService)
            }

        ).flow
    }
}