package com.example.kotlindemo.activity.linkage

import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiIntent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.example.kotlindemo.activity.linkage.origin.TagState

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/4
 */
data class LinkageUiState(
    val selectedList: Pair<List<LinkageChildItem>, TagState> = Pair(emptyList(), TagState.Add),
    val linkageList: MutableList<LinkageGroupItem> = mutableListOf()
) : IUiState

sealed class LinkageIntent : IUiIntent {
    data class RequestLinkageData(val params: MutableMap<String, Any>) : LinkageIntent()
    data class LinkageTagClick(val items: Set<LinkageChildItem> = emptySet(), val state: TagState) : LinkageIntent()
    data class RemoveSelectedTag(val items: Set<LinkageChildItem> = emptySet()) : LinkageIntent()
    data class IndustrySelectedItemsChange(val items: MutableList<LinkageChildItem>) : LinkageIntent()
    object ClearAll : LinkageIntent()
    object SubmitFilterData : LinkageIntent()
}

sealed class LinkageEvent : IUiEvent {
    data class OnLinkageItemRemove(val data: Pair<Int, Int>): LinkageEvent()
    data class OnLinkageItemClear(val size: Int): LinkageEvent()
    data class OnLinkageItemAdd(val items: Pair<Int, List<Int>> = Pair(-1, emptyList())): LinkageEvent()
}

