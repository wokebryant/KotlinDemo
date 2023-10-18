package com.example.kotlindemo.task.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityPositionSearchBridgeBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.utils.StatusBarUtil
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.getColor
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.common.widget.dialog.BoDialog
import com.zhaopin.common.widget.dialog.OnViewClickListener
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

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

    private val floatAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(
            diffEnable = true
        ).apply {
            register(PositionSearchFloatDelegate())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        StatusBarUtil.setStatusBar(this, false, false)
        with(binding) {
            rvHistory.run {
                itemAnimator = null
                adapter = listAdapter
            }
            rvFloat.run {
                itemAnimator = null
                adapter = floatAdapter
            }
            ivDelete.onClick { viewModel.clearAllHistoryDataClick() }
            tvExpandAll.onClick { viewModel.expandAllHistoryData() }
        }
    }

    /**
     * 设置顶部搜索框
     */
    private fun keyWordTopView() {

    }

    private fun initData() {
        request()
        collect()
    }

    private fun request() {
        viewModel.requestHistoryData()
        viewModel.requestRecommendJobData()
        viewModel.requestKeywordList()
    }

    private fun collect() {
        viewModel.stateFlow.collectState(this) {
            // 设置职位推荐
            updateRecommendJobView(it.recommendData)
            // 设置关键词联想
//            updateKeyWordListView(it.keyWordList)
        }
        viewModel.stateFlow.collectState(
            this,
            SearchBridgeState::searchList,
            SearchBridgeState::showExpandAll
        ) { list, showExpand ->
            // 单独对历史搜索记录做局部刷新
            listAdapter.submitList(list){
                binding.tvExpandAll.visibility = if (showExpand) View.VISIBLE else View.GONE
            }
            if (list.isNullOrEmpty()) {
                binding.flHistorySearch.setGone()
            }
        }
        viewModel.eventFlow.collectEvent(this) {
            when(it) {
                SearchBridgeEvent.ShowClearAllDialog -> {
                    showDeleteDialog(it, "确定删除全部历史记录吗？")
                }
                SearchBridgeEvent.ShowUnsubscribeAndClearAllDialog -> {
                    showDeleteDialog(it, "确定取消订阅并删除全部历史记录吗？")
                }
                is SearchBridgeEvent.ShowUnsubscribeAndClearDialog -> {
                    showDeleteDialog(it, "确定取消订阅并删除记录吗？")
                }
                is SearchBridgeEvent.ClearSingleHistory -> {
                    viewModel.clearHistoryData(it)
                }
            }
        }
    }

    private fun updateRecommendJobView(data: SearchRecommendJobSate?) {
        binding.tvRecommendJobTitle.text = data?.title
        val tagList = data?.itemList
        val tagAdapter = object : TagAdapterOrigin<SearchRecommendJobItemState>(tagList) {
            override fun getView(
                parent: FlowLayoutOrigin?,
                position: Int,
                state: SearchRecommendJobItemState?
            ): View {
                val tagView =
                    LayoutInflater.from(curContext).inflate(R.layout.position_search_recommend_tag, parent, false) as TextView
                tagView.run {
                    text = state?.name
                    background = Bovb.with().color(getColor(R.color.C_S2)).radius(6.dp.toFloat()).build()

                }
                return tagView
            }
        }
        binding.layoutFlow.adapter = tagAdapter
        binding.layoutFlow.setOnTagClickListener { view, position, parent ->
            // TODO 点击标签
            true
        }
    }

    private fun updateKeyWordListView(list: List<String>?) {
        if (!list.isNullOrEmpty()) {
            binding.rvFloat.setVisible()
            floatAdapter.submitList(list)
        }
    }

    /**
     * 展示删除历史记录弹窗
     */
    private fun showDeleteDialog(type: SearchBridgeEvent, content: String) {
        BoDialog.commonCenterDialog()
            .setContent(content)
            .setPositiveButton("确定", object : OnViewClickListener() {
                override fun onClick(v: View?) {
                    viewModel.clearHistoryData(type)
                }
            })
            .setNegativeButton("取消", object : OnViewClickListener() { })
            .show(supportFragmentManager)
    }

}