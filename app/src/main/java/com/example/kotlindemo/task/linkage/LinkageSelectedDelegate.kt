package com.example.kotlindemo.task.linkage

import androidx.recyclerview.widget.DiffUtil
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemLinkageSelectedBinding
import com.example.kotlindemo.utils.getColor
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 二级联动已选择列表适配器
 * @Author LuoJia
 * @Date 2023/7/3
 */
class LinkageSelectedDelegate(private val onDeleteCallback: (LinkageChildItem) -> Unit)
    : BindingViewDelegate<LinkageChildItem, ItemLinkageSelectedBinding>(diffUtil = ItemDiffCallback()) {

    override fun onBindViewHolder(
        binding: ItemLinkageSelectedBinding,
        item: LinkageChildItem,
        position: Int
    ) {
        with(binding) {
            root.onClick {
                onDeleteCallback.invoke(item)
            }
            tvTag.text = item.name
            root.run {
                background = Bovb.with().color(getColor(R.color.C_F6F7F8)).radius(6f.dp).build()
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<LinkageChildItem>() {
        override fun areItemsTheSame(oldItem: LinkageChildItem, newItem: LinkageChildItem): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: LinkageChildItem, newItem: LinkageChildItem): Boolean {
            return oldItem == newItem
        }
    }

}