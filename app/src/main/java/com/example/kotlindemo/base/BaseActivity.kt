package com.example.kotlindemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.utils.StatusBarUtil

open class BaseActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this, false, false)
    }

}