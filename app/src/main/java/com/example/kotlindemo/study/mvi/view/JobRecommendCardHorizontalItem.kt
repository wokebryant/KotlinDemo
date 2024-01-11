package com.example.kotlindemo.study.mvi.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutRecommendCardHorizontalItemBinding
import com.example.kotlindemo.task.jobtag.JobItemState
import com.example.kotlindemo.utils.binding
import com.zhaopin.common.widget.flowLayout.FlowLayout
import com.zhaopin.common.widget.flowLayout.TagAdapter
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/5
 */
class JobRecommendCardHorizontalItem @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutRecommendCardHorizontalItemBinding by binding()

    fun setData(data: JobItemState?) {
        if (data == null) {
            binding.root.setGone()
            return
        }
        with(binding) {
            tvJobName.text = data.jobName
            tvSalary.text = data.salary
            tvAddress.text = data.address
            tvCompanyName.text = data.companyName
            if (data.recommendReason.isNotEmpty()) {
                layoutFlow.setGone()
                tvRecommendReason.setVisible()
                tvRecommendReason.run {
                    text = data.recommendReason
                    background = Bovb.with().color(getColor(R.color.C_P8)).radius(6.dp.toFloat()).build()
                }
            } else {
                layoutFlow.setVisible()
                tvRecommendReason.setGone()
                val tagAdapter = object : TagAdapter<JobItemState.TagItem>(data.skillTagList) {
                    override fun getView(
                        parent: FlowLayout?,
                        position: Int,
                        t: JobItemState.TagItem?
                    ): View {
                        val tagView = LayoutInflater.from(curContext)
                            .inflate(R.layout.position_recommend_job_card_tag, parent, false) as TextView
                        tagView.run {
                            text = t?.value
                            background = Bovb.with().color(getColor(R.color.C_S2)).radius(4.dp.toFloat()).build()
                        }
                        return tagView
                    }
                }
                layoutFlow.adapter = tagAdapter
            }
        }

    }
}