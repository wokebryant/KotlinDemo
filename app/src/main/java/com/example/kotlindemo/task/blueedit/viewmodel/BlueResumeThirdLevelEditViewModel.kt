package com.example.kotlindemo.task.blueedit.viewmodel

import com.example.kotlindemo.study.mvi.core.MviBaseViewModel
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.model.BlueEditInfoSaveData
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/30
 */
class BlueResumeThirdLevelEditViewModel : MviBaseViewModel<BlueEditPageState, BlueEditEvent>() {

    private var pageState: BlueEditPageState? = null

    private var answerList = mutableListOf<BlueEditInfoSaveData>()

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
            answerList.add(answer)
        }

        this.pageState = pageState
        setState {
            copy(
                title = pageState.title,
                itemList = pageState.itemList,
            )
        }
    }

    /**
     * 三级标签选择回调数据
     */
    fun thirdLevelTagSaveData(answer: BlueEditInfoSaveData) {
        var hasQuestion = false
        val questionId = answer.questionId
        answerList.forEachIndexed { index, blueEditInfoSaveData ->
            // 存在当前问题ID，则更新
            if (questionId == blueEditInfoSaveData.questionId) {
                answerList[index] = answer
                hasQuestion = true
            }
        }

        // 不存在当前问题ID，则追加
        if (!hasQuestion) {
            answerList.add(answer)
        }

        updatePageBottom()
        setEvent(BlueEditEvent.SaveCommonAnswer(answerList))
    }

    /**
     * 更新底部区域
     */
    private fun updatePageBottom() {
        setState {
            copy(hasSelected = hasSelected())
        }
    }

    fun hasSelected(): Boolean {
        var hasSelected = false
        answerList.forEach {
            if (it.answerList.isNotEmpty()) {
                hasSelected = true
            }
        }
        return hasSelected
    }

}