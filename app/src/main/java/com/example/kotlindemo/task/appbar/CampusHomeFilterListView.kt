package com.example.kotlindemo.task.appbar

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemCampusFilterListBinding
import com.example.kotlindemo.databinding.LayoutCampusFilterListBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.getDrawable
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/9/9
 */
class CampusHomeFilterListView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private var mockList = listOf(
        CampusHomeFilterItemState(
            name = "综合",
            image = "",
            selected = true,
            type = "text"
        ),
        CampusHomeFilterItemState(
            name = "",
            image = "",
            selected = false,
            type = "image"
        ),
        CampusHomeFilterItemState(
            name = "",
            image = "",
            selected = false,
            type = "image"
        ),
        CampusHomeFilterItemState(
            name = "",
            image = "",
            selected = false,
            type = "image"
        ),
        CampusHomeFilterItemState(
            name = "",
            image = "",
            selected = false,
            type = "image"
        ),
    )

    private val binding: LayoutCampusFilterListBinding by binding()

    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(FilterItemDelegate(::onTagClick))
        }
    }

    /** 默认选中第一位 */
    private var selectedIndex = 0

    fun setData() {
        binding.rvFilterList.run {
            adapter = listAdapter
            itemAnimator = null
        }

        listAdapter.setList(mockList)
    }

    /**
     * 标签点击
     */
    private fun onTagClick(position: Int) {
        if (selectedIndex == position) {
            return
        }
        val newList = mockList.mapIndexed { index, state ->
            if (position == index) {
                selectedIndex = index
                state.copy(selected = !state.selected)
            } else {
                state.copy(selected = false)
            }
        }
        mockList = newList
        listAdapter.setList(newList)
    }


    inner class FilterItemDelegate(
        private val clickCallback: (Int) -> Unit
    ): BindingViewDelegate<CampusHomeFilterItemState, ItemCampusFilterListBinding>() {
        override fun onBindViewHolder(
            binding: ItemCampusFilterListBinding,
            item: CampusHomeFilterItemState,
            position: Int
        ) {
            if (item.type == "text") {
                binding.tvTag.setVisible()
                binding.ivTag.setGone()
                binding.tvTag.text = item.name
                binding.tvTag.setTextColor(
                    if (item.selected) getColor(R.color.C_W1) else getColor(R.color.C_B2)
                )
                binding.tvTag.background =
                    if (item.selected) getDrawable(R.color.C_P1) else getDrawable(R.color.C_S2)
            } else {
                binding.tvTag.setGone()
                binding.ivTag.setVisible()
                val imgDrawable = if (item.selected) getDrawable(R.drawable.ic_pro_match_selected)
                                  else getDrawable(R.drawable.ic_pro_match_un_selected)
                binding.ivTag.setImageDrawable(imgDrawable)
            }
            binding.root.onClick {
                clickCallback.invoke(position)
            }
        }
    }

    data class CampusHomeFilterItemState(
        val name: String,
        val image: String,
        val selected: Boolean,
        val type: String,
    )

}