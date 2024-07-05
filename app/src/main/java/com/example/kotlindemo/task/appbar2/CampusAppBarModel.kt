package com.example.kotlindemo.task.appbar2

/**
 * @Description 校园AppBarModel
 * @Author LuoJia
 * @Date 2024/06/26
 */

data class FamousEnterpriseState(
    val list: List<Item>
) {
    data class Item(
        val logo: String,
        val companyName: String,
        val companyAddress: String,
        val companySize: String,
        val companyNature: String,
        val labelList: List<String>
    )
}

val testFamousEnterpriseData = FamousEnterpriseState(
    list = listOf(
        FamousEnterpriseState.Item(
            logo = "",
            companyName = "北京卓望数码技术有限公司",
            companyAddress = "上海",
            companySize = "10000人以上",
            companyNature = "国企",
            labelList = listOf(
                "互联网100强", "优选雇主"
            )
        ),
        FamousEnterpriseState.Item(
            logo = "",
            companyName = "智联招聘",
            companyAddress = "北京",
            companySize = "99999人以上",
            companyNature = "国企",
            labelList = listOf(
                "世界500强", "优选雇主"
            )
        ),
        FamousEnterpriseState.Item(
            logo = "",
            companyName = "阿里巴巴",
            companyAddress = "北京",
            companySize = "99999人以上",
            companyNature = "民营",
            labelList = listOf(
                "世界500强", "优选雇主"
            )
        ),
        FamousEnterpriseState.Item(
            logo = "",
            companyName = "北京卓望数码技术有限公司",
            companyAddress = "上海",
            companySize = "10000人以上",
            companyNature = "国企",
            labelList = listOf(
                "互联网100强", "优选雇主"
            )
        ),

    )
)