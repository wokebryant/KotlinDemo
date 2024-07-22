package com.example.kotlindemo.compose.paging.sample

import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.compose.base.ComposeViewModel
import com.example.kotlindemo.compose.paging.ComposeItem
import kotlinx.coroutines.launch

class PagingViewModel : ComposeViewModel<PagingState, PagingEffect>() {

    override fun createInitialState() = PagingState()

    private val repository: PagingRepository = PagingRepository(viewModelScope)

    init {
        viewModelScope.launch {
            repository.dataFlow.collect {
                // 在这里收集列表数据，并发送给UI
                setState { copy(list = it) }
            }
        }
        viewModelScope.launch {
            repository.pagingState.collect {
                // 刷新状态收集
                setState { copy(pagingSate = it)}
            }
        }
    }

    /**
     * 刷新
     */
    fun refresh(clear: Boolean = true) {
        repository.isDoRefresh = true
        repository.invalidate(clear)
    }

    /**
     * 重试
     */
    fun retry() {
        repository.invalidate()
    }

    /**
     * 删除Item
     */
    fun deleteItem(position: Int) {
        repository.removeItemAt(position)
    }

    /**
     * 添加Item
     */
    fun addItem(position: Int, item: ComposeItem) {
        repository.addItem(item, position)
    }

    /**
     * 更新Item
     */
    fun updateItem(index: Int, item: ComposeItem) {
        repository.setItem(index, item)
    }




}