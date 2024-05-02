package com.example.kotlindemo.task.blueedit.repository

import com.example.kotlindemo.study.mvi.core.MviBaseRepository
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.bean.BlueResumeEditQuestionBean
import com.example.kotlindemo.task.blueedit.data.BlueMockDataSource
import com.example.kotlindemo.task.blueedit.data.BlueResumeEditDataSource
import com.example.kotlindemo.task.blueedit.model.BlueEditInfoSaveData
import com.example.kotlindemo.task.blueedit.model.BlueResumeEditQAResponse
import com.google.gson.Gson
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.toast.showToast
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * @Description 蓝领简历编辑数据仓库
 * @Author LuoJia
 * @Date 2024/04/26
 */
class BlueResumeEditRepository(
    private val dataSource: BlueResumeEditDataSource = BlueResumeEditDataSource()
) : MviBaseRepository<BlueEditEvent>() {

    /**
     * 问题数据
     */
    private var qaData: BlueResumeEditQuestionBean? = null

    /** 答案列表 */
    private var saveMap = mutableMapOf<Int, List<BlueEditInfoSaveData>>()
    /** 工作时长ID */
    private var workDurationId = ""

    /**
     * 获取问题
     */
    fun getQAData(): Flow<BlueResumeEditQAResponse> = callbackFlow {
        val params = mutableMapOf<String, Any?>().apply {

        }
        qaData = BlueMockDataSource.getMockQABean()
        trySend(BlueResumeEditQAResponse.Complete(
            data = qaData!!
        ))
        close()
//        dataSource.getQAData(
//            params = params,
//            callback = {
//                // 请求成功
//                if (it.isSuccess) {
//
//                }
//                // 请求失败
//                else {
//
//                }
//            }
//        )

        awaitClose()
    }


    fun saveWorkDurationStepAnswer(id: String) {
        workDurationId = id
    }

    fun saveCommonStepAnswer(
        position: Int,
        answerList: List<BlueEditInfoSaveData>
    ) {
        if (saveMap.contains(position)) {
            saveMap[position] = answerList
        } else {
            saveMap[position] = answerList
        }
    }

    /**
     * 点击保存请求接口
     */
    fun uploadSaveAnswer() {
        // 将Map数据转换成Json字符串
        val saveList = mutableListOf<BlueEditInfoSaveData>()
        saveMap.forEach {
            saveList.addAll(it.value)
        }
        val json = Gson().toJson(saveList)
    }

}