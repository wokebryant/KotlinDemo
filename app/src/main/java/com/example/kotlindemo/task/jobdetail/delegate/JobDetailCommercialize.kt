package com.example.kotlindemo.task.jobdetail.delegate

import android.util.Log
import android.view.View
import com.example.kotlindemo.databinding.JobDetailItemCommercializeBinding
import com.example.kotlindemo.task.jobdetail.CommercializeState
import com.example.kotlindemo.task.jobdetail.onJClick
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.common.extension.getDrawable

/**
 * @Description 职位详情页投后商业化卡片
 * @Author LuoJia
 * @Date 2023/11/29
 */
class JobDetailCommercialize : BindingViewDelegate<CommercializeState, JobDetailItemCommercializeBinding>() {

    override fun onBindViewHolder(
        binding: JobDetailItemCommercializeBinding,
        item: CommercializeState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(binding: JobDetailItemCommercializeBinding, item: CommercializeState) {
        Log.i("LuoJia-RE", "JobDetailCommercialize")

        with(binding) {
            llTitle.run {
                background = getDrawable(item.titleBg)
            }
            tvTitle.text = item.title
            tvContent.text = item.content
            ivIcon.run {
                visibility = if (item.icon != 0) View.VISIBLE else View.GONE
                setImageResource(item.icon)
            }
            tvSubContent.text = item.subContent
            tvBtn.text = item.buttonContent
            ivClose.onJClick {
                // TODO 点击关闭
            }
        }
    }

}