package com.example.kotlindemo.compose.paging

import com.example.kotlindemo.compose.paging.datasource.Cleanable

interface ComposeItem : ComposeItemType, Cleanable, ComposeItemKey

interface ComposeItemKey {
    val key: String
        get() = ""
}

//interface ComposeItem{
//    val key: String
//} : ComposeItemType, Cleanable