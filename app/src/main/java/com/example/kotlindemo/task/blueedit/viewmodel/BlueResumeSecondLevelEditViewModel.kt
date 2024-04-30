package com.example.kotlindemo.task.blueedit.viewmodel

import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.blueedit.adapter.BlueTagType
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.model.BlueEditInfoSaveData
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState
import com.example.kotlindemo.task.blueedit.model.BlueResumeTagState
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.common.extension.copyOf
import com.zhaopin.toast.showToast

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/29
 */
class BlueResumeSecondLevelEditViewModel : MviBaseViewModel<BlueEditPageState, BlueEditEvent>() {

    private var pageState: BlueEditPageState? = null

    /** 当前标签列表 */
    private val curList: List<BlueResumeTagState>
        get() = pageState?.itemList?.first()?.list ?: emptyList()

    /** 选中的标签下标 */
    private val selectedList = mutableSetOf<Int>()
    /** 是否为工作时长页面 */
    private val isWorkDurationPage: Boolean
        get() = pageState?.isWorkDuration == true

    override fun createInitialState() = BlueEditPageState()

    fun initPageState(pageState: BlueEditPageState) {
        this.pageState = pageState
        updateSelectedList()
        setState {
            copy(
                title = pageState.title,
                itemList = pageState.itemList,
            )
        }
    }

    /**
     * 二级问题标签点选
     */
    fun secondLevelTagSelect(position: Int, tagState: BlueResumeTagState) {
        if (tagState.type == BlueTagType.Add) {
            // 打开选择时间弹窗
            if (pageState?.isWorkDuration == true) {
                currentActivity()?.showToast("时间")
            } else {
                // 打开自定义标签页面
                setEvent(BlueEditEvent.JumpCustomTagPage)
            }
            return
        }
        when (pageState?.max) {
            // 单选
            1 -> {
                if (selectedList.contains(position)) {
                    return
                }
                selectedList.clear()
                selectedList.add(position)

                // 如果是工作时间页面，跳转下一页
                if (isWorkDurationPage) {
                    if (selectedList.isNotEmpty()) {
                        val selectedPosition = selectedList.first()
                        val answerId = curList.getOrNull(selectedPosition)?.id ?: ""
                        setEvent(BlueEditEvent.SaveWorkDurationId(answerId))
                    }
                    setEvent(BlueEditEvent.ShowNextPage)
                }
            }

            // 多选
            else -> {
                if (selectedList.contains(position)) {
                    selectedList.remove(position)
                } else {
                    // 拦截选择个数是否超限
                    if (selectedList.size >= (pageState?.max ?: 5)) {
                        setEvent(BlueEditEvent.ShowLimitToast("最多选择${selectedList.size}个"))
                        return
                    }
                    selectedList.add(position)
                }
            }
        }

        val newTagList = curList.mapIndexed { index, blueResumeTagState ->
            blueResumeTagState.copy(selected = selectedList.contains(index))
        }
        updatePageState(newTagList)
    }

    /**
     * 二级问题标签删除
     */
    fun secondLevelTagDelete(tagState: BlueResumeTagState) {
        setEvent(BlueEditEvent.ShowDeleteTagConfirmDialog(tagState))
    }

    /**
     * 确认删除
     */
    fun confirmDelete(state: BlueResumeTagState) {
        val newTagList = curList.copyOf().toMutableList()
        if (newTagList.contains(state)) {
            newTagList.remove(state)
            updatePageState(newTagList)
        }
    }

    /**
     * 二级问题标签添加
     */
    fun secondLevelTagAdd() {

    }

    /**
     * 更新当前PageState
     */
    private fun updatePageState(newTagList: List<BlueResumeTagState>) {
        val newItemList = mutableListOf(BlueEditPageState.ItemState(title = "", list = newTagList))
        pageState = pageState?.copy(itemList = newItemList)

        updateSelectedList()
        setState {
            copy(
                itemList = newItemList,
                hasSelected = hasSelected()
            )
        }

        // 每次更新页面都把数据保存到ActivityViewModel
        if (!isWorkDurationPage) {
            val firstItem = curList.firstOrNull()
            val answer = BlueEditInfoSaveData(
                answerList = getSelectedAnswerIdList(selectedList, curList),
                level = "2",
                questionId = firstItem?.questionId ?: "",
                questionType = firstItem?.questionType ?: "",
                parentAnswerId = firstItem?.parentAnswerId ?: "",
                parentQuestionId = firstItem?.parentQuestionId ?: ""
            )
            setEvent(BlueEditEvent.SaveCommonAnswer(mutableListOf(answer)))
        }
    }

    private fun getSelectedAnswerIdList(
        selectedList: MutableSet<Int>,
        curList: List<BlueResumeTagState>
    ): List<String> {
        val idList = mutableListOf<String>()
        curList.forEachIndexed { index, blueResumeTagState ->
            if (selectedList.contains(index)) {
                idList.add(blueResumeTagState.id)
            }
        }

        return idList
    }

    /**
     * 获取最新的选中Set
     */
    private fun updateSelectedList() {
        selectedList.clear()
        curList.forEachIndexed { index, blueResumeTagState ->
            if (blueResumeTagState.selected) {
                selectedList.add(index)
            }
        }
    }

    fun hasSelected() = selectedList.isNotEmpty()

}