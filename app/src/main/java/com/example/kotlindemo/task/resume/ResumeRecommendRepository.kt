package com.example.kotlindemo.task.resume

import com.example.kotlindemo.study.mvi.core.MviBaseRepository
import java.util.concurrent.Flow

/**
 * @Description 简历点后推荐页面Repository
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendRepository : MviBaseRepository<ResumeRecommendEvent>() {

    private var dataList = mutableListOf<Any>()

    /**
     * 获取简历卡片数据
     */
    fun requestResumeCarData() {

    }

    /**
     * 删除Item
     */
    private fun removeItem(item: Any?, position: Int = -1) {
        if (position >= dataList.size) {
            return
        }
        item?.let {
            if (position >= 0) dataList.removeAt(position)
            setEvent(ResumeRecommendEvent.RemoveItem(position, it))
        }
    }

    /**
     * 更新Item
     */
    private fun updateItem(item: Any?, position: Int = -1) {
        if (position >= dataList.size) {
            return
        }
        item?.let {
            dataList[position] = it
            setEvent(ResumeRecommendEvent.UpdateItem(position, it))
        }
    }

}