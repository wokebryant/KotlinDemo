package com.example.kotlindemo.task.resume.delegate

import com.example.kotlindemo.databinding.ItemResumeRecommendExpectBinding
import com.example.kotlindemo.databinding.LayoutResumeRecommendBaseListBinding
import com.example.kotlindemo.task.resume.JobExpectState
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description 简历点后推荐页卡片 (求职期望)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendJobExpectDelegate : BindingViewDelegate<JobExpectState, LayoutResumeRecommendBaseListBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(JobExpectItemDelegate()) }
    }

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendBaseListBinding,
        item: JobExpectState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendBaseListBinding,
        item: JobExpectState,
    ) {
        with(binding) {
            inTitle.tvTitle.text = "求职期望"
            rvList.adapter = listAdapter
            listAdapter.setList(item.jobExpectList)
        }
    }

    inner class JobExpectItemDelegate : BindingViewDelegate<JobExpectState.ItemState, ItemResumeRecommendExpectBinding>() {

        override fun onBindViewHolder(
            binding: ItemResumeRecommendExpectBinding,
            item: JobExpectState.ItemState,
            position: Int
        ) {
            with(binding) {
                tvJobName.text = item.jobName
                tvSalary.text = item.salary
                tvDesc.text = item.desc
            }
        }
    }

}