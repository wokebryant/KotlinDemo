package com.example.kotlindemo.study

import kotlin.collections.HashMap

fun main1() {
    val a = 10
    var b = 34
    var c = largeNumber(a, b)
    var d = a + b
    print(c)
    print("//n")
    print(d)
}

fun methodName(param1: Int, param2: Int) : Int{
    return 0
}


/**
 *  if语法
 */

fun largeNumber(param1: Int, param2: Int) : Int {
//    return max(param1, param2)

//    var value = if (param1 > param1) {
//        param1
//    } else{
//        param2
//    }
//    return value

    return if (param1 > param2) {
        param1
    } else {
        param2
    }

}

fun lagerValue (num: Int, num2: Int) = if (num > num2) num else num2


/**
 *  when语法
 */
fun getScore(name: String) = when(name) {
    "Tom" -> 86
    "Jim" -> 87
    "Jack" -> 88
    "Lily" -> 89
    else -> 0
}

fun getScoreNew(name : String) = when {
    name.startsWith("Tom") -> 86
    name == "Jim" -> 87
    name == "Jack" -> 88
    name == "Lily" -> 89
    else -> 0
}

fun checkNum(value: Number) {
    when (value) {
        is Int -> print("isInt")
        is Long -> print("isString")
        is Double -> print("isDouble")
        else -> print("isFloat")
    }
}


/**
 *  for循环语法
 *  0..10  闭区间
 *  0 until 10 开区间
 */


fun main() {
    for (i in 0..10) {
        print(i)
    }

    for (i in 0 until 10 step 2) {
        print(i)
    }

    for (i in 10 downTo 1 step 2) {
        print(i)
    }

    LambdaFun()

    if (content != null) {
        printUpperCase()
    }

    printNum(content = "hello_kotlin")

}


/**
 *  集合
 *  listOf  不可变集合，不能add
 *
 */
fun fruitList() {
    var list = listOf("apple", "juice", "banana", "orange", "pear")
    val apple = list[0]
    // 找到list中最长的字段
    val maxLengthFruit = list.maxOf { it.length }
    for (fruit in list) {
        println(fruit)
    }

    val mutableList = mutableListOf("yellow", "pink", "red", "blue", "green")
    mutableList.add("gray")
    for (color in mutableList) {
        println(color)
    }
}

fun friutMap() {
    val map = HashMap<String, Int>()
    map["key"] = 0
    map["Apple"] = 1
    map["orange"] = 2
    map["banana"] = 3
    val orangeNum = map["orange"]

    val map1 = mapOf("key" to 0, "Apple" to 1, "orange" to 2, "banana" to 3)
    for ((fruit, numbel) in map1) {
        println("fruit is " + fruit + " , number is " + numbel)
    }
}

/**
 *  集合的Lambda表达式
 */
fun LambdaFun( ){
    val list = listOf("apple", "juice", "banana", "orange", "pear")
    val newList = list.filter { it.length <= 5 }
                    .map { it.toUpperCase() }
    // 是否存在一个数长度小于等于5
    var anyResult = list.any{it.length <= 5}
    // 是否所有数的长度小于等于5
    var allResult = list.all { it.length <= 5 }
    for (f in newList) println(f)
}

/**
 *  Java函数式API使用，Kotlin调用java接口（接口有且只有一个待实现方法），可省略接口名以及方法名
 */
fun creatRunable() {
    Thread(object : Runnable {
        override fun run() {
            println("create runnable from kotlin")
        }
    }).start()

    Thread(Runnable {
        print("create runnable from kotlin")
    }).start()

    Thread({ println("create runnable from kotlin")
    }).start()

}


/**
 *  空指针
 *  object?.fun  如果对象不为空，正常调用方法，为空什么都不做
 *  objectA?:objectB    如果左边不为空(null)返回左边，否则返回右边
 *  object!!.fun()      一定保证object不为空，可以避免编译判空问题
 *  object.let{object -> //业务逻辑} 可以处理全局变量空指针，因为let函数内部加锁同步
 */

fun doSomething2() {
    var a = ""
    var b = ""
//    val c = if (a != null) a else b
    val c = a ?: b
}

fun getTextLength(text: String?) = if (text != null) text.length else 0

fun getTextLengthNew(text: String?) = text?.length ?: 0

var content: String? = null

fun printUpperCase() {
    val upperCase = content?.toUpperCase()
    println(upperCase)
}


/**
 *  kotlin内嵌表达式
 *  ${value}
 */
fun printNum(num: Int = 100, content: String) {
    println("num= $num, content= $content, value= $num%")
}









