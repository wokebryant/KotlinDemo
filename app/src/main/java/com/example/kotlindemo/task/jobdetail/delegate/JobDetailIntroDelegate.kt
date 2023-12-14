package com.example.kotlindemo.task.jobdetail.delegate

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.JobDetailItemIntroBinding
import com.example.kotlindemo.task.jobdetail.IntroState
import com.example.kotlindemo.task.jobdetail.IntroTagData
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext

/**
 * @Description 职位详情页简介卡
 * @Author LuoJia
 * @Date 2023/11/22
 */
class JobDetailIntroDelegate : BindingViewDelegate<IntroState, JobDetailItemIntroBinding>() {

    override fun onBindViewHolder(
        binding: JobDetailItemIntroBinding,
        item: IntroState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(binding: JobDetailItemIntroBinding, item: IntroState) {
        Log.i("LuoJia-RE", "JobDetailIntroDelegate")
        with(binding) {
            tvJobName.text = item.jobName
            tvSalary.text = item.salary
            tvStreet.text = item.street
            tvRouteTime.text = item.routeTime
            // 设置标签
            setTagFlowLayout(binding.layoutFlow, item.tagList)
        }
    }

    private fun setTagFlowLayout(flowLayout: TagFlowLayoutOrigin, tagList: List<IntroTagData>) {
        val tagAdapter = object : TagAdapterOrigin<IntroTagData>(tagList) {
            override fun getView(parent: FlowLayoutOrigin?, position: Int, tag: IntroTagData): View {
                val layout =  LayoutInflater.from(curContext)
                    .inflate(R.layout.job_detail_item_intro_tag, parent, false) as LinearLayout
                val iconView = layout.findViewById<ImageView>(R.id.iv_icon)
                val contentTextView = layout.findViewById<TextView>(R.id.tv_tag)
                iconView.setImageResource(tag.icon)
                contentTextView.text = tag.tagName
                return layout
            }
        }
        flowLayout.adapter = tagAdapter
    }

}