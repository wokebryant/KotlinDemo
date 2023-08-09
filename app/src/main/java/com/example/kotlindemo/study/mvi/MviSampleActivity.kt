package com.example.kotlindemo.study.mvi

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.kotlindemo.activity.BaseActivity
import com.example.kotlindemo.databinding.ActivityMviSampleBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.study.mvi.core.collectStateLast
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.loadmore.delegate.LoadMoreDelegate
import com.zhaopin.list.multitype.loadmore.model.LoadMore
import com.zhaopin.list.multitype.loadmore.model.LoadMoreStatus
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast

/**
 * @Description MVI简单示例
 * @Author LuoJia
 * @Date 2023/7/18
 */
class MviSampleActivity : BaseActivity() {

    private val binding: ActivityMviSampleBinding by binding()

    private val viewModel by viewModels<MviListSampleViewModel>()

    private val listAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(
            diffEnable = true,
//            loadMoreEnable = true,
//            autoLoadEnable = true,
//            loadMore = LoadMore(loadMoreStatus = LoadMoreStatus.Loading),
//            loadMoreListener = { request(isLoadMore = true) }
        ).apply {
            register(JobKeyWordDelegate(this@MviSampleActivity))
            register(MviListItemDelegate())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding.rvList.run {
            adapter = listAdapter
            setHasFixedSize(true)
            itemAnimator = null
        }
        binding.tvRefresh.onClick {
            viewModel.requestTopCardData()
        }
        binding.swipeRefresh.setOnRefreshListener {
            request()
        }
    }

    private fun initData() {
        request()
        collect()
    }

    private fun request(isLoadMore: Boolean= false) {
        viewModel.requestListData(isLoadMore)
    }

    private fun collect() {
        // 全量订阅UiState
        viewModel.stateFlow.collectState(this) {
            Log.i("collectState: ", " Full")
            binding.tvTop.text = it.topCardContent
            binding.swipeRefresh.isRefreshing = false

            loadStateChange(it.loadMoreStatus)
            when(it.pageState) {
                PageState.Loading -> showLoading()
                PageState.Content -> showContent()
                PageState.Empty -> showEmpty()
                PageState.Error -> showError()
            }

        }
        // UiState局部刷新
        viewModel.stateFlow.collectState(this, MviSampleState::loadList) {
            Log.i("collectState: ", " Part")
            if (it.isNotEmpty()) {
                listAdapter.submitList(it) {

                }
            }
        }
        // UiEvent订阅
        viewModel.eventFlow.collectEvent(this) {
            when(it) {
                MviSampleEvent.ShowLoadingSuccessToast -> {}
            }
            this.showToast("刷新成功")
        }
    }

    private fun showContent() {
        with(binding) {
            rvList.setVisible()
            layoutEmpty.setGone()
            progressBar.setGone()
        }
    }

    private fun showEmpty() {
        with(binding) {
            rvList.setGone()
            layoutEmpty.setVisible()
            progressBar.setGone()
        }
    }

    private fun showError() {
        with(binding) {
            rvList.setGone()
            layoutEmpty.setGone()
            progressBar.setGone()
        }
    }

    private fun showLoading() {
        with(binding) {
            rvList.setGone()
            layoutEmpty.setGone()
            progressBar.setVisible()
        }
    }

    private fun loadStateChange(loadMoreStatus: LoadMoreStatus) {
        when (loadMoreStatus) {
            LoadMoreStatus.Fail -> listAdapter.loadMoreModule?.loadMoreFail()
            LoadMoreStatus.Complete -> listAdapter.loadMoreModule?.loadMoreComplete()
            LoadMoreStatus.End -> listAdapter.loadMoreModule?.loadMoreEnd()
            LoadMoreStatus.Loading -> { }
        }
    }

}