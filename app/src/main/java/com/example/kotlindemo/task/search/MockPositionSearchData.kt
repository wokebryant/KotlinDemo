package com.example.kotlindemo.task.search

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/8
 */

val mockRecommendJobList = mutableListOf(
    SearchRecommendJobItemState(
        code = "",
        name = "项目财务经理"
    ),
    SearchRecommendJobItemState(
        code = "",
        name = "财税咨询经理"
    ),
    SearchRecommendJobItemState(
        code = "",
        name = "财务BP"
    ),
    SearchRecommendJobItemState(
        code = "",
        name = "财务分析师"
    ),
    SearchRecommendJobItemState(
        code = "",
        name = "Finance Manager"
    )
)

val mockHistoryItem = PositionSearchListItem(
    isSubscribe = false,
    id = 111,
    userId = 222,
    title = "财务管理咨询顾问",
    subTitle = "",
    keyword = "333",
    workCity = "444",
    workCityName = "北京",
    salary = "555",
    salaryName = "1.6-2.2万",
    education = "666",
    educationName = "本科",
    workExperience = "777",
    workExperienceName = "1-3年",
    positionType = "888",
    positionTypeName = "全职",
    companyType = "999",
    companyTypeName = "国企",
    companyScale = "1111",
    companyScaleName = "9999人",
    industry = "2222",
    industryName = "教育互联网"
)