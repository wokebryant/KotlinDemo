package com.example.kotlindemo.compose.data

import androidx.paging.Pager
import androidx.paging.PagingConfig

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/12/15
 */
class CollectRepository {

    private val pageSize = 10

    fun requestJobData() = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = { CollectJobDataSource() }
    ).flow


    fun requestCompanyData() = Pager(
        config = PagingConfig(pageSize),
        pagingSourceFactory = {  CollectCompanyDataSource() }
    ).flow

}