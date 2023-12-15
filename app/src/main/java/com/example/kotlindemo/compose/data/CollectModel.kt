package com.example.kotlindemo.compose.data

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/12/15
 */
data class CollectJobState(
    val list: List<CollectJobItem>
)

data class CollectJobItem(
    val jobName: String,
    val salary: String,
    val companyName: String,
    val companyStrength: String,
    val companySize: String,
    val skillList: List<String>,
    val hrAvatar: String,
    val hrOnline: Boolean,
    val hrName: String,
    val publishDate: String

)