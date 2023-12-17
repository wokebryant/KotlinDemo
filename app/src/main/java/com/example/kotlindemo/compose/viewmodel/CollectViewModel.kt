package com.example.kotlindemo.compose.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.kotlindemo.compose.data.CollectJobDataSource
import com.example.kotlindemo.compose.data.CollectJobItem
import com.example.kotlindemo.compose.data.CollectRepository
import com.example.kotlindemo.jetpack.paging3.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/12/15
 */
class CollectViewModel(
    private val repository: CollectRepository = CollectRepository()
) : ViewModel() {

    val pageList = arrayOf("职位", "公司")

    private val removeItemFlow = MutableStateFlow(mutableListOf<Any>())

    private val _jobData = MutableStateFlow<PagingData<CollectJobItem>>(PagingData.empty())
    val jobData = _jobData.asStateFlow()

    val companyData = repository.requestCompanyData().cachedIn(viewModelScope)

    fun getJobData() {
        viewModelScope.launch {
            repository.requestJobData()
                .cachedIn(viewModelScope)
                .combine(removeItemFlow) { pagingData, removed ->
                    pagingData.filter {
                        it !in removed
                    }
                }
                .collectLatest {
                    _jobData.value = it
            }
        }
    }

    fun removeItem(item: Any?) {
        item?.let {
            val removes = removeItemFlow.value
            val list = mutableListOf(it)
            list.addAll(removes)
            removeItemFlow.value = list
        }
    }

}