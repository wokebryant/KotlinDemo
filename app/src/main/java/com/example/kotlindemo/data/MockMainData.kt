package com.example.kotlindemo.data

import com.example.kotlindemo.R
import com.example.kotlindemo.activity.MaterialDesignActivity
import com.example.kotlindemo.model.MainItemState
import com.example.kotlindemo.model.MainItemType

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/17
 */

val mainItemList = mutableListOf(
    MainItemState(
        title = "Material示例",
        type = MainItemType.Material
    ),
    MainItemState(
        title = "MotionLayout示例",
        type = MainItemType.Motion
    ),
    MainItemState(
        title = "ConstrainLayout示例",
        type = MainItemType.Constraint
    ),
    MainItemState(
        title = "ViewPager2示例",
        type = MainItemType.ViewPager2
    ),
    MainItemState(
        title = "Mark测试",
        type = MainItemType.Mark
    ),
    MainItemState(
        title = "区块链钱包",
        type = MainItemType.BlockChain
    ),
    MainItemState(
        title = "Flow",
        type = MainItemType.Flow
    ),
    MainItemState(
        title = "MVI",
        type = MainItemType.MVI
    ),
    MainItemState(
        title = "智联需求",
        type = MainItemType.ZLTask,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P1
    ),
    MainItemState(
        title = "AppBar",
        type = MainItemType.AppBar,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
    MainItemState(
        title = "二级联动",
        type = MainItemType.Linkage,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
    MainItemState(
        title = "小微企业",
        type = MainItemType.MircoCompany,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
    MainItemState(
        title = "搜索桥",
        type = MainItemType.SearchBridge,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
    MainItemState(
        title = "搜索结果页",
        type = MainItemType.SearchResult,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
    MainItemState(
        title = "职位详情页",
        type = MainItemType.JobDetail,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
    MainItemState(
        title = "职位排行榜",
        type = MainItemType.PositionRank,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
    MainItemState(
        title = "收藏页Compose",
        type = MainItemType.CollectCompose,
        textColor = R.color.C_W1,
        bgColor = R.color.C_P3
    ),
)