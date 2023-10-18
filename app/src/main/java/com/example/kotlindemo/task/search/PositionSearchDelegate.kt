package com.example.kotlindemo.task.search

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.example.kotlindemo.databinding.ItemPositionSearchHistoryBinding
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位搜索桥历史记录Delegate
 * @Author LuoJia
 * @Date 2023/9/27
 */
class PositionSearchDelegate : BindingViewDelegate<SearchItemState, ItemPositionSearchHistoryBinding>(
        diffUtil = ItemDiffCallback()
) {

    override fun onBindViewHolder(
        binding: ItemPositionSearchHistoryBinding,
        item: SearchItemState,
        position: Int
    ) {
        with(binding) {
            tvTitle.text = item.title
            tvSubscribed.visibility = if (item.isSubscribe) View.VISIBLE else View.GONE
            ivDelete.onClick {
                item.onItemDelete.invoke(position, item)
            }
            root.onClick {
                item.onItemClick.invoke(position, item)
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<SearchItemState>() {
        override fun areItemsTheSame(oldItem: SearchItemState, newItem: SearchItemState): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchItemState, newItem: SearchItemState): Boolean {
            return oldItem == newItem
        }
    }
}