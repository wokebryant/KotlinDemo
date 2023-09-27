package com.example.kotlindemo.task.jobtag.card

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlindemo.databinding.ItemJobFlowLayoutBinding
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
        binding.root.text = data.name
        binding.root.isEnabled = data.enable
        if (data.enable) {
            binding.root.isSelected = data.selected
            binding.root.typeface = if (data.selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }
    }
}

data class JobFlowLayoutUIState(
    override val name: String,
    override val selected: Boolean,
    val enable: Boolean,
    val itemClick: (position: Int, data: JobFlowLayoutUIState) -> Unit,
) : MVXTagUIState