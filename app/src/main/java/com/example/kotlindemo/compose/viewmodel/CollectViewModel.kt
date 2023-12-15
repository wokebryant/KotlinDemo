package com.example.kotlindemo.compose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.kotlindemo.compose.data.CollectDataSource
import com.example.kotlindemo.compose.data.CollectRepository

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/12/15
 */
class CollectViewModel(
    repository: CollectRepository = CollectRepository()
) : ViewModel() {


    val jobData = repository.requestJobData().cachedIn(viewModelScope)

}