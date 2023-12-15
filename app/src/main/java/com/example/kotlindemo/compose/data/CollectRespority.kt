package com.example.kotlindemo.compose.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/12/15
 */
class CollectRepository {

    private val pageSize = 20

    fun requestJobData(): Flow<PagingData<CollectJobItem>> {
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = {
                CollectDataSource()
            }
        ).flow
    }

}