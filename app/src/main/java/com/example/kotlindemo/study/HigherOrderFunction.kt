package com.example.kotlindemo.study

/**
 *  kotlin高阶函数
 *  接受一个函数类型，或返回一个函数类型
 */

fun numAndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Unit) {
    val result = operation(num1, num2)
//    return result
}

fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}

fun StringBuilder.build(block: StringBuilder.() -> Unit): StringBuilder {
    block()
    return this
}

inline fun runRunnable(crossinline block: () -> Unit) {
    val runnable = Runnable{
        block()
    }
}

fun main() {
    val num1 = 100
    val num2 = 80
    //Normal
//    val result1 = numAndNum2(num1, num2, ::plus)
//    val result2 = numAndNum2(num1, num2, ::minus)
    //Great
    val result1 = numAndNum2(num1, num2) {n1, n2 ->
        n1 - n2

    }
    val result2 = numAndNum2(num1, num2) {n1, n2->
        n1 + n2
    }
    println("result1 is $result1")
    print("result2 is $result2")
}