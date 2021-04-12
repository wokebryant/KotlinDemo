package com.example.kotlindemo.study

/**
 *  单例类，只需要把class替换成object即可
 */

object Singleton {

    @JvmStatic
    fun SingletonTest() {
        print("singletonTest is called")
    }


}

fun main() {
    Singleton.SingletonTest()
}