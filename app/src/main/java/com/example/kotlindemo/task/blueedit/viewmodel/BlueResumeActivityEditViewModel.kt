package com.example.kotlindemo.task.blueedit.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.blueedit.adapter.BlueTagType
import com.example.kotlindemo.task.blueedit.bean.BlueResumeEditQuestionBean
import com.example.kotlindemo.task.blueedit.bean.WorkPreferenceBean
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.model.BlueEditInfoSaveData
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState
import com.example.kotlindemo.task.blueedit.model.BlueEditState
import com.example.kotlindemo.task.blueedit.model.BlueResumeEditQAResponse
import com.example.kotlindemo.task.blueedit.model.BlueResumeTagState
import com.example.kotlindemo.task.blueedit.repository.BlueResumeEditRepository
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.toast.showToast
import kotlinx.coroutines.launch

/**
 * @Description 蓝领简历编辑ViewModel
 * @Author LuoJia
 * @Date 2024/04/26
 */
class BlueResumeActivityEditViewModel(
    private val repository: BlueResumeEditRepository = BlueResumeEditRepository()
) : MviBaseViewModel<BlueEditState, BlueEditEvent>() {

    override fun createInitialState() = BlueEditState()

    var curPageIndex = 0
    private var listSize = 0
    /** 是否为最后一页 */
    private val isLastPage: Boolean
        get() = curPageIndex == listSize - 1
    /** 是否有工作时长页面 */
    private var hasWorkDuration = false

    /**
     * 请求问答数据
     */
    fun requestQAData() {
        viewModelScope.launch(exceptionHandler) {
            repository.getQAData().collect {
                when(it) {
                    // 请求成功
                    is BlueResumeEditQAResponse.Complete -> {
                        setState {
                            copy(
                                list = it.data.toPageUiList(),
                                progress = 100 / listSize,
                                showBottomButton = !hasWorkDuration,
                                bottomBtnContent = if (isLastPage) "保存" else "下一步",
                            )
                        }
                    }
                    // 请求为空
                    is BlueResumeEditQAResponse.Empty -> {

                    }
                    //  请求错误
                    is BlueResumeEditQAResponse.Error -> {

                    }
                }
            }
        }
    }

    /**
     * 更新进度条
     */
    fun updateProgress() {
        withState {
            val curProgress = it.progress
            val newProgress = curProgress + 100 / listSize
            setState { copy(progress = newProgress) }
        }
    }

    /**
     * 更新底部按钮
     */
    fun updateBottom(
        hasSelected: Boolean,
        must: Boolean
    ) {
        val buttonContent = if (isLastPage) "保存" else "下一步"
        setState { copy(
            // 非必填，按钮始终高亮
            bottomBtnEnable = hasSelected || !must,
            bottomBtnContent = buttonContent,
            showBottomButton = !(curPageIndex == 0 && hasWorkDuration)
        ) }
    }

    /**
     * 展示下一页
     */
    fun showNextPage() {
        setEvent(BlueEditEvent.ShowNextPage)
    }

    /**
     * 保存非工作时长页的标签选择数据
     */
    fun saveCommonPageAnswer(
        position: Int,
        answerList: List<BlueEditInfoSaveData>,
    ) {
        repository.saveCommonStepAnswer(position, answerList)

        currentActivity()?.showToast(answerList.first().answerList.toString())
    }

    /**
     * 保存工作时长的标签选择数据
     */
    fun saveWorkDurationPageAnswer(id: String) {
        repository.saveWorkDurationStepAnswer(id)

    }

    /**
     * 点击保存请求接口
     */
    fun uploadSaveAnswer() {
        repository.uploadSaveAnswer()
    }

    /**
     * 获取问答页面UI State列表
     */
    private fun BlueResumeEditQuestionBean.toPageUiList(): List<Any> {
        val workDurationPage = this.toWorkDurationState()
        val pageList = this.workPreferenceQuestionList?.map {
            BlueEditPageState(
                title = it.title ?: "",
                must =  it.must ?: false,
                max = it.max ?: 0,
                itemList = it.toItemUiList(it.type ?: 1)
            )
        }?.toMutableList() ?: mutableListOf()

        if (workDurationPage != null) {
            hasWorkDuration = true
            pageList.add(0, workDurationPage)
        }

        listSize = pageList.size

        return pageList
    }

    /**
     * 获取工作时间UI State
     */
    private fun BlueResumeEditQuestionBean.toWorkDurationState(): BlueEditPageState? {
        // 选中的值
        val selectedValue = this.durationInMonthsQuestion?.userSaveMonths?.durationInMonthsValue ?: -1

        if (this.durationInMonthsQuestion?.hasThisQuestion == true) {
            val list = this.durationInMonthsQuestion.durationInMonthsList?.map {
                BlueResumeTagState(
                    name = it.name ?: "",
                    selected = it.durationInMonthsValue == selectedValue,
                    type = BlueTagType.Normal,
                    id = it.durationInMonthsValue ?: ""
                )
            }?.toMutableList()?: mutableListOf()

            val footTag = BlueResumeTagState(
                name = "",
                selected = false,
                type = BlueTagType.Add,
            )
            list.add(footTag)
            return BlueEditPageState(
                title = this.durationInMonthsQuestion.title ?: "",
                must = true,
                max = 1,
                isWorkDuration = true,
                itemList = mutableListOf(
                    BlueEditPageState.ItemState(
                        title = "",
                        list = list
                    )
                )
            )
        } else {
            return null
        }
    }

    /**
     * 获取问答Item
     */
    private fun WorkPreferenceBean.toItemUiList(type: Int): List<BlueEditPageState.ItemState> {
        val isThreeLevel = !answerList?.first()?.subQuestionList.isNullOrEmpty()
        val list = if (isThreeLevel) {
            // 生成三级列表
            this.answerList?.map { answer ->
                // 子问题只有一个
                val subQuestion = answer.subQuestionList?.first()
                BlueEditPageState.ItemState(
                    title = subQuestion?.title ?: "",
                    list = subQuestion?.subAnswerList?.map { subAnswer ->
                        BlueResumeTagState(
                            name = subAnswer.name ?: "",
                            selected = subAnswer.selected ?: false,
                            type = BlueTagType.Normal,
                            id = subAnswer.id ?: "",
                            level = "3",
                            questionId = subQuestion.questionId ?: "",
                            questionType = subQuestion.questionType ?: "",
                            parentAnswerId = subQuestion.parentAnswerId ?: "",
                            parentQuestionId =subQuestion.parentQuestionId ?: ""
                        )
                    } ?: emptyList()
                )
            } ?: emptyList()
        } else {
            // 生成二级列表
            val isCustomType = type == 3
            val secondLevelTagList = this.answerList?.map { answer ->
                BlueResumeTagState(
                    name = answer.name ?: "",
                    selected = answer.selected ?: false,
                    type = if (isCustomType) BlueTagType.Delete else BlueTagType.Normal,
                    id = answer.id ?: "",
                    level = "2",
                    questionId = this.questionId ?: "",
                    questionType = this.questionType ?: "",
                )
            }?.toMutableList() ?: mutableListOf()
            // 如果是自定义标签类型,尾部追加
            if (isCustomType) {
                val footerTag = BlueResumeTagState(
                    name = "",
                    selected = false,
                    type = BlueTagType.Add,
                )
                secondLevelTagList.add(footerTag)
            }
            mutableListOf(BlueEditPageState.ItemState(
                title = "",
                list = secondLevelTagList
            ))
        }

        return list
    }

}