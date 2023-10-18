package com.example.kotlindemo.study.motion

import android.os.Bundle
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityMotionMultiTranslationBinding
import com.example.kotlindemo.utils.binding

/**
 *  MotionLayout示例：多个控件平移
 */
class MotionMultiTranslationActivity : BaseActivity() {

    private val binding: ActivityMotionMultiTranslationBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {}
    }

}