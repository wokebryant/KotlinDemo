package com.example.kotlindemo.study.share

import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/03/18
 */

fun main() {
//    getOrElse()
//    indexOfFirst()
//    groupBy()
//    take()
//    drop()
//    slice()
//    any()
//    joinToString()
//    sequence()
    lazySequence()
}

fun getOrElse() {
    val list = mutableListOf(1, 2, 3, 4, 5)
    val value = list.getOrElse(5) {
        111
    }
    println(value)
}

fun indexOfFirst() {
    val list = mutableListOf(1, 2, 2, 3, 5, 6)
    val index = list.indexOfFirst { it == 2 }
    println(index)
}

fun groupBy() {
    val list = mutableListOf("Apple", "Banana", "Apricot", "Blueberry")
    val groupMap = list.groupBy {
        if (it.startsWith("A")) {
            "fruit_a"
        } else if (it.startsWith("B")) {
            "fruit_b"
        } else {
            ""
        }
    }
    val fruitA = groupMap.getOrElse("fruit_a") {
        emptyList()
    }
    println(fruitA)
    println(groupMap)
}

fun take() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    val takeFirst = list.take(2)
    val takeLast = list.takeLast(2)
    println(takeFirst)
    println(takeLast)
}

fun drop() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    val dropFirst = list.drop(2)
    val dropLast = list.dropLast(1)
    println(dropFirst)
    println(dropLast)
}

fun slice() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    val sliceList = list.slice(listOf(0, 2, 4))
    val sliceRangeList = list.slice(IntRange(0, 3))
    println(sliceList)
    println(sliceRangeList)
}

fun any() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    val any1 = list.any { it == 5 }
    val any2 = list.any { it == 7 }
    println(any1)
    println(any2)
}

fun joinToString() {
    val list = listOf(1, 2, 3, 4, 5, 6)
    val result = list.joinToString(",")
    println(result)
}

fun sequence() {

    val numbersSequence = sequenceOf("four", "three", "two", "one")
    println(numbersSequence.toList())

    // list转sequence
    val numbers = listOf("one", "two", "three", "four")
    val numbersSequence2 = numbers.asSequence()

    // 创建无限序列
    val infiniteNumbers = generateSequence(1) { it + 2 }
    println(infiniteNumbers.take(5).toList())
//    println(infiniteNumbers.count())

    // 创建有限序列
    val oddNumbers = generateSequence(1) { if (it == 11) null else it + 2 }
    println(oddNumbers.take(5).toList())
    println(oddNumbers.count())
}

fun lazySequence() {
    val list = (1..10).toList()
    val newList = list.asSequence()
        .filter {
            println("filter $it")
            it % 2 == 0
        }.map {
            println("map $it")
            it * it
        }
//    println(newList.toList())
}