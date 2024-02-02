package com.example.kotlindemo.task.ai.delegate

import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemAiRecommendQuestionBinding
import com.example.kotlindemo.databinding.ItemAiRecommendQuestionItemBinding
import com.example.kotlindemo.task.ai.AiQuestionState
import com.example.kotlindemo.task.ai.util.AiRecommendManager
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description AI推荐问题卡
 * @Author LuoJia
 * @Date 2024/1/9
 */
class AiRecommendQuestionDelegate : BindingViewDelegate<AiQuestionState, ItemAiRecommendQuestionBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(OptionDelegate())
        }
    }

    /** 已选择的选项Index列表 */
    private val selectedIndexList: MutableSet<Int> = mutableSetOf()
    /** 是否为多选 */
    private var isMultiSelect = false

    override fun onBindViewHolder(
        binding: ItemAiRecommendQuestionBinding,
        item: AiQuestionState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(
        binding: ItemAiRecommendQuestionBinding,
        item: AiQuestionState,
    ) {
        with(binding) {
            isMultiSelect = item.multi
            // 设置卡片高度
            val cardHeight = AiRecommendManager.getQuestionCardHeight()
            binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
                height = cardHeight
            }
            tvTitle.text = item.title
            tvQuestion.text = item.question
            rvList.run {
                adapter = listAdapter
                listAdapter.setList(item.optionList)
            }
            // 确定点击
            tvSure.onClick {
                item.onSureClick.invoke()
            }
            // 关闭点击
            ivDelete.onClick {
                item.onDeleteClick.invoke()
            }
        }
    }

    /**
     * 选项点击
     */
    private fun onOptionClick(index: Int, state: AiQuestionState.Option) {
        if (isMultiSelect) {
            if (state.isSelected) selectedIndexList.remove(index) else selectedIndexList.add(index)
        } else {
            selectedIndexList.clear()
            selectedIndexList.add(index)
        }
    }

    inner class OptionDelegate : BindingViewDelegate<AiQuestionState.Option, ItemAiRecommendQuestionItemBinding>() {

        override fun onBindViewHolder(
            binding: ItemAiRecommendQuestionItemBinding,
            item: AiQuestionState.Option,
            position: Int
        ) {
            with(binding) {
                val textColor = if (item.isSelected) R.color.C_P1 else R.color.C_B1
                tvOption.run {
                    text = item.text
                    setTextColor(getColor(textColor))
                }
                tvOption.isSelected = item.isSelected
            }
        }

        override fun onItemClick(
            holder: RecyclerView.ViewHolder,
            item: AiQuestionState.Option,
            position: Int
        ) {
            super.onItemClick(holder, item, position)
            onOptionClick(position, item)
            item.onItemClick.invoke(position, selectedIndexList)
        }
    }
}