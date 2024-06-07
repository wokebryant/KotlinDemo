package com.example.kotlindemo.task.searchresult.fragment

import android.os.Bundle
import android.view.View
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentPositionSearchResultBinding
import com.example.kotlindemo.task.jobtag.card.JobFlowLayoutUIState
import com.example.kotlindemo.task.jobtag.card.JobFlowSelectAdapter
import com.example.kotlindemo.task.searchresult.MockSearchResultData
import com.example.kotlindemo.task.searchresult.viewholder.PositionResultJobDelegate
import com.example.kotlindemo.task.searchresult.ResultJobState
import com.example.kotlindemo.task.searchresult.bean.PositionSearchResultFilterTag
import com.example.kotlindemo.task.searchresult.bean.PositionSearchResultFilterTagsBean
import com.example.kotlindemo.task.searchresult.viewholder.PositionSearchResultFilterMoreDelegate
import com.zhaopin.list.multitype.adapter.AnimationType
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setAnimationWithDefault
import com.zhaopin.list.multitype.adapter.setList

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/10/18
 */
class PositionSearchResultFragment : BaseFragment<FragmentPositionSearchResultBinding>() {

    /** 筛选更多列表适配器 */
    private val filterMoreAdapter by lazy {
        MultiTypeAdapter().apply {
            register(PositionSearchResultFilterMoreDelegate())
        }
    }

    /** 筛选标签列表适配器 */
    private val filterTagListAdapter by lazy { JobFlowSelectAdapter() }

    /** 职位列表适配器 */
    private val jobListAdapter by lazy {
        MultiTypeAdapter().apply {
            register(PositionResultJobDelegate())
            animationEnable = true
            setAnimationWithDefault(AnimationType.SlideInLeft)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initRecyclerView()
    }


    private fun initRecyclerView() {
        binding.rvFilterMore.run {
            adapter = filterMoreAdapter
            filterMoreAdapter.setList(MockSearchResultData.getMockFilterMoreList())
        }
        binding.rvFilterTag.run {
            adapter = filterTagListAdapter
            filterTagListAdapter.list = MockSearchResultData.getMockFilterTagList()
        }
        binding.rvJobList.run {
            adapter = jobListAdapter
            jobListAdapter.setList(MockSearchResultData.getMockJobListData())
        }
    }

}