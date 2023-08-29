package com.example.kotlindemo.widget

import android.content.Context
import android.util.AttributeSet
import com.airbnb.lottie.LottieAnimationView
import com.zhaopin.social.module_common_util.log.LogKitty

/**
 * 设置setFailureListener的lottie,避免使用系统LottieAnimationView时,加载资源报错后抛出异常
 */
class ZLLottieAnimationView : LottieAnimationView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setFailureListener {
            LogKitty.e("ZLLottieAnimationView", it.message)
        }
    }
}