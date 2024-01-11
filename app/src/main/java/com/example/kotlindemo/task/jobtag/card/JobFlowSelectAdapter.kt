package com.example.kotlindemo.task.jobtag.card

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        when (data.type) {
            TagUiType.Normal -> {
                binding.tvTag.setVisible()
                binding.tvSearchResultTag.setGone()
                bindData(binding.tvTag, binding.tvTitle, data, position)
            }
            TagUiType.SearchResult -> {
                binding.tvTag.setGone()
                binding.tvSearchResultTag.setVisible()
                bindData(binding.tvSearchResultTag, binding.tvTitle, data, position)
            }
        }
    }

    private fun bindData(
        tagView: TextView,
        titleView: TextView,
        data: JobFlowLayoutUIState,
        position: Int) {
        tagView.text = data.name
        tagView.isEnabled = data.enable
        if (data.enable) {
            tagView.isSelected = data.selected
            tagView.typeface = if (data.selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }
        titleView.visibility = if (position == 0 && data.title.isNotEmpty()) View.VISIBLE else View.GONE
        titleView.text = data.title
    }
}

data class JobFlowLayoutUIState(
    override val name: String,
    override val selected: Boolean,
    val code: String = "",
    val title: String = "",
    val enable: Boolean,
    val type: TagUiType = TagUiType.Normal,
    val itemClick: (position: Int, data: JobFlowLayoutUIState) -> Unit,
) : MVXTagUIState

/**
 * 标签样式
 */
enum class TagUiType {
    Normal,
    SearchResult
}