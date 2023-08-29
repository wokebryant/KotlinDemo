package com.example.kotlindemo.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.zhaopin.social.appbase.util.initActivityUtil

class MyApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        initActivityUtil()
    }
}