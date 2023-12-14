package com.example.kotlindemo.task.jobdetail

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.databinding.LayoutJobDetailTopBarBinding
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.common.extension.isGone
import com.zhaopin.social.common.extension.isVisible
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/24
 */
class JobDetailTopBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutJobDetailTopBarBinding by binding()

    init {
        setView()
    }

    private fun setView() {
        with(binding) {
            ivBack.onClick {
                currentActivity()?.finish()
            }
            ivShare.onJClick {

            }
            ivCollect.onJClick {

            }
            ivReport.onClick {

            }
        }
    }

    /**
     * 展示标题
     */
    fun showTitle(title: String) {
        if (binding.tvTitle.isVisible()) {
            return
        }
        binding.tvTitle.text = title
        binding.tvTitle.setVisible()
    }

    /**
     * 隐藏标题
     */
    fun hideTitle() {
        if (binding.tvTitle.isGone()) {
            return
        }
        binding.tvTitle.setGone()
    }
}