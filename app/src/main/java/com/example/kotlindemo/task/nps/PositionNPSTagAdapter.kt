package com.example.kotlindemo.task.nps

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.kotlindemo.databinding.ItemNpsTagBinding
import com.example.kotlindemo.widget.diffUtil.FlowMVXLayoutListAdapter

/**
 * @Description 职位NPS标签适配器
 * @Author LuoJia
 * @Date 2024/07/05
 */
class PositionNPSTagAdapter : FlowMVXLayoutListAdapter<NPSTagState, ItemNpsTagBinding>() {

    override fun getItemViewBinding(parent: ViewGroup) =
        ItemNpsTagBinding.inflate(LayoutInflater.from(parent.context))

    override fun onBindView(binding: ItemNpsTagBinding, data: NPSTagState, position: Int) {
        binding.tvTag.text = data.name
        binding.tvTag.isSelected = data.selected
    }
}