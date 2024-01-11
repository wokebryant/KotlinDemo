package com.example.kotlindemo.study.mvi.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutRecommendCardVerticalBinding
import com.example.kotlindemo.databinding.LayoutRecommendCardVerticalItemBinding
import com.example.kotlindemo.task.jobtag.JobItemState
import com.example.kotlindemo.utils.binding
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/4
 */
class JobRecommendCardVertical @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutRecommendCardVerticalBinding by binding()

    /** 标签适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(VerticalDelegate()) }
    }

    init {
        binding.rvCard.adapter = listAdapter
    }

    fun setData(data: List<JobItemState>) {
        listAdapter.setList(data)
    }

    inner class VerticalDelegate : BindingViewDelegate<JobItemState, LayoutRecommendCardVerticalItemBinding>() {

        override fun onBindViewHolder(
            binding: LayoutRecommendCardVerticalItemBinding,
            item: JobItemState,
            position: Int
        ) {
            with(binding) {
                tvJobName.text = item.jobName
                tvSalary.text = item.salary
                tvCompanyName.text = item.companyName
                tvCompanyStrength.text = item.companyStrength
                tvCompanySize.text = item.companySize
                tvCompanyName.run {
                    post {
                        val paintWidth = paint.measureText(item.companyName)
                        if (width > paintWidth) {
                            updateLayoutParams<LinearLayout.LayoutParams> {
                                weight = 0f
                                width = paintWidth.toInt()
                            }
                        }
                    }
                }
                tvRecommendReason.text = item.recommendReason
                tvAddress.text = item.address
                tvRecommendReasonTag.run {
                    background = Bovb.with().radius(4.dp.toFloat()).color(getColor(R.color.C_P7)).build()
                }
                vDivide.visibility = if (position == 0) View.VISIBLE else View.INVISIBLE
                vDivide.updateLayoutParams<MarginLayoutParams> {
                    topMargin = if (position == 0) 20.dp else 26.dp
                }
            }
        }
    }

}