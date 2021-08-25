package com.example.kotlindemo.study.motion

import android.os.Bundle
import com.example.kotlindemo.activity.BaseActivity
import com.example.kotlindemo.databinding.ActivityMotionKeyCycleBinding
import com.example.kotlindemo.utils.binding

/**
 *  MotionLayout示例：KeyFrame循环
 */
class MotionKeyCycleActivity : BaseActivity() {

    private val binding: ActivityMotionKeyCycleBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {}
    }
}