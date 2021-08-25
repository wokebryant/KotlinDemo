package com.example.kotlindemo.study.motion

import android.os.Bundle
import com.example.kotlindemo.activity.BaseActivity
import com.example.kotlindemo.databinding.ActivityMotionKeyAttributeBinding
import com.example.kotlindemo.utils.binding

/**
 *  MotionLayout示例：KeyFrame属性
 */
class MotionKeyAttributeActivity : BaseActivity() {

    private val binding: ActivityMotionKeyAttributeBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding){}
    }


}