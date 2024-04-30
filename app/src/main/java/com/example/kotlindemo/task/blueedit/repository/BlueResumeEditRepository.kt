package com.example.kotlindemo.task.blueedit.repository

import com.example.kotlindemo.study.mvi.core.MviBaseRepository
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.bean.BlueResumeEditQuestionBean
import com.example.kotlindemo.task.blueedit.data.BlueMockDataSource
import com.example.kotlindemo.task.blueedit.data.BlueResumeEditDataSource
import com.example.kotlindemo.task.blueedit.model.BlueEditInfoSaveData
import com.example.kotlindemo.task.blueedit.model.BlueResumeEditQAResponse
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
    private var saveData = mutableListOf<BlueEditInfoSaveData>()
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
        saveData.addAll(answerList)
    }

}