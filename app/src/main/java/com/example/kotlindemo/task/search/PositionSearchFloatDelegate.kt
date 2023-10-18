package com.example.kotlindemo.task.search

import com.example.kotlindemo.databinding.ItemKeywordLayoutBinding
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/8
 */
class PositionSearchFloatDelegate : BindingViewDelegate<String, ItemKeywordLayoutBinding>() {

    override fun onBindViewHolder(binding: ItemKeywordLayoutBinding, item: String, position: Int) {
        with(binding) {
            tipKeyword.text = item
        }
    }
}