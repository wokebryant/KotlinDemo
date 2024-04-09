package com.example.kotlindemo.task.resume

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.databinding.LayoutResumeRecommendTopBarBinding
import com.example.kotlindemo.utils.binding
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 简历点后推AppBar
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendTopBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutResumeRecommendTopBarBinding by binding()

    private var callback: IResumeRecommendCallback? = null

    init {
        binding.ivBack.onClick {
            currentActivity()?.finish()
        }
        binding.ivReport.onClick {
            callback?.onReportClick()
        }
        binding.ivShare.onClick {
            callback?.onShareClick()
        }
    }

    fun setClickCallback(callback: IResumeRecommendCallback) {
        this.callback = callback
    }

}