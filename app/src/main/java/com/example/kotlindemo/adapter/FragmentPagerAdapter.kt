package com.example.kotlindemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlindemo.fragment.AnotherFragment
import com.example.kotlindemo.fragment.HomeFragment
import com.example.kotlindemo.fragment.IndicatorFragment
import com.example.kotlindemo.fragment.PageFragment

/**
 *  ViewPager2 Fragment适配器
 */
class FragmentPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val PAGE_HOME = 0
        const val PAGE_FIND = 1
        const val PAGE_INDICATOR = 2
        const val PAGE_OTHERS = 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            PAGE_HOME -> HomeFragment()
            PAGE_FIND -> PageFragment()
            PAGE_INDICATOR -> IndicatorFragment()
            PAGE_OTHERS -> AnotherFragment()
            else -> AnotherFragment()
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

}