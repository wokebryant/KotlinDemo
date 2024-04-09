package com.example.kotlindemo.task.microenterprises.view.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.BMainMicroFragmentResumeHomeBinding
import com.example.kotlindemo.databinding.BMainMicroFragmentResumeTabItemBinding
import com.example.kotlindemo.task.microenterprises.bean.MircoResumeJobBean
import com.example.kotlindemo.task.microenterprises.bean.mockMircoJobList
import com.example.kotlindemo.task.microenterprises.view.page.MicroResumeListFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.zhaopin.social.module_common_util.ext.binding

/**
 * @Description 小微企业简历列表首页
 * @Author LuoJia
 * @Date 2023/8/30
 */
class MicroResumeHomeFragment : BaseActivity(), MircoResumeHomeCallback{

    private val binding: BMainMicroFragmentResumeHomeBinding by binding()

    private val viewModel by viewModels<MicroFragmentHomeViewModel>()

    private lateinit var viewpagerAdapter: ViewPager2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        with(binding) {
            // 设置ViewPager2
            viewPager.run {
                viewpagerAdapter = ViewPager2Adapter(this@MicroResumeHomeFragment)
                adapter = viewpagerAdapter
                offscreenPageLimit = 3
                registerOnPageChangeCallback(onPageChangeCallback)
            }
            // 设置ViewPager2和TabLayout联动
            TabLayoutMediator(navBar.tabLayout, viewPager) { tab, position ->
                // 自定义TabItem
                val customTab = BMainMicroFragmentResumeTabItemBinding.inflate(layoutInflater)
                customTab.tvTabItem.text = mockMircoJobList[position].jobName
                tab.customView = customTab.root
            }.attach()
            navBar.callback= this@MicroResumeHomeFragment
        }
    }

    private fun initData() {
        val fragmentList = mutableListOf<Fragment>()
        mockMircoJobList.forEach {
            fragmentList.add ( MicroResumeListFragment(it) )
        }
        viewpagerAdapter.setList(fragmentList)
    }

    /**
     * ViewPager2页面change回调
     */
    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

    override fun onTabClick(position: Int) {

    }

    override fun onAddJobClick() {
        val newTab = MircoResumeJobBean(jobName = "新增Tab")
        mockMircoJobList.add(newTab)
        viewpagerAdapter.addItem(MicroResumeListFragment(newTab))
        binding.viewPager.currentItem = viewpagerAdapter.itemCount - 1
    }

}