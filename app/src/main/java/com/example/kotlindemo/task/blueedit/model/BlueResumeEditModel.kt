package com.example.kotlindemo.task.blueedit.model

import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.example.kotlindemo.task.blueedit.bean.BlueResumeEditQuestionBean
import com.example.kotlindemo.task.blueedit.adapter.BlueTagType
import com.zhaopin.common.widget.mvx.flowLayout.MVXTagUIState

/**
 * @Description 蓝领简历编辑Model
 * @Author LuoJia
 * @Date 2024/04/26
 */

/**
 * 所有问答页面UI State (Activity)
 */
data class BlueEditState(
    /** 简历编辑引导页问答数据 */
    val list: List<Any> = emptyList(),
    /** 进度 */
    val progress: Int = 0,
    /** 底部按钮文案 */
    val bottomBtnContent: String = "下一步",
    /** 是否展示底部按钮 */
    val showBottomButton: Boolean = false,
    /** 底部按钮是否可操作 */
    val bottomBtnEnable: Boolean = false
) : IUiState

sealed class BlueEditEvent : IUiEvent {
    /** 跳转自定义标签页 */
    object JumpCustomTagPage : BlueEditEvent()
    /** 显示删除标签确认弹窗 */
    data class ShowDeleteTagConfirmDialog(
        val state: BlueResumeTagState
    ) : BlueEditEvent()
    /** 保存工作时长ID */
    data class SaveWorkDurationId(val id: String) : BlueEditEvent()
    /** 保存非工作时长数据 */
    data class SaveCommonAnswer(val answerList: List<BlueEditInfoSaveData>) : BlueEditEvent()
    /** 跳转下一页 */
    object ShowNextPage : BlueEditEvent()
    /** 展示选择超限Toast */
    data class ShowLimitToast(val content: String) : BlueEditEvent()
    /** 三级标签Item更新 */
    data class UpdateThirdLevelItem(val position: Int, val item: Any) : BlueEditEvent()
}

/**
 * 单个问答页面UI State (Fragment)
 */
data class BlueEditPageState(
    /** 标题 */
    val title: String = "",
    /** 是否必填 */
    val must: Boolean = false,
    /** 是否为工作时间选择 */
    val isWorkDuration: Boolean = false,
    /** 最多可选 */
    val max: Int = 0,
    /** Item信息（如果是二级问答，listSize = 1, 三级为多个） */
    val itemList: List<ItemState> = emptyList(),
    /** 是否有标签被选中 */
    val hasSelected: Boolean = false
) : IUiState {
    data class ItemState(
        /** 标题 （三级才有）*/
        val title: String,
        /** 标签列表 */
        val list: List<BlueResumeTagState>
    )

    /** 页面问题级别,默认2级 */
    val pageLevel get() = if (itemList.size == 1) 2 else 3
}

sealed class BottomAction {
    object Next: BottomAction()
    object Save: BottomAction()
}

/**
 * 标签UI State
 */
data class BlueResumeTagState(
    override val name: String,
    override val selected: Boolean,
    val type: BlueTagType,
    // 保存需要传递的参数
    val id: String = "",
    val level: String = "",
    val questionId: String = "",
    val questionType: String = "",
    val parentAnswerId: String = "",
    val parentQuestionId: String = ""
) : MVXTagUIState

sealed class BlueResumeEditQAResponse {
    /** 请求成功有数据 */
    data class Complete(val data: BlueResumeEditQuestionBean) : BlueResumeEditQAResponse()
    /** 请求为空 */
    object Empty: BlueResumeEditQAResponse()
    /** 请求错误 */
    object Error: BlueResumeEditQAResponse()
}

/**
 * 蓝领简历编辑上传数据接口定义
 */
data class BlueEditInfoSaveData(
    val answerList: List<String>,
    val level: String,
    val questionId: String,
    val questionType: String,
    val parentAnswerId: String,
    val parentQuestionId: String
)