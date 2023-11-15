package com.example.kotlindemo.task.searchresult.viewholder

import com.example.kotlindemo.databinding.LayoutSearchResultJobItemBinding
import com.example.kotlindemo.task.searchresult.ResultJobState
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/18
 */
class PositionResultJobDelegate : BindingViewDelegate<ResultJobState, LayoutSearchResultJobItemBinding>() {

    override fun onBindViewHolder(
        binding: LayoutSearchResultJobItemBinding,
        item: ResultJobState,
        position: Int
    ) {
        binding.tvTitle.text = item.title
    }
}