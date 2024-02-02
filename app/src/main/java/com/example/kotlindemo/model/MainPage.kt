package com.example.kotlindemo.model

import androidx.annotation.ColorRes
import com.example.kotlindemo.R
import com.zhaopin.selectwidget.bean.PositionNpsBean.MainModel


/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/17
 */
data class MainItemState(
    val title: String,
    val type:MainItemType,
    @ColorRes val textColor: Int = R.color.C_B1,
    @ColorRes val bgColor: Int = R.color.C_W1
)

sealed class MainItemType {
    object Material: MainItemType()
    object Motion: MainItemType()
    object Constraint: MainItemType()
    object Paging: MainItemType()
    object ViewPager2: MainItemType()
    object Mark: MainItemType()
    object BlockChain: MainItemType()
    object MVI: MainItemType()
    object Flow: MainItemType()
    object ZLTask: MainItemType()
    object AppBar: MainItemType()
    object Linkage: MainItemType()
    object MircoCompany: MainItemType()
    object SearchBridge: MainItemType()
    object SearchResult: MainItemType()
    object PositionRank: MainItemType()
    object JobDetail: MainItemType()
    object CollectCompose: MainItemType()
    object AiRecommend: MainItemType()
}