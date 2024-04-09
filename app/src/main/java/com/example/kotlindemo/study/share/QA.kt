package com.example.kotlindemo.study.share

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/03/18
 */

fun printE() = { println("E") }

fun main() {
    if (true) println("A")
    if (true) { println("B") }
    if (true) {
        { println("C") }
    }

    { println("D") }

    printE()

    when {
        true -> { println("F") }
    }
}





