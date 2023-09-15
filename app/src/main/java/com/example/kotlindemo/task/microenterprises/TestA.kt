package com.example.kotlindemo.task.microenterprises

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/9/1
 */
fun main() {
    val a = mutableListOf(1, 2, 3, 4 ,5)

    val position = 5
    if (position > a.size) return
    a.add(position, 8)

    println(a)

}