package com.example.kotlindemo.task.resume.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemResumeRecommendWorkExpBinding
import com.example.kotlindemo.databinding.LayoutResumeRecommendBaseListBinding
import com.example.kotlindemo.task.resume.WorkExpState
import com.zhaopin.common.widget.flowLayout.FlowLayout
import com.zhaopin.common.widget.flowLayout.NoActionTagLy
import com.zhaopin.common.widget.flowLayout.TagAdapter
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 简历点后推荐页卡片 (工作经历)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendWorkExpDelegate : BindingViewDelegate<WorkExpState, LayoutResumeRecommendBaseListBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(WorkExpItemDelegate()) }
    }

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendBaseListBinding,
        item: WorkExpState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendBaseListBinding,
        item: WorkExpState
    ) {
        binding.inTitle.tvTitle.text = "工作经历"
        binding.rvList.adapter = listAdapter
        binding.rvList.updateLayoutParams<MarginLayoutParams> {
            topMargin = 0.dp
        }
        listAdapter.setList(item.workExpList)
    }

    inner class WorkExpItemDelegate : BindingViewDelegate<WorkExpState.ItemState, ItemResumeRecommendWorkExpBinding>() {

        override fun onBindViewHolder(
            binding: ItemResumeRecommendWorkExpBinding,
            item: WorkExpState.ItemState,
            position: Int
        ) {
            with(binding) {
                tvCompanyName.text = item.companyName
                tvCompanyName.onClick {

                }
                tvWorkTime.text = item.industryAndTime
                tvJobName.text = item.jobName
                tvDesc.text = item.jobDesc
                if (item.tagList.isNotEmpty()) {
                    setSkillFlowLayout(binding.layoutSkillFlow, item.tagList)
                }
                // 如果是最后一个Item,隐藏分割线
                if (position == item.itemSize - 1) {
                    viewSpilt.setGone()
                    root.setPadding(0, 20.dp, 0, 8.dp)
                } else {
                    viewSpilt.setVisible()
                    root.setPadding(0, 20.dp, 0, 0)
                }
            }
        }

        /**
         * 设置技能标签
         */
        private fun setSkillFlowLayout(flowLayout: NoActionTagLy, list: List<String>) {
            if (list.isNotEmpty()) {
                flowLayout.setVisible()
                flowLayout.setAdapter(object : TagAdapter<String>(list) {
                    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                        val textView = LayoutInflater.from(curContext)
                            .inflate(R.layout.resume_recommend_skill_tag, null, false) as TextView
                        textView.run {
                            text = t
                            background = Bovb.with().radius(4.dp.toFloat()).color(getColor(R.color.C_S2)).build()
                            height = 24.dp
                        }
                        return textView
                    }
                }, 0, 0, 6, 0)
            } else {
                flowLayout.setGone()
            }
        }

    }

}
