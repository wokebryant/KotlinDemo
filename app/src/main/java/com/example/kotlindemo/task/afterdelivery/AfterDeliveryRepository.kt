package com.example.kotlindemo.task.afterdelivery

import com.example.kotlindemo.study.mvi.core.MviBaseRepository

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/06/06
 */
class AfterDeliveryRepository : MviBaseRepository<AfterDeliveryEvent>() {


    fun requestJobList(): List<Int> {
        // TODO 测试代码
        val testList = mutableListOf<Int>()
        repeat(2) {
            testList.add(it)
        }
        return testList
    }

}