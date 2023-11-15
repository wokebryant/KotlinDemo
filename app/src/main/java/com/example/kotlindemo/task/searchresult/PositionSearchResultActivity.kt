package com.example.kotlindemo.task.searchresult

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityPositionSearchResultBinding
import com.example.kotlindemo.databinding.PositionSearchResultTabItemBinding
import com.example.kotlindemo.task.searchresult.fragment.PositionSearchResultFragmentAdapter
import com.example.kotlindemo.utils.binding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @Description 搜索结果页
 * @Author LuoJia
 * @Date 2023/10/17
 */
class PositionSearchResultActivity : BaseActivity() {

    private val binding: ActivityPositionSearchResultBinding by binding()

    private lateinit var viewpagerAdapter: PositionSearchResultFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private val tabNameList = mutableListOf("职位", "公司")

    private fun initView() {
        with(binding) {
            // 设置ViewPager2
            viewPager.run {
                viewpagerAdapter = PositionSearchResultFragmentAdapter(this@PositionSearchResultActivity)
                adapter = viewpagerAdapter
                isUserInputEnabled = false
                registerOnPageChangeCallback(onPageChangeCallback)
            }
            // 设置ViewPager2和TabLayout联动
            TabLayoutMediator(navBar.tabLayout, viewPager) { tab, position ->
                // 自定义TabItem
                val customTab = PositionSearchResultTabItemBinding.inflate(layoutInflater)
                customTab.tvTabItem.text = tabNameList[position]
                tab.customView = customTab.root
            }.attach()
        }
    }

    /**
     * ViewPager2页面change回调
     */
    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

}