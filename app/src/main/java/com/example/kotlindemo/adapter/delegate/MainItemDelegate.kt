package com.example.kotlindemo.adapter.delegate

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.databinding.LayoutMainItemBinding
import com.example.kotlindemo.model.MainItemState
import com.example.kotlindemo.model.MainItemType
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.getDrawable

/**
 * @Description 主页Item
 * @Author LuoJia
 * @Date 2023/10/17
 */
class MainItemDelegate(
    private val onClickCallback: ((MainItemType) -> Unit)? =null
) : BindingViewDelegate<MainItemState, LayoutMainItemBinding>() {

    override fun onBindViewHolder(binding: LayoutMainItemBinding, item: MainItemState, position: Int) {
        with(binding) {
            tvMainTitle.run {
                text = item.title
                setTextColor(getColor(item.textColor))
            }
            root.run {
                background = getDrawable(item.bgColor)
            }
        }
    }

    override fun onItemClick(holder: RecyclerView.ViewHolder, item: MainItemState, position: Int) {
        super.onItemClick(holder, item, position)
        onClickCallback?.invoke(item.type)
    }

}