package com.example.kotlindemo.task.afterdelivery

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/06/06
 */
class AfterDeliveryViewModel(
    private val repository: AfterDeliveryRepository = AfterDeliveryRepository()
) : MviBaseViewModel<AfterDeliveryState, AfterDeliveryEvent>() {

    companion object {
        private const val WHAT = 0X1001
        private const val ADD_CARD_INTERVAL = 0L
    }

    override fun createInitialState() = AfterDeliveryState()

    init {
        viewModelScope.launch {
            repository.eventFlow.collect {
                setEvent(it)
            }
        }
    }

    /** 添加卡片Handler */
    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var index = msg.obj as Int
            // 如果卡片数量不到5个，就继续添加下去
            if (index < 5) {
                withState {
                    it.jobList.getOrNull(index)?.let { item ->
                        setEvent(AfterDeliveryEvent.AddCard(item))
                    }
                    if (index == it.jobList.size - 1) {
                        viewModelScope.launch {
                            delay(800)
                            setEvent(AfterDeliveryEvent.StartExpose)
                        }
                    }
                }

                val newMsg = obtainMessage(WHAT, ++index)
                sendMessageDelayed(newMsg, ADD_CARD_INTERVAL)
            }
        }
    }

    /**
     * 请求数据
     */
    fun requestJobList() {
        viewModelScope.launch(exceptionHandler) {
//            delay(2000)
            val jobList = repository.requestJobList()
            setState {
                copy(
                    jobList = jobList.toUiList(::onItemClick, ::onSelectedClick),
                    allSelected = false,
                    pageState = AfterDeliveryPageState.Content
                )
            }
            delay(600)
            startAddCard()
        }
    }

    /**
     * 开始添加卡片
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun startAddCard() {
        handler.removeCallbacksAndMessages(null)
        //开始添加卡片
        handler.obtainMessage(WHAT, 0).sendToTarget()
    }

    /**
     * 卡片点击
     */
    private fun onItemClick(index: Int, state: AfterDeliveryCardState) {

    }

    /**
     * 选择点击
     */
    private fun onSelectedClick(index: Int, state: AfterDeliveryCardState) {
        setEvent(AfterDeliveryEvent.UpdateItem(index, state))
        withState {
            it.jobList[index] = state
            // 获取选中的卡片数量
            var count = 0
            it.jobList.forEach { item ->
                (item as? AfterDeliveryCardState)?.let {
                    if (item.selected)  {
                        count ++
                    }
                }
            }
            setState {
                copy(
                    allSelected = count == 5,
                    deliveryBtnContent = if (count == 0) "请选择职位" else "一键投递${count}个职位",
                    deliveryBtnEnable = count != 0
                )
            }
        }
    }

    /**
     * 全选点击
     */
    fun onAllSelectedClick() {
        withState {
            if (!it.allSelected) {
                val newJobList = it.jobList.map { item ->
                    item.copy(selected = true)
                }

                setState {
                    copy(
                        jobList = newJobList.toMutableList(),
                        allSelected = true,
                        deliveryBtnContent = "一键投递5个职位",
                        deliveryBtnEnable = true
                    )
                }
                setEvent(AfterDeliveryEvent.AllSelectedClick(newJobList))
            } else {
                val newJobList = it.jobList.map { item ->
                    item.copy(selected = false)
                }

                setState {
                    copy(
                        jobList = newJobList.toMutableList(),
                        allSelected = false,
                        deliveryBtnContent = "请选择职位",
                        deliveryBtnEnable = false
                    )
                }
                setEvent(AfterDeliveryEvent.AllSelectedClick(newJobList))
            }
        }
    }

    /**
     * 投递按钮点击
     */
    fun deliveryClick() {
        // 重置
        setEvent(AfterDeliveryEvent.ResetCardList)
        // 获取下一批卡片
        val nextJobList = repository.requestJobList()
        setState {
            copy(
                jobList = nextJobList.toUiList(::onItemClick, ::onSelectedClick),
                allSelected = false,
                deliveryBtnContent = "请选择职位",
                deliveryBtnEnable = false,
                pageState = AfterDeliveryPageState.Content
            )
        }
        startAddCard()
    }

    fun removeCallbacksAndMessages() {
        handler.removeCallbacksAndMessages(null)
    }

}