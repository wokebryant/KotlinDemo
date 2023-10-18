package com.example.kotlindemo.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.kotlindemo.base.BaseActivity
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

/**
 *  打开activity执行转场动画
 */
open class TransformActivity : BaseActivity(){

    companion object {
        private const val TAG = "TransformActivity"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        doContainerTransform()
        super.onCreate(savedInstanceState)
    }

    /**
     *  执行转场动画
     */
    private fun doContainerTransform() {
        if (shouldSetUpContainerTransform()) {
            window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            val transitionName = intent.getStringExtra(EXTRA_TRANSITION_NAME)
            findViewById<View>(android.R.id.content).transitionName = transitionName
            setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            window.sharedElementEnterTransition = MaterialContainerTransform().apply {
                addTarget(android.R.id.content)
                duration = 400L

            }
            window.sharedElementReturnTransition = MaterialContainerTransform().apply {
                addTarget(android.R.id.content)
                duration = 500L
            }
        }
    }

    private fun shouldSetUpContainerTransform(): Boolean {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
    }


}