package com.example.kotlindemo.task.ai

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityAiRecommendBinding
import com.example.kotlindemo.databinding.LayoutAiRecommendTipBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectStateLast
import com.example.kotlindemo.task.ai.util.AiRecommendManager
import com.example.kotlindemo.task.ai.util.AiRecommendSnapHelper
import com.example.kotlindemo.task.ai.util.RecyclerViewPageChangeListenerHelper
import com.example.kotlindemo.task.ai.util.addAiRecommendItem
import com.example.kotlindemo.task.ai.util.registerAiRecommendList
import com.example.kotlindemo.task.ai.util.removeAiRecommendItem
import com.example.kotlindemo.task.ai.util.updateAiRecommendItem
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast

/**
 * @Description Ai求职推荐页面
 * @Author LuoJia
 * @Date 2024/1/9
 */
class AiRecommendActivity : BaseActivity(), IAiRecommendCallback {

    private val binding: ActivityAiRecommendBinding by binding()

    private val viewModel: AiRecommendViewModel by viewModels()

    /** 列表适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            registerAiRecommendList(this@AiRecommendActivity)
        }
    }
    /** 列表滑动工具类 */
    private var pageSnapHelper: AiRecommendSnapHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        collect()
        request()
    }

    private fun initView() {
        binding.layoutTopBar.setClickCallback(this)
        binding.inGuide.root.onClick { it.setGone() }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvList.run {
            pageSnapHelper = AiRecommendSnapHelper()
            pageSnapHelper?.attachToRecyclerView(this)
            scrollListener.setSnapHelper(pageSnapHelper)
            adapter = listAdapter
            itemAnimator = null
            addOnScrollListener(scrollListener)
        }
    }

    private val scrollListener =
        RecyclerViewPageChangeListenerHelper(object : RecyclerViewPageChangeListenerHelper.OnPageChangeListener {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView?,
                newState: Int,
                position: Int
            ) { }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) { }

            override fun onPageSelected(position: Int) { }
        })

    /**
     * 收集数据
     */
    private fun collect() {
        viewModel.stateFlow.collectStateLast(this) {
            updateList(it.list)
        }
        viewModel.eventFlow.collectEvent(this) {
            when (it) {
                is AiRecommendEvent.AddItem -> listAdapter.addAiRecommendItem(it.type, it.index, it.item)
                is AiRecommendEvent.RemoveItem -> listAdapter.removeAiRecommendItem(it.type, it.index, it.item)
                is AiRecommendEvent.UpdateItem -> listAdapter.updateAiRecommendItem(it.type, it.index, it.item)
                is AiRecommendEvent.MoveToNext -> moveToNext()
            }
        }
    }

    /**
     * 请求数据
     */
    private fun request() {
        viewModel.requestJobListData()
    }

    private fun updateList(list: List<Any>) {
        if (list.isNotEmpty()) {
            listAdapter.setList(list)
        }
    }

    /**
     * 点击更多
     */
    override fun onMoreClick(anchor: View) {
        val binding = LayoutAiRecommendTipBinding.inflate(
            LayoutInflater.from(this), null, false
        )
        val popupWindow = PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        ).apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isTouchable = true
            isOutsideTouchable = true
        }
        popupWindow.showAsDropDown(anchor)
        binding.llFeedback.onClick {
            showToast("点击反馈建议")
        }
        binding.llUserProtocol.onClick {
            showToast("点击用户协议")
        }
    }

    /**
     * 打招呼
     */
    override fun onGreetClick() {
        showToast("打招呼点击")
        moveToNext()
    }

    /**
     * 负反馈
     */
    override fun onFeedbackClick(position: Int, item: AiJobState) {
        viewModel.removeJDCard(position, item)
        if (position != 0) {
            moveToNext()
        }
    }

    /**
     * 切换下一张卡片
     */
    private fun moveToNext() {
        val cardHeight = AiRecommendManager.getCardHeight() + 10.dp
        binding.rvList.smoothScrollBy(0, cardHeight)
    }

}