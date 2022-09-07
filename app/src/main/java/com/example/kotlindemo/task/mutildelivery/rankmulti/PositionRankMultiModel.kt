package com.example.kotlindemo.task.mutildelivery.rankmulti

/**
 * @Description 职位榜单批投卡片数据模型
 * @Author LuoJia
 * @Date 2022/8/30
 */
data class PositionRankMultiModel (
    val job: String,
    val salary: String,
    val company: String,
    val workYear: String,
    val education: String,
    val skill:String,
    var checkState: CheckState
)

sealed class CheckState {
    object Check : CheckState()
    object UnCheck : CheckState()
    object Gray : CheckState()
}