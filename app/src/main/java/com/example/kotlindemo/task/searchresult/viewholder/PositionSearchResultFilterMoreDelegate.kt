package com.example.kotlindemo.task.searchresult.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.databinding.LayoutSearchResultFilterMoreTemBinding
import com.example.kotlindemo.task.searchresult.ResultFilterMoreState
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description 搜索结果页筛选更多
 * @Author LuoJia
 * @Date 2023/10/19
 */
class PositionSearchResultFilterMoreDelegate : BindingViewDelegate<ResultFilterMoreState, LayoutSearchResultFilterMoreTemBinding>() {

    override fun onBindViewHolder(
        binding: LayoutSearchResultFilterMoreTemBinding,
        item: ResultFilterMoreState,
        position: Int
    ) {
        binding.tvName.text = item.title
    }

    override fun onItemClick(
        holder: RecyclerView.ViewHolder,
        item: ResultFilterMoreState,
        position: Int
    ) {
        super.onItemClick(holder, item, position)
    }

}