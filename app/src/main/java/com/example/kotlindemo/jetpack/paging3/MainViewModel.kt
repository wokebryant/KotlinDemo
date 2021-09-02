package com.example.kotlindemo.jetpack.paging3

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainViewModel : ViewModel() {

    fun getPagingData(): Flow<PagingData<Repo>> {
        return Resposity.getPagingData().cachedIn(viewModelScope)
    }

    private val _pagingData = Resposity.getPagingData()
        .cachedIn(viewModelScope)
        .asLiveData()
        .let { it as MutableLiveData<PagingData<Repo>> }

    val pagingData: LiveData<PagingData<Repo>> = _pagingData

    fun updatePagingData(data: PagingData<Repo>) {
        _pagingData.value = data
    }
}