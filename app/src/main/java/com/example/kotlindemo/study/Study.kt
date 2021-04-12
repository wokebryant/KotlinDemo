package com.example.kotlindemo.study

interface Study {
    fun readBooks()
    fun doHomework(): String {
        println("do homework default implementation")
        return "string"
    }
}