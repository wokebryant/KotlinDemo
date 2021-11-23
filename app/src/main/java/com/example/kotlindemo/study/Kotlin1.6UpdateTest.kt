package com.example.kotlindemo.study

/**
 * @Author: LuoJia
 * @Date: 2021/11/23
 * @Description: Kotlin1.6版本新增功能测试
 */
fun getSuspending(suspending: suspend () -> Unit) {}

fun suspending() {}

fun test(regular: () -> Unit) {
    getSuspending {  }
    getSuspending (::suspending)
    getSuspending (regular)

}

