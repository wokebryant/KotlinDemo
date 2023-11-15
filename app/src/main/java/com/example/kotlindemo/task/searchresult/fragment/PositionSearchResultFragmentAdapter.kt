package com.example.kotlindemo.task.searchresult.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlindemo.task.searchresult.PAGE_COMPANY
import com.example.kotlindemo.task.searchresult.PAGE_POSITION

/**
 * @Description 搜索结果页Fragment适配器
 * @Author LuoJia
 * @Date 2023/10/18
 */
class PositionSearchResultFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_POSITION -> PositionSearchResultFragment()
            PAGE_COMPANY -> PositionSearchResultFragment()
            else -> PositionSearchResultFragment()
        }
    }
}