package com.example.kotlindemo.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.Window
import androidx.fragment.app.FragmentActivity
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

/**
 *  app工具类
 */
object AppUtil {

    /**
     *  通过实例化目标Activity泛型，必须是内联函数
     */
    inline fun <reified T> startActivity(
        context: Context,
        shareElement: View? = null,
        transformName: String? = null,
        block: Intent.() -> Unit
    ) {
        val intent = Intent(context, T::class.java)
        intent.block()

        if (shareElement != null && !transformName.isNullOrEmpty()) {
            val activity = context as FragmentActivity
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, shareElement, transformName)
            context.startActivity(intent, options.toBundle())
        } else {
            context.startActivity(intent)
        }
    }
}