package com.example.kotlindemo.utils

import android.util.Log
import com.example.kotlindemo.application.MyApplication

fun dip2px(dpValue: Float): Int {
    val scale: Float = MyApplication.context.resources.displayMetrics.density
    Log.i("scale= ", scale.toString())
    return (dpValue * scale + 0.5f).toInt()
}