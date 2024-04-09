package com.example.kotlindemo.task.resume.delegate

import com.example.kotlindemo.databinding.ItemResumeRecommendHighlightBinding
import com.example.kotlindemo.databinding.LayoutResumeRecommendBaseListBinding
import com.example.kotlindemo.task.resume.ResumeHighlightState
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description 简历点后推荐页卡片 (履历亮点)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendHighlightDelegate : BindingViewDelegate<ResumeHighlightState, LayoutResumeRecommendBaseListBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(HighlightItemDelegate()) }
    }

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendBaseListBinding,
        item: ResumeHighlightState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendBaseListBinding,
        item: ResumeHighlightState
    ) {
        binding.inTitle.tvTitle.text = "履历亮点"
        binding.rvList.adapter = listAdapter
        listAdapter.setList(item.highlightList)
    }

    inner class HighlightItemDelegate : BindingViewDelegate<String, ItemResumeRecommendHighlightBinding>() {

        override fun onBindViewHolder(
            binding: ItemResumeRecommendHighlightBinding,
            item: String,
            position: Int
        ) {
            binding.tvHighlight.text = item
        }

    }



}