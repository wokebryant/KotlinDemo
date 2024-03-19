package com.example.kotlindemo.study.share

/**
 * @Description Kotlin集合和数组分享
 * @Author LuoJia
 * @Date 2024/03/15
 */

fun main() {
//    createDefaultArray()
//    createArrayByArrayOf()
//    traverseArray()
    sliceArray()
}

/**
 * 1. 默认创建，需要传入数组长度和初始化函数
 */
fun createDefaultArray() {
    val array = Array(16) { index ->
        "No.$index"
    }
    println(array.size)
    for (str in array) {
        println(str)
    }
}

/**
 * 2. 通过kotlin函数创建
 */
fun createArrayByArrayOf() {
    val array = arrayOf("A", "B", "C", "D", "E")

    // int
    val intArray = arrayOf(1, 2, 3, 4, 5)
    val intArrayGood = intArrayOf(1,  2, 3, 4, 5)

    // char
    val charArray = arrayOf('a', 'b', 'c')
    val charArrayGood = charArrayOf('a', 'b', 'c')

    // double
    val doubleArray = arrayOf(1.11, 2.22, 3.33)
    val doubleArrayGood = doubleArrayOf(1.11, 2.22, 3.33)

    // short
    val shortArray = arrayOf(1, 2, 3, 4, 5)
    val shortArrayGood = shortArrayOf(1, 2, 3, 4, 5)

    // float
    val floatArray = arrayOf(1.1f, 1.2f, 1.3f)
    val floatArrayGood = floatArrayOf(1.1f, 1.2f, 1.3f)

    // long
    val longArray = arrayOf(11, 22, 33)
    val longArrayGood = longArrayOf(11, 22, 33)
}

/**
 * 3. 数组遍历
 */
fun traverseArray() {
    val array = arrayOf(0, 1, 2, 3, 4, 5, 6)

    //forEach
    array.forEach {
        println(it)
    }

    //区间表达式
    for (value in 0..6) {
        println(value)
    }

    //indices
    for (value in array.indices) {
        println(value)
    }
//
//    //withIndex
    for ((index, value) in array.withIndex()) {
        println(index)
        println(value)
    }
}

/**
 * 4. 数组切片
 */
fun sliceArray() {
    val array = arrayOf(0, 1, 2, 3, 4, 5, 6)
    val sliceArray = array.slice(2 until  4)
    println(sliceArray)
}

/**
 * 5. 数组反转
 */
fun reverseArray() {
    val array = arrayOf(0, 1, 2, 3, 4, 5, 6)
    val reverseArray = array.reverse()
    println(reverseArray)
}

