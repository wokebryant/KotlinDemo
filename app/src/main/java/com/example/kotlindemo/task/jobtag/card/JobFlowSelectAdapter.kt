package com.example.kotlindemo.task.jobtag.card

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlindemo.databinding.ItemJobFlowLayoutBinding
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.common.widget.mvx.flowLayout.FlowMVXLayoutAdapter
import com.zhaopin.common.widget.mvx.flowLayout.MVXFlowLayoutUIState
import com.zhaopin.common.widget.mvx.flowLayout.MVXTagUIState

/**
 * @Description 职位标签选择器
 * @Author LuoJia
 * @Date 2023/9/20
 */
class JobFlowSelectAdapter : FlowMVXLayoutAdapter<JobFlowLayoutUIState, ItemJobFlowLayoutBinding>() {

    override fun getItemViewBinding(parent: ViewGroup) =
        ItemJobFlowLayoutBinding.inflate(LayoutInflater.from(parent.context))

    override fun onItemClick(position: Int, data: JobFlowLayoutUIState) {
        data.itemClick.invoke(position, data)
    }

    override fun onBindView(
        binding: ItemJobFlowLayoutBinding,
        data: JobFlowLayoutUIState,
        position: Int
    ) {
        binding.tvTag.text = data.name
        binding.tvTag.isEnabled = data.enable
        if (data.enable) {
            binding.tvTag.isSelected = data.selected
            binding.tvTag.typeface = if (data.selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }
        if (position == 0) {
            binding.tvTitle.setVisible()
        } else {
            binding.tvTitle.setGone()
        }
    }
}

data class JobFlowLayoutUIState(
    override val name: String,
    override val selected: Boolean,
    val title: String,
    val enable: Boolean,
    val itemClick: (position: Int, data: JobFlowLayoutUIState) -> Unit,
) : MVXTagUIState