package com.example.kotlindemo.task.ai.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.databinding.LayoutAiRecommendTopBarBinding
import com.example.kotlindemo.task.ai.IAiRecommendCallback
import com.example.kotlindemo.utils.binding
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/11
 */
class AiRecommendTopBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutAiRecommendTopBarBinding by binding()

    private var callback: IAiRecommendCallback? = null

    init {
        binding.ivBack.onClick {
            currentActivity()?.finish()
        }
        binding.ivMore.onClick {
            callback?.onMoreClick(it)
        }
    }

    fun setClickCallback(callback: IAiRecommendCallback) {
        this.callback = callback
    }

}