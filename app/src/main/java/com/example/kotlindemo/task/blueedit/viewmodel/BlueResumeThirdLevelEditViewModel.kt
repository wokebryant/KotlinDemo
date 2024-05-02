package com.example.kotlindemo.task.blueedit.viewmodel

import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.blueedit.adapter.BlueTagType
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.model.BlueEditInfoSaveData
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState
import com.example.kotlindemo.task.blueedit.model.BlueResumeTagState
import com.example.kotlindemo.utils.copyOf

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/30
 */
class BlueResumeThirdLevelEditViewModel : MviBaseViewModel<BlueEditPageState, BlueEditEvent>() {

    private var pageState: BlueEditPageState? = null

    private var allAnswerList = mutableListOf<BlueEditInfoSaveData>()

    /** 展开更多尾部State */
    private val footTag = BlueResumeTagState(
        name = "",
        selected = false,
        type = BlueTagType.Expand
    )

    override fun createInitialState() = BlueEditPageState()

    fun initPageState(pageState: BlueEditPageState) {
        // 初始化answerList
        pageState.itemList.forEach {
            val idList = mutableListOf<String>()
            val firstItem = it.list.firstOrNull()
            it.list.forEach { tagState ->
                if (tagState.selected) {
                    idList.add(tagState.id)
                }
            }

            val answer = BlueEditInfoSaveData(
                answerList = idList,
                level = "3",
                questionId = firstItem?.questionId ?: "",
                questionType = firstItem?.questionType ?: "",
                parentAnswerId = firstItem?.parentAnswerId ?: "",
                parentQuestionId = firstItem?.parentQuestionId ?: ""
            )
            allAnswerList.add(answer)
        }

        this.pageState = pageState
        setState {
            copy(
                title = pageState.title,
                itemList = pageState.itemList.toShowList(),
            )
        }
    }

    private fun List<BlueEditPageState.ItemState>.toShowList(): List<BlueEditPageState.ItemState> {
        val showList = mutableListOf<BlueEditPageState.ItemState>()
        this.forEach {
            if (it.list.size > 9) {
                val tagList = it.list.toMutableList()
                val subList = tagList.subList(0, 8).toMutableList()
                subList.add(footTag)
                val item = it.copy(list = subList)
                showList.add(item)
            } else {
                showList.add(it)
            }
        }
        return showList
    }

    /**
     * 三级标签点击
     */
    fun onTagClick(
        itemPosition: Int,
        tagPosition: Int,
        selectedList: MutableSet<Int>,
        fromFoldItem: Boolean,
        isAdd: Boolean
    ) {
        updateWhenClick(
            itemPosition = itemPosition,
            tagPosition = tagPosition,
            selectedList = selectedList,
            isExpand = false,
            fromFoldItem = fromFoldItem,
            isAdd = isAdd
        )
    }

    /**
     * 展开更多点击
     */
    fun onExpandClick(position: Int, selectedList: MutableSet<Int>) {
        updateWhenClick(
            itemPosition = position,
            tagPosition = 8,
            selectedList = selectedList,
            isExpand = true,
            fromFoldItem = false,
            isAdd = false
        )
    }

    private fun updateWhenClick(
        itemPosition: Int,
        tagPosition: Int,
        selectedList: MutableSet<Int>,
        isExpand: Boolean,
        fromFoldItem: Boolean,
        isAdd: Boolean
    ) {
        // 拦截标签选择限制
        val allSelectedSize = getAllSelectedSize()
        if (allSelectedSize >= (pageState?.max ?: 5) && isAdd) {
            setEvent(BlueEditEvent.ShowLimitToast("最多选择${allSelectedSize}个"))
            if (selectedList.contains(tagPosition)) {
                selectedList.remove(tagPosition)
            }
            return
        }

        val itemList = pageState?.itemList?.toMutableList() ?: mutableListOf()
        val tagList = itemList.getOrNull(itemPosition)?.list ?: emptyList()
        if (tagList.isNotEmpty()) {
            var newTagList = tagList.mapIndexed { index, blueResumeTagState ->
                blueResumeTagState.copy(selected = selectedList.contains(index))
            }.toMutableList()
            // 如果是来自折叠Item的标签点击，重新计算需要展示的标签列表
            if (fromFoldItem) {
                newTagList = newTagList.subList(0, 8)
                newTagList.add(footTag)
            }
            val newItem = itemList.getOrNull(itemPosition)?.copy(list = newTagList)!!

            // 更新Item
            setEvent(BlueEditEvent.UpdateThirdLevelItem(itemPosition, newItem))

            if (!isExpand) {
                // 更新答案和底部按钮
                updateAnswerAndBottom(
                    firstTag = newTagList.firstOrNull(),
                    answerList = getSelectedAnswerIdList(selectedList, newTagList)
                )
            }
        }
    }

    /**
     * 更新Answer列表和底部UI
     */
    private fun updateAnswerAndBottom(
        firstTag: BlueResumeTagState?,
        answerList: List<String>
    ) {
        // 生成答案
        val answer = BlueEditInfoSaveData(
            answerList = answerList,
            level = "3",
            questionId = firstTag?.questionId ?: "",
            questionType = firstTag?.questionType ?: "",
            parentAnswerId = firstTag?.parentAnswerId ?: "",
            parentQuestionId = firstTag?.parentQuestionId ?: ""
        )

        var hasQuestion = false
        val questionId = answer.questionId
        allAnswerList.forEachIndexed { index, blueEditInfoSaveData ->
            // 存在当前问题ID，则更新
            if (questionId == blueEditInfoSaveData.questionId) {
                allAnswerList[index] = answer
                hasQuestion = true
            }
        }

        // 不存在当前问题ID，则追加
        if (!hasQuestion) {
            allAnswerList.add(answer)
        }

        setState {
            copy(hasSelected = hasSelected())
        }
        setEvent(BlueEditEvent.SaveCommonAnswer(allAnswerList))
    }

    private fun getSelectedAnswerIdList(
        selectedList: Set<Int>,
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

    fun hasSelected(): Boolean {
        var hasSelected = false
        allAnswerList.forEach {
            if (it.answerList.isNotEmpty()) {
                hasSelected = true
            }
        }
        return hasSelected
    }

    /**
     * 获取选择的总数量
     */
    private fun getAllSelectedSize(): Int {
        var size = 0
        allAnswerList.forEach {
            size += it.answerList.size
        }
        return size
    }

}