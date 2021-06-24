package com.example.kotlindemo.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.kotlindemo.R
import kotlin.concurrent.thread

class ThirdActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        getSharedPreferences("data", Context.MODE_PRIVATE).open {
            putString("name", "Tom")
            putInt("age", 28)
            putBoolean("married", false)
        }

    }

    /**
     *  因为open方法是SharedPreferences的拓展函数，因此大括号内拥有SharedPreferences的上下文
     *  因为函数类型参数属于SharedPreferences.Editor类，因此调用open方法的lambda表达式内拥有SharedPreferences.Editor上下文
     *  函数类型参数需要被调用，相当于执行lambda方法,又因为类型是SharedPreferences.Editor.()。所以需要被SharedPreferences.Editor调用
     */
    fun SharedPreferences.open(block: SharedPreferences.Editor.()-> Unit) {
        val editor = edit()
        editor.block()
        editor.apply()
    }

    val id = 1

    val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when(msg.what) {
                id -> ""
            }
        }
    }

    fun testhandler() {

        thread {
            val msg = Message()
            msg.what = id
            handler.sendMessage(msg)
        }

    }
}


