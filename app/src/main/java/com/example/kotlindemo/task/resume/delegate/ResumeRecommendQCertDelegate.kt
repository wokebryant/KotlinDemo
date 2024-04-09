package com.example.kotlindemo.task.resume.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutResumeRecommendQualificationCertBinding
import com.example.kotlindemo.task.resume.QualificationCertState
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
 * @Description 简历点后推荐页卡片 (资格证书)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendQCertDelegate : BindingViewDelegate<QualificationCertState, LayoutResumeRecommendQualificationCertBinding>() {

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendQualificationCertBinding,
        item: QualificationCertState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendQualificationCertBinding,
        item: QualificationCertState
    ) {
        binding.inTitle.tvTitle.text = "资格证书"
        setSkillFlowLayout(binding.layoutSkillFlow, item.certList)
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
            }, 0, 0, 12, 0)
        } else {
            flowLayout.setGone()
        }
    }

}