package com.example.kotlindemo.task.resume.delegate

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import coil.load
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutResumeRecommendBaseInfoBinding
import com.example.kotlindemo.task.resume.BaseInfoState
import com.example.kotlindemo.utils.dp
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 简历点后推荐页卡片 (基础信息)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendBaseInfoDelegate : BindingViewDelegate<BaseInfoState, LayoutResumeRecommendBaseInfoBinding>() {

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendBaseInfoBinding,
        item: BaseInfoState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendBaseInfoBinding,
        item: BaseInfoState
    ) {
        with(binding) {
            // 姓名
            tvName.text = item.name
            // 在线状态
            if (item.isOnline) {
                tvOnline.run {
                    setVisible()
                    background = Bovb.with()
                        .color(Color.parseColor("#1A00BF9F"))
                        .radius(4.dp)
                        .build()
                }
            } else {
                tvOnline.setGone()
            }
            // 距离
            if (item.distance.isNotEmpty()) {
                tvDistance.run {
                    setVisible()
                    text = item.distance
                    background = Bovb.with()
                        .color(Color.parseColor("#0D5C6A99"))
                        .radius(4.dp)
                        .borderColor(Color.parseColor("#4D5C6A99"))
                        .borderWidth(0.6.dp.toInt())
                        .build()
                }
            } else {
                tvDistance.setGone()
            }
            // 职位公司
            tvJobCompany.text = item.jobAndCompany
            // 头像
            ivAvatar.load(item.avatar) {
                error(R.drawable.c_common_icon_hr_new_default)
                placeholder(R.drawable.c_common_icon_hr_new_default)
            }
            // 性别
            val genderSrc = if (item.gender == 1) R.drawable.b_common_b_app_home_icon_male
                            else R.drawable.b_common_b_app_home_icon_male
            ivGender.setImageResource(genderSrc)
            // 到岗时间
            tvDutyTime.text = item.dutyTime
            // 标签
            setTagFlowLayout(binding.layoutFlow, item.tagList)
            // 描述
            tvDesc.text = item.desc.substring(0, 50)
            // 展开全部点击
            inExpandAll.root.onClick {
                tvDesc.text = item.desc
                it.setGone()
            }
        }
    }

    private fun setTagFlowLayout(flowLayout: TagFlowLayoutOrigin, tagList: List<BaseInfoState.TagState>) {
        val tagAdapter = object : TagAdapterOrigin<BaseInfoState.TagState>(tagList) {
            override fun getView(parent: FlowLayoutOrigin?, position: Int, tag: BaseInfoState.TagState): View {
                val layout =  LayoutInflater.from(curContext)
                    .inflate(R.layout.resume_recommend_item_intro_tag, parent, false) as LinearLayout
                val iconView = layout.findViewById<ImageView>(R.id.iv_icon)
                val contentTextView = layout.findViewById<TextView>(R.id.tv_tag)
                iconView.setImageResource(tag.icon)
                contentTextView.text = tag.name
                return layout
            }
        }
        flowLayout.adapter = tagAdapter
    }

}