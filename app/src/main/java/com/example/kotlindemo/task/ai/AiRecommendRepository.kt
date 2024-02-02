package com.example.kotlindemo.task.ai

import com.example.kotlindemo.study.mvi.core.MviBaseRepository
import com.example.kotlindemo.task.ai.bean.AiRecommendListBean
import com.example.kotlindemo.task.ai.util.AiRecommendManager
import com.example.kotlindemo.task.ai.util.toQuestionState
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.toast.showToast
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * @Description AI求职推荐列表Repository
 * @Author LuoJia
 * @Date 2024/1/9
 */
class AiRecommendRepository(
    private val dataSource: AiRecommendDataSource = AiRecommendDataSource()
) : MviBaseRepository<AiRecommendEvent>() {

    private var dataList = mutableListOf<Any>()

    /**
     * 请求职位推荐数据
     */
    fun requestJobListData(): Flow<AiRecommendListResponse> = callbackFlow {
        val params = mutableMapOf<String, Any?>()
        // TODO 测试数据
        dataSource.getMockJobListData(
            params = params,
            callback = {
                // 请求成功
                if (it.isSuccess) {
                    dataList = it.getOrNull()?.list?.toMutableList() ?: mutableListOf()
                    trySend(AiRecommendListResponse.Complete(
                        data = AiRecommendListBean(dataList)
                    ))
                    requestQuestionData()
                    close()
                }
                // 请求失败
                else {

                    close()
                }
            }
        )

        awaitClose()
    }

    /**
     * 请求问题卡数据
     */
    fun requestQuestionData() {
        val params = mutableMapOf<String, Any?>()
        // TODO 测试数据
        dataSource.getMockQuestionData(
            params = params,
            callback = {
                if (it.isSuccess) {
                    it.getOrNull()?.let { bean ->
                        addItem(
                            type = AiRecommendCardType.Question,
                            item = bean.toQuestionState(
                                onOptionClick = ::onQuestionOptionClick,
                                onDeleteClick = ::onQuestionDeleteClick,
                                onSureClick = ::onQuestionSureClick
                            ),
                            position = 4
                        )
                    }
                }
            }
        )
    }

    /**
     * 删除问题卡点击
     */
    private fun onQuestionDeleteClick() {
        val questionState =
            dataList.filterIsInstance<AiQuestionState>().firstOrNull()
        removeItem(
            type = AiRecommendCardType.Question,
            item = questionState
        )
    }

    /**
     * 问题卡确认点击
     */
    private fun onQuestionSureClick() {
        setEvent(AiRecommendEvent.MoveToNext)
    }

    /**
     * 问题卡选项点击
     */
    private fun onQuestionOptionClick(index: Int, selectedSet: Set<Int>) {
        val questionState =
            dataList.filterIsInstance<AiQuestionState>().firstOrNull()
        questionState?.let { state ->
            // 将选项列表重置
            val newList = state.optionList.mapIndexed { index, option ->
                val isSelected = selectedSet.contains(index)
                option.copy(isSelected = isSelected)
            }
            updateItem(
                type = AiRecommendCardType.Question,
                item = questionState.copy(
                    optionList = newList
                ),
                position = dataList.indexOfFirst { it is AiQuestionState }
            )
        }
    }

    fun removeJDCard(index: Int, item: AiJobState) {
        if (index >= 0 && index < dataList.size) {
            removeItem(
                type = AiRecommendCardType.Job,
                item = item,
                position = index
            )
        }
    }

    /**
     * 添加Item
     */
    private fun addItem(type: AiRecommendCardType, item: Any?, position: Int = -1) {
        if (position > dataList.size) {
            return
        }
        item?.let {
            if (!AiRecommendManager.isHeader(type)) {
                if (position > -1) dataList.add(position, it) else dataList.add(it)
            }
            setEvent(AiRecommendEvent.AddItem(type, position, it))
        }
    }

    /**
     * 删除Item
     */
    private fun removeItem(type: AiRecommendCardType, item: Any?, position: Int = -1) {
        if (position >= dataList.size) {
            return
        }
        item?.let {
            if (!AiRecommendManager.isHeader(type)) {
                if (position >= 0) dataList.removeAt(position)
            }
            setEvent(AiRecommendEvent.RemoveItem(type, position, it))
        }
    }

    /**
     * 更新Item
     */
    private fun updateItem(type: AiRecommendCardType, item: Any?, position: Int = -1) {
        if (position >= dataList.size) {
            return
        }
        item?.let {
            if (!AiRecommendManager.isHeader(type)) {
                dataList[position] = it
            }
            setEvent(AiRecommendEvent.UpdateItem(type, position, it))
        }
    }

}