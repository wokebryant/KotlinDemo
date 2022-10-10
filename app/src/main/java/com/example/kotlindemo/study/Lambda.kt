package com.example.kotlindemo.study

/**
 * @Description 学习Lambda表达式
 * https://juejin.cn/post/7145275953814437925
 * @Author LuoJia
 * @Date 2022-09-29
 */
fun main() {
    if (true) println("A")
    if (true) { println("B") }
    if (true) {
        { println("C") }()
    }

    { println("D") } ()

    printE()

    when {
        true -> { println("F") }
    }
}

fun printE() = { println("E") } ()

