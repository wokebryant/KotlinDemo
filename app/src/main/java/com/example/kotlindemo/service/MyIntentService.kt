package com.example.kotlindemo.service

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log

/**
 *  自动开启线程，执行完毕后自动停止Service
 */

class MyIntentService : IntentService("MyIntentService"){

    override fun onCreate() {
        super.onCreate()
        Log.d("MyIntentService", "onCreate Thread is is ${Thread.currentThread().name}")
    }

    //这个方法在子线程运行，处理耗时逻辑
    override fun onHandleIntent(intent: Intent?) {
        Log.d("MyIntentService", "Handle Thread is is ${Thread.currentThread().name}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService", "onDestroy Thread is is ${Thread.currentThread().name}")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

}



