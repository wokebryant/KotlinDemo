package com.example.kotlindemo.library

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/6/30
 */
fun main() {
    val list = mutableListOf(1, 2, 3, 4, 5, 6)
    list.add(4, 7)
    println(list)
}