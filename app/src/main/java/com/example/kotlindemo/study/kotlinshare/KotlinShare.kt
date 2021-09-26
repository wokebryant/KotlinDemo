package com.example.kotlindemo.study.kotlinshare

class KotlinShare {

    /**
     *  Init
     */
    private val parameter1 = "im parameter1"

    init {
        println(parameter1)
//        print(parameter2)

        arrayTraverse()
    }

    private val parameter2 = "im parameter2"


    /**
     *  lambada表达式
     */
    fun requestHttp(callback: (code: Int, data: String) -> Unit) {
        callback(200, "success")
    }

    fun onCall() {
        requestHttp { code, data ->
            println("code: $code")
            println("data: $data")
        }
    }

    /**
     *  lateinit var
     */
    lateinit var name: String

    fun print() {
        if (this::name.isInitialized) {
            println(name)
        }
    }

    /**
     *  lazy
     *
     */
    //只允许由单个线程来完成初始化，且初始化操作包含有双重锁检查，从而使得所有线程都得到相同的值
    //默认
    val defaultLazyValue by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        "im default lazy value"
    }

    //允许多个线程同时执行初始化操作，不进行任何线程同步，导致不同线程可能会得到不同的初始化值，因此不应该用于多线程环境
    val noModeLazyValue by lazy(LazyThreadSafetyMode.NONE) {
        "im none mode lazy value"
    }

    //允许多个线程同时执行初始化操作，不进行任何线程同步，导致不同线程可能会得到不同的初始化值，因此不应该用于多线程环境
    val publicationModeLazyValue by lazy(LazyThreadSafetyMode.PUBLICATION) {
        "im publication mode lazy value"
    }

    /**
     *  数组遍历
     */
    private fun arrayTraverse() {
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

        //withIndex
        for ((index, value) in array.withIndex()) {
            println(index)
            println(value)
        }

    }











}