package com.example.kotlindemo.task.afterdelivery

import androidx.constraintlayout.widget.Placeholder
import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/06/06
 */

data class AfterDeliveryState(
    // 职位列表
    val jobList: MutableList<AfterDeliveryCardState> = mutableListOf(),
    // 是否全选
    val allSelected: Boolean = false,
    // 投递按钮文案
    val deliveryBtnContent: String = "请选择职位",
    // 投递按钮是否可点击
    val deliveryBtnEnable: Boolean = false,
    // 页面状态
    val pageState: AfterDeliveryPageState = AfterDeliveryPageState.Content
) : IUiState

enum class AfterDeliveryPageState() {
    Loading,
    Content,
    // 首次弹起弹窗推荐为空
    InitEmpty,
    // 点击投递按钮，后续推荐为空
    NextEmpty
}

data class AfterDeliveryCardState(
    val jobName: String,
    val salary: String,
    val companyName: String,
    val companyType: String,
    val companySize: String,
    val skillList: List<String>,
    val avatar: String,
    val hrName: String,
    val hrJob: String,
    val selected: Boolean,
    val onSelectedClick: OnAfterDeliveryItemClick,
    val onItemClick: OnAfterDeliverySelectedClick,
    val isPlaceholder: Boolean = false
)

sealed class AfterDeliveryEvent : IUiEvent {
    // 重置卡片
    object ResetCardList: AfterDeliveryEvent()
    // 添加卡片
    data class AddCard(val item: AfterDeliveryCardState): AfterDeliveryEvent()
    // 更新卡片
    data class UpdateItem(
        val index: Int,
        val itemAfterDeliveryCardState: AfterDeliveryCardState
    ) : AfterDeliveryEvent()
    // 全选点击
    data class AllSelectedClick(val jobList: List<AfterDeliveryCardState>) : AfterDeliveryEvent()
    // 开始曝光
    object StartExpose: AfterDeliveryEvent()
}

internal fun List<Int>.toUiList(
    onItemClick: OnAfterDeliveryItemClick,
    onSelectedClick: OnAfterDeliverySelectedClick
): MutableList<AfterDeliveryCardState> {
    val newList = this.toMutableList()
//    if (newList.size < 4) {
//        newList.add(3)
//        newList.add(4)
////        newList.add(5)
//    }
    val jobList = newList.map {
        getJobCardState(it, onItemClick, onSelectedClick)
    }
    return jobList.toMutableList()
}

private fun getJobCardState(
    index: Int,
    onItemClick: OnAfterDeliveryItemClick,
    onSelectedClick: OnAfterDeliverySelectedClick
): AfterDeliveryCardState {
    return AfterDeliveryCardState(
        jobName = "工业智能 $index",
        salary = "3万·13薪",
        companyName = "科大讯飞",
        companyType = "互联网100强",
        companySize = "1000-9999人",
        skillList = listOf("3-5年", "学历不限", "渠道销售", "企业客户"),
        avatar = "",
        hrName = "刘先生",
        hrJob = "招聘经理",
        selected = false,
        onSelectedClick = onSelectedClick,
        onItemClick = onItemClick,
//        isPlaceholder = index == 3 || index == 4
    )
}

internal typealias OnAfterDeliveryItemClick = (Int, AfterDeliveryCardState) -> Unit
internal typealias OnAfterDeliverySelectedClick = (Int, AfterDeliveryCardState) -> Unit



