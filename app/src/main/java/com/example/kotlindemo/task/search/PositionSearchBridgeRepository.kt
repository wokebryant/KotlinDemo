package com.example.kotlindemo.task.search

import com.example.kotlindemo.utils.copyOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @Description 职位搜索桥页面数据仓库
 * @Author LuoJia
 * @Date 2023/10/7
 */
class PositionSearchBridgeRepository(
    private val viewModelScope: CoroutineScope?
) {

    private var historyList: MutableList<PositionSearchListItem> = mutableListOf()
    private var showExpandAll = false
    private var isExpandClick = false

    private val _historyDataFlow = MutableStateFlow(HistoryRepoData())
    val historyDataFlow = _historyDataFlow.asStateFlow()

    private val _eventFlow = MutableSharedFlow<SearchBridgeEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    suspend fun requestHistoryData() {
        val historyData = mutableListOf<PositionSearchListItem>()
        for (i in 0 until 10) {
            val item = mockHistoryItem.copy(
                title = "测试标题 $i",
                id = i.toLong(),
                isSubscribe = false
            )
            historyData.add(item)
        }
        historyList = historyData
        showExpandAll = historyData.isNotEmpty() && historyData.size > 4
        sendHistoryDataFlow()
    }

    suspend fun requestRecommendJobData(): List<SearchRecommendJobItemState> {
        return mockRecommendJobList
    }

    fun clearAllHistoryDataClick() {
        // 判断历史搜索数据中是否有订阅
        val hasSubscribe = historyList.find { it.isSubscribe }
        if (hasSubscribe != null) {
            sendEventFlow(SearchBridgeEvent.ShowUnsubscribeAndClearAllDialog)
        } else {
            sendEventFlow(SearchBridgeEvent.ShowClearAllDialog)
        }
    }

    fun deleteSingleHistory(subscribe: Boolean = false, index: Int) {
        historyList.removeAt(index)
        showExpandAll = historyList.isNotEmpty() && historyList.size > 4 && !isExpandClick
        sendHistoryDataFlow()
    }

    fun deleteAllHistory() {
        historyList.clear()
        sendHistoryDataFlow()
    }

    fun expandAllHistoryData() {
        isExpandClick = true
        showExpandAll = false
        sendHistoryDataFlow()
    }

    private fun sendHistoryDataFlow() {
        val newList = historyList.copyOf().toMutableList()
        _historyDataFlow.update {
            it.copy(
                showExpandAll = showExpandAll,
                historyList = if (showExpandAll && newList.size > 4) newList.subList(0 ,4) else newList
            )
        }
    }

    private fun sendEventFlow(event: SearchBridgeEvent) {
        viewModelScope?.launch {
            _eventFlow.emit(event)
        }
    }

}

data class HistoryRepoData(
    val showExpandAll: Boolean = false,
    val historyList: MutableList<PositionSearchListItem> = mutableListOf(),
)