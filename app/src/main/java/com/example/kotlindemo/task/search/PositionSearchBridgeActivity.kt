package com.example.kotlindemo.task.search

import android.os.Bundle
import androidx.activity.viewModels
import com.example.kotlindemo.activity.BaseActivity
import com.example.kotlindemo.databinding.ActivityPositionSearchBridgeBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.utils.StatusBarUtil
import com.example.kotlindemo.utils.binding
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter

/**
 * @Description 职位搜索桥页面
 * @Author LuoJia
 * @Date 2023/9/21
 */
class PositionSearchBridgeActivity : BaseActivity() {

    private val binding: ActivityPositionSearchBridgeBinding by binding()

    private val viewModel by viewModels<PositionSearchBridgeViewModel>()

    private val listAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(
            diffEnable = true
        ).apply {
            register(PositionSearchDelegate())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        StatusBarUtil.setStatusBar(this, false, false)
        binding.rvHistory.run {
            adapter = listAdapter
        }
    }

    private fun initData() {
        request()
        collect()
    }

    private fun request() {
        viewModel.requestHistoryData()
    }

    private fun collect() {
        viewModel.stateFlow.collectState(this) {

        }
        viewModel.stateFlow.collectState(this, SearchBridgeState::searchList) {
            // 单独对历史搜索记录做局部刷新
            if (!it.isNullOrEmpty()) {
                listAdapter.submitList(it)
            }
        }
        viewModel.eventFlow.collectEvent(this) {
            when(it) {
                SearchBridgeEvent.ShowClearAllDialog -> {}
                SearchBridgeEvent.ShowUnsubscribeAndClearAllDialog -> {}
                SearchBridgeEvent.ShowUnsubscribeAndClearDialog -> {}
            }
        }
    }

    /**
     * 展示删除历史记录弹窗
     */
    private fun showDeleteDialog() {

    }

}