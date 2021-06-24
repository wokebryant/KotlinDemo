package com.example.kotlindemo.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.utils.StatusBarUtil

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this, false, false)
    }
}