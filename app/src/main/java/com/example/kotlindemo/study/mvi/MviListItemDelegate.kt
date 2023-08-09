package com.example.kotlindemo.study.mvi

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlindemo.databinding.ItemMviSampleBinding
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/19
 */
class MviListItemDelegate(): BindingViewDelegate<MviListItemState, ItemMviSampleBinding>(diffUtil = ItemDiffCallback()) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        binding: ItemMviSampleBinding,
        item: MviListItemState,
        position: Int
    ) {
        with(binding) {
            tvContent.text = item.content
            tvUpdate.onClick {
                item.clickState?.invoke(ClickState.Update, item)
            }
            ivDelete.onClick {
                item.clickState?.invoke(ClickState.Remove, item)
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<MviListItemState>() {
        override fun areItemsTheSame(oldItem: MviListItemState, newItem: MviListItemState): Boolean {
            return oldItem.content == newItem.content
        }

        override fun areContentsTheSame(oldItem: MviListItemState, newItem: MviListItemState): Boolean {
            return oldItem == newItem
        }
    }

}