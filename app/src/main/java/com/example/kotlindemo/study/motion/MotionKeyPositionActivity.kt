package com.example.kotlindemo.study.motion

import android.os.Bundle
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityMotionKeyPostionBinding
import com.example.kotlindemo.utils.binding

/**
 *  MotionLayout示例：KeyFrame关键点
 */
class MotionKeyPositionActivity : BaseActivity() {

    private val binding: ActivityMotionKeyPostionBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {}
    }

}