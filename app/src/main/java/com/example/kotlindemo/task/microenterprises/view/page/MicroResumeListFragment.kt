package com.example.kotlindemo.task.microenterprises.view.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.BMainMicroFragmentResumeListBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.task.microenterprises.bean.MircoResumeJobBean
import com.example.kotlindemo.task.microenterprises.bean.mockMircoResumeList
import com.example.kotlindemo.task.microenterprises.registerMicroResumeList
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList

/**
 * @Description 小微企业简历列表
 * @Author LuoJia
 * @Date 2023/8/29
 */
class MicroResumeListFragment(
    private val job: MircoResumeJobBean?
) : BaseFragment<BMainMicroFragmentResumeListBinding>() {

    private val viewModel by viewModels<MicroResumeListViewModel>()

    private val listAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(
            diffEnable = false
        ).apply {
            registerMicroResumeList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        with(binding) {
            rvList.run {
                adapter = listAdapter
            }
        }
    }

    private fun initData() {
        request()
        collect()
    }

    private fun request() {
        val data = mockMircoResumeList
        listAdapter.setList(data)
        viewModel.requestList()
    }

    private fun collect() {
        viewModel.stateFlow.collectState(this) {

        }
        viewModel.eventFlow.collectEvent(this) {

        }
    }

}