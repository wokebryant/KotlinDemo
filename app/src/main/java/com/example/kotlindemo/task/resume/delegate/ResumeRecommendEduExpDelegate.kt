package com.example.kotlindemo.task.resume.delegate

import com.example.kotlindemo.databinding.ItemResumeRecommendEduExpBinding
import com.example.kotlindemo.databinding.LayoutResumeRecommendBaseListBinding
import com.example.kotlindemo.task.resume.EducationExpState
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.common.extension.setGone

/**
 * @Description 简历点后推荐页卡片 (教育经历)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendEduExpDelegate : BindingViewDelegate<EducationExpState, LayoutResumeRecommendBaseListBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(EduExpItemDelegate()) }
    }

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendBaseListBinding,
        item: EducationExpState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendBaseListBinding,
        item: EducationExpState,
    ) {
        binding.inTitle.tvTitle.text = "教育经历"
        binding.rvList.adapter = listAdapter
        listAdapter.setList(item.eduList)
    }

    inner class EduExpItemDelegate : BindingViewDelegate<EducationExpState.ItemState, ItemResumeRecommendEduExpBinding>() {
        override fun onBindViewHolder(
            binding: ItemResumeRecommendEduExpBinding,
            item: EducationExpState.ItemState,
            position: Int
        ) {
            with(binding) {
                tvSchoolName.text = item.schoolName
                if (item.schoolTag.isNotEmpty()) {
                    tvSchoolTag.setVisible()
                    tvSchoolTag.text = item.schoolTag
                } else {
                    tvSchoolTag.setGone()
                }
                tvSchoolTime.text = item.schoolTime
                tvMajor.text = item.majorName
                tvMajorDesc.text = item.majorDesc
            }
        }

    }
}