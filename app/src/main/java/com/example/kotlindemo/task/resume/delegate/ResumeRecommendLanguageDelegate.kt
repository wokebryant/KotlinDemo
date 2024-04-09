package com.example.kotlindemo.task.resume.delegate

import com.example.kotlindemo.databinding.ItemResumeRecommendLanguageBinding
import com.example.kotlindemo.databinding.LayoutResumeRecommendBaseListBinding
import com.example.kotlindemo.task.resume.LanguageSkillState
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description 简历点后推荐页卡片 (语言能力)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendLanguageDelegate : BindingViewDelegate<LanguageSkillState, LayoutResumeRecommendBaseListBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(LanguageItemDelegate()) }
    }

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendBaseListBinding,
        item: LanguageSkillState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendBaseListBinding,
        item: LanguageSkillState
    ) {
        binding.inTitle.tvTitle.text = "语言能力"
        binding.rvList.adapter = listAdapter
        listAdapter.setList(item.languageList)
    }

    inner class LanguageItemDelegate : BindingViewDelegate<LanguageSkillState.ItemState, ItemResumeRecommendLanguageBinding>() {

        override fun onBindViewHolder(
            binding: ItemResumeRecommendLanguageBinding,
            item: LanguageSkillState.ItemState,
            position: Int
        ) {
            with(binding) {
                tvLanguage.text = item.languageName
                tvReadSkill.text = item.readSkill
                tvSpeakSkill.text = item.speakSkill
            }
        }

    }
}