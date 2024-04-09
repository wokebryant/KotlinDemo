package com.example.kotlindemo.task.resume.delegate

import com.example.kotlindemo.databinding.ItemResumeRecommendObtainedCertBinding
import com.example.kotlindemo.databinding.LayoutResumeRecommendBaseListBinding
import com.example.kotlindemo.task.resume.ObtainedCertState
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description 简历点后推荐页卡片 (所获证书)
 * @Author LuoJia
 * @Date 2024/03/19
 */
class ResumeRecommendObtainedCertDelegate : BindingViewDelegate<ObtainedCertState, LayoutResumeRecommendBaseListBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply { register(ObtainedCertItemDelegate()) }
    }

    override fun onBindViewHolder(
        binding: LayoutResumeRecommendBaseListBinding,
        item: ObtainedCertState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: LayoutResumeRecommendBaseListBinding,
        item: ObtainedCertState,
    ) {
        with(binding) {
            inTitle.tvTitle.text = "所获证书"
            rvList.adapter = listAdapter
            listAdapter.setList(item.certList)
        }
    }

    inner class ObtainedCertItemDelegate : BindingViewDelegate<ObtainedCertState.ItemState, ItemResumeRecommendObtainedCertBinding>() {

        override fun onBindViewHolder(
            binding: ItemResumeRecommendObtainedCertBinding,
            item: ObtainedCertState.ItemState,
            position: Int
        ) {
            with(binding) {
                tvCertName.text = item.certName
                tvCertDate.text = item.date
            }
        }

    }


}