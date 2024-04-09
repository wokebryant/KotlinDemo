package com.example.kotlindemo.task.resume.delegate

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutResumeRecommendProSkillBinding
import com.example.kotlindemo.task.resume.ProSkillState
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext

/**
 * @Description 简历点后推荐页卡片 (专业技能)
 * @Author LuoJia
 * @Date 2024/03/21
 */
class ResumeRecommendProSkillDelegate : BindingViewDelegate<ProSkillState, LayoutResumeRecommendProSkillBinding>() {

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendProSkillBinding,
        item: ProSkillState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendProSkillBinding,
        item: ProSkillState
    ) {
        binding.inTitle.tvTitle.text = "专业技能"
        setTagFlowLayout(binding.layoutFlow, item.skillList)

    }

    private fun setTagFlowLayout(flowLayout: TagFlowLayoutOrigin, tagList: List<ProSkillState.ItemState>) {
        val tagAdapter = object : TagAdapterOrigin<ProSkillState.ItemState>(tagList) {
            override fun getView(parent: FlowLayoutOrigin?, position: Int, tag: ProSkillState.ItemState): View {
                val layout =  LayoutInflater.from(curContext)
                    .inflate(R.layout.item_resume_recommend_pro_skill, parent, false) as LinearLayout
                val skillTextView = layout.findViewById<TextView>(R.id.tv_skill)
                val dateTextView = layout.findViewById<TextView>(R.id.tv_date)
                skillTextView.text = tag.skill
                dateTextView.text = tag.year
                return layout
            }
        }
        flowLayout.adapter = tagAdapter
    }

}