package com.example.kotlindemo.study.mvi

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/1
 */
fun main() {
    val list1 = listOf(Keyword("1", 1), Keyword("2", 2), Keyword("3", 3))
    val list2 = listOf(Keyword("1", 1), Keyword("3", 3), Keyword("2", 2))

    if (list1.toSet() == list2.toSet()) {
        println("两个列表的值相同")
    } else {
        println("两个列表的值不同")
    }

}

data class Keyword(
    val a: String,
    val b: Int
)