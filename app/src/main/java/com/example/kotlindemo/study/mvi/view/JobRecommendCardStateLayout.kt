package com.example.kotlindemo.study.mvi.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.databinding.LayoutRecommendJobStateBinding
import com.example.kotlindemo.utils.binding
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/6
 */
class JobRecommendCardStateLayout  @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutRecommendJobStateBinding by binding()

    var onRetryListener: (() -> Unit)? = null

    init {
        binding.tvRetry.onClick {
            onRetryListener?.invoke()
        }
    }

    fun showSkeleton() {
        binding.llSkeleton.setVisible()
    }

    fun hideSkeleton() {
        binding.llSkeleton.setGone()
    }

    fun showError() {
        binding.llEmpty.setVisible()
    }

    fun hideError() {
        binding.llEmpty.setGone()
    }
}