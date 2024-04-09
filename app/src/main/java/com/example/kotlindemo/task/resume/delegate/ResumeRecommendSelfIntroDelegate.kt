package com.example.kotlindemo.task.resume.delegate

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutResumeRecommendSelfIntroBinding
import com.example.kotlindemo.task.resume.SelfIntroState
import com.zhaopin.common.widget.flowLayout.FlowLayout
import com.zhaopin.common.widget.flowLayout.NoActionTagLy
import com.zhaopin.common.widget.flowLayout.TagAdapter
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 简历点后推荐页卡片 (自我介绍)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendSelfIntroDelegate : BindingViewDelegate<SelfIntroState, LayoutResumeRecommendSelfIntroBinding>() {

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendSelfIntroBinding,
        item: SelfIntroState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendSelfIntroBinding,
        item: SelfIntroState,
    ) {
        binding.inTitle.tvTitle.text = "自我介绍"
        binding.tvIntro.text = item.content
        setSkillFlowLayout(binding.layoutSkillFlow, item.skillList)
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
                        background = Bovb.with().radius(6.dp.toFloat()).color(getColor(R.color.C_S2)).build()
                        setPadding(12.dp, 0.dp, 12.dp, 0.dp)
                        height = 26.dp
                    }
                    return textView
                }
            }, 0, 0, 8, 0)
        } else {
            flowLayout.setGone()
        }
    }

}