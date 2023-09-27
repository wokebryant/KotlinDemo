package com.example.kotlindemo.task.search

import com.example.kotlindemo.activity.linkage.KeepProtocol

/**
 * @Description 职位搜索桥页面数据Bean
     * 历史/订阅数据查询: https://wiki.zhaopin.com/pages/viewpage.action?pageId=152115269
     * 删除订阅: https://wiki.zhaopin.com/pages/viewpage.action?pageId=145424505
     * 添加订阅: https://wiki.zhaopin.com/pages/viewpage.action?pageId=152115263
 * @Author LuoJia
 * @Date 2023/9/27
 */
data class PositionSearchBridgeBean (
    val size: Int,
    val subscribeInfo: List<PositionSearchListItem>
) : KeepProtocol

data class PositionSearchListItem(
    val isSubscribe: Boolean,
    val id: Long,
    val userId: Long,
    val title: String,
    val subTitle: String,
    val keyword: String,
    val workCity: String,
    val workCityName: String,
    val salary: String,
    val salaryName: String,
    val education: String,
    val educationName: String,
    val workExperience: String,
    val workExperienceName: String,
    val positionType: String,
    val positionTypeName: String,
    val companyType: String,
    val companyTypeName: String,
    val companyScale: String,
    val companyScaleName: String,
    val industry: String,
    val industryName: String
) : KeepProtocol