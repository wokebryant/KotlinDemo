package com.example.kotlindemo.utils

import java.lang.Exception
import kotlin.concurrent.thread

object HttpUtil {

    fun sendHttpRequest(address: String, listener: HttpCallbackListener) {
        thread {

        }

    }

    interface HttpCallbackListener {
        fun onFinish(response: String)

        fun onError(error: Exception)
    }
}