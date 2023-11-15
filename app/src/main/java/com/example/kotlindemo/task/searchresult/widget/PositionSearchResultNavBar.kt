package com.example.kotlindemo.task.searchresult.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutPositionSearchResultNavBarBinding
import com.example.kotlindemo.task.searchresult.PAGE_COMPANY
import com.example.kotlindemo.task.searchresult.PAGE_POSITION
import com.example.kotlindemo.task.searchresult.PositionSearchResultTab
import com.example.kotlindemo.utils.binding
import com.google.android.material.tabs.TabLayout
import com.zhaopin.social.common.extension.getColor

/**
 * @Description 职位搜索结果页顶部导航栏
 * @Author LuoJia
 * @Date 2023/10/18
 */
class PositionSearchResultNavBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attr, defStyleAttr) {

    /**
     * TabLayout选中监听
     */
    private val onTabLayoutSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.customView?.let {
                it.findViewById<TextView>(R.id.tv_tab_item).run {
                    textSize = 16f
                    setTextColor(getColor(R.color.C_B1))
                }
                val tabTab = when (tab.position) {
                    PAGE_POSITION -> PositionSearchResultTab.Position
                    PAGE_COMPANY -> PositionSearchResultTab.Company
                    else -> PositionSearchResultTab.Position
                }
                onTabClickCallback?.invoke(tabTab)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.customView?.let {
                it.findViewById<TextView>(R.id.tv_tab_item).run {
                    textSize = 13f
                    setTextColor(getColor(R.color.C_B3))
                }
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab?) { }
    }

    var onTabClickCallback: ((PositionSearchResultTab) -> Unit)? = null

    private val binding: LayoutPositionSearchResultNavBarBinding by binding()

    var tabLayout: TabLayout = binding.tableLayout.apply {
        addOnTabSelectedListener(onTabLayoutSelectedListener)
    }

}