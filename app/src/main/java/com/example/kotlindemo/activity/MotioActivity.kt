package com.example.kotlindemo.activity

import android.os.Bundle
import com.example.kotlindemo.R

/**
 *  运动布局
 */
class MotionActivity : TransformActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_motion)
        setContentView(R.layout.fold_title_view)
    }


}