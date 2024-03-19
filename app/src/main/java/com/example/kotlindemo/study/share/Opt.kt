package com.example.kotlindemo.study.share

import org.json.JSONArray


/**
 * @Description
 * @Author LuoJia
 * @Date 2024/03/18
 */
fun main() {
    val count = 2
    (1..10).forEach { value ->
        calculate(value) { result ->
            val average = result / count
            println(average)
        }
    }
}


fun calculate(x: Int, lambda: (result: Int) -> Unit) {
    lambda(x + 10)
}


//fun main() {
//    val count = 2
//    val lambda: (result: Int) -> Unit = { result ->
//        val average = result / count
//        println(average)
//    }
//    (1..10).forEach { value ->
//        calculate(value, lambda)
//    }
//}

