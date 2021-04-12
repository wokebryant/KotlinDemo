package com.example.kotlindemo.study

import android.app.Activity
import android.content.Context
import java.io.*
import java.lang.StringBuilder

/**
 *  数据存储
 */

/**
 * 写数据
 */
fun save(inputText: String) {
    try {
        val context = Activity()
        val output = context.openFileOutput("data", Context.MODE_PRIVATE)
        val writer = BufferedWriter(OutputStreamWriter(output))
        writer.use {
            it.write(inputText)
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * 读数据
 */
fun load(): String {
    val content = StringBuilder()
    try {
        val context = Activity()
        val input = context.openFileInput("data")
        val reader = BufferedReader(InputStreamReader(input))
        reader.use {
            reader.forEachLine {
                content.append(it)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return content.toString()
}

