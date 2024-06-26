package com.example.kotlindemo.task.afterdelivery

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutAfterDeliveryBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.utils.SizeUtils
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.example.kotlindemo.widget.BaseBottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.zhaopin.common.widget.loading.LoadingDialog
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.ItemViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.common.StatusBarUtils
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位投后弹窗
 * @Author LuoJia
 * @Date 2024/06/05
 */
class AfterDeliveryDialog : BaseBottomSheetDialogFragment<LayoutAfterDeliveryBinding>(){

    companion object {
        fun newInstance() = AfterDeliveryDialog()
    }

    private val viewModel: AfterDeliveryViewModel by viewModels()

    /** 职位卡列表适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter(
        ).apply {
            register(AfterDeliveryItemDelegate())
        }
    }

    private var isInTop = false

    /** 弹窗滑动监听 */
    val bottomSheetDialogListener = object : BottomSheetDialogListener {
        override fun dismiss() { viewModel.removeCallbacksAndMessages() }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            super.onStateChanged(bottomSheet, newState)
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                updateDraggable(enable = false)
                isInTop = true
                checkItemVisible()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            super.onSlide(bottomSheet, slideOffset)
            binding.motionContainer.progress = slideOffset
        }
    }

    private val initHeight: Int
        get() {
            val screenHeight = SizeUtils.getMetricsFull().heightPixels
            val navBarHeight = if (SizeUtils.isNavigationBarExist(currentActivity())) SizeUtils.getNavBarHeight(curContext) else 0
            val initHeight = screenHeight - navBarHeight - 200.dp
            return initHeight
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 设置弹窗属性
        setDialogParams()
        // 设置列表
        setRecyclerView()
        // 动态设置投递区域 TopMargin
        setDeliveryBottomMargin()
        setEmptyHeight()

        checkItemVisible()
        // 收集数据
        collect()
        // 请求数据
        request()
        // 监听
        listener()
    }

    private fun setDialogParams() {
        disableBackClose = false
        outsideClickClose = true
        isDraggable = true
        isHideable = false
        initState = BottomSheetBehavior.STATE_COLLAPSED
//        peekHeight = 575.dp
        peekHeight = initHeight

    }

    private fun setRecyclerView() {
        binding.rvList.run {
            adapter = listAdapter
            itemAnimator = SlideInUpAnimator2().apply {
                addDuration = 400
            }
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                        checkItemVisible()
                    }
                }
            })
        }
    }

    private fun setDeliveryBottomMargin() {
        val screenHeight = SizeUtils.getMetricsFull().heightPixels
        val statusBarHeight = StatusBarUtils.getStatusBarHeight()
        val showNav = SizeUtils.isNavigationBarExist(currentActivity())
        val navBarHeight = if (showNav) SizeUtils.getNavBarHeight(curContext) else 0
        val topMarginEnd = screenHeight - 56.dp - statusBarHeight - navBarHeight

        val topMarginStart = initHeight - 56.dp
        val startConstraintSet = binding.motionContainer.getConstraintSet(R.id.start)
        startConstraintSet?.setMargin(R.id.fl_bottom, ConstraintSet.TOP, topMarginStart)
        binding.motionContainer.updateState(R.id.start, startConstraintSet)


        val endConstraintSet = binding.motionContainer.getConstraintSet(R.id.end)
        endConstraintSet?.setMargin(R.id.fl_bottom, ConstraintSet.TOP, topMarginEnd)
        binding.motionContainer.updateState(R.id.end, endConstraintSet)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun collect() {
        viewModel.stateFlow.collectState(this) {
            updateBottom(it)
            updatePageState(it.pageState)
        }
        viewModel.eventFlow.collectEvent(this) {
            when (it) {
                is AfterDeliveryEvent.ResetCardList -> {
                    exposedSet.clear()
                    listAdapter.items.clear()
                    listAdapter.notifyDataSetChanged()
                }
                is AfterDeliveryEvent.AddCard -> {
                    listAdapter.addAfterDeliveryItem(it.item)
                }
                is AfterDeliveryEvent.UpdateItem -> {
                    listAdapter.updateAfterDeliveryItem(it.index, it.itemAfterDeliveryCardState)
                }
                is AfterDeliveryEvent.AllSelectedClick -> {
                    listAdapter.setList(it.jobList)
                }
                is AfterDeliveryEvent.StartExpose -> {
                    checkItemVisible()
                }
            }
        }
    }

    private val exposedSet = mutableSetOf<Int>()

    /**
     * 动画结束后检测ItemView在屏幕中的绝对坐标（RecycleView判断第一个最后一个显示Item的方法无效）
     */
    private fun checkItemVisible() {
        listAdapter.items.forEachIndexed { index, any ->
            val itemViewType = listAdapter.getItemViewType(index)
            val delegate =
                listAdapter.types.getType<Any>(itemViewType).delegate as ItemViewDelegate<*, *>
            if (delegate is AfterDeliveryItemDelegate) {
                val view = binding.rvList.layoutManager?.findViewByPosition(index)
                view?.let {
                    val rect = Rect()
                    val isRectInScreen = view.getGlobalVisibleRect(rect)

                    // 屏幕高度
                    val screenHeight = SizeUtils.getMetricsFull().heightPixels
                    // 全选布局高度
                    val allSelectedViewHeight = 56.dp
                    // 导航栏高度
                    val showNav = SizeUtils.isNavigationBarExist(currentActivity()!!)
                    val navBarHeight = if (showNav) SizeUtils.getNavBarHeight(curContext) else 0
                    // 获取RecyclerView在屏幕中实际的bottom值
                    val realRVBottom = screenHeight - allSelectedViewHeight - navBarHeight
                    // 拿卡片top和realRVBottom做比较
                    val isItemVisible = isRectInScreen && rect.top < realRVBottom

                    if (isItemVisible && !exposedSet.contains(index) && (any as? AfterDeliveryCardState)?.isPlaceholder == false) {
                        exposedSet.add(index)
                        Log.e("AfterDeliveryExpose: ", " isRectShow $index")
                    }
                }
            }
        }
    }

    private fun updateBottom(state: AfterDeliveryState) {
        binding.ivAllSelected.isSelected = state.allSelected
        binding.tvDelivery.text = state.deliveryBtnContent
        binding.tvDelivery.isEnabled = state.deliveryBtnEnable
    }

    private fun updatePageState(state: AfterDeliveryPageState) {
        when(state) {
            AfterDeliveryPageState.Loading -> currentActivity()?.let {
                LoadingDialog.show(it)
            }
            AfterDeliveryPageState.Content -> {
                binding.rvList.setVisible()
                binding.flBottom.setVisible()
                binding.inEmpty.root.setGone()
            }
            AfterDeliveryPageState.InitEmpty -> {
                binding.rvList.setGone()
                binding.inEmpty.root.setVisible()
                binding.inEmpty.tvContent.text = "暂无相似职位推荐"
                setBottomGone()
            }
            AfterDeliveryPageState.NextEmpty -> {
                binding.rvList.setGone()
                binding.inEmpty.root.setVisible()
                if (isInTop) {
                    val screenHeight = SizeUtils.getMetricsFull().heightPixels
                    val statusBarHeight = StatusBarUtils.getStatusBarHeight()
                    binding.inEmpty.root.updateLayoutParams<ViewGroup.LayoutParams> {
                        height = screenHeight - statusBarHeight - 148.dp
                    }
                }
                binding.inEmpty.tvContent.text = "该职位的相似推荐已看完"
                setBottomGone()
            }
        }
        if (state != AfterDeliveryPageState.Loading) {
            LoadingDialog.dismiss()
        }
    }

    private fun setBottomGone() {
        val endConstraintSet = binding.motionContainer.getConstraintSet(R.id.end)
        val startConstraintSet = binding.motionContainer.getConstraintSet(R.id.start)
        endConstraintSet?.setVisibility(R.id.fl_bottom, View.GONE)
        startConstraintSet?.setVisibility(R.id.fl_bottom, View.GONE)
        binding.motionContainer.updateState(R.id.start, startConstraintSet)
        binding.motionContainer.updateState(R.id.end, endConstraintSet)
    }

    private fun setEmptyHeight() {
        val emptyHeight = initHeight - 244.dp
        if (emptyHeight < 331.dp) {
            binding.inEmpty.root.updateLayoutParams<ViewGroup.LayoutParams> {
                height = emptyHeight
            }
            binding.inEmpty.root.updateLayoutParams<MarginLayoutParams> {
                bottomMargin = 10.dp
            }
            binding.inEmpty.ivImg.updateLayoutParams<ViewGroup.LayoutParams> {
                width = 128.dp
                height = 96.dp
            }
            binding.inEmpty.tvContent.textSize = 14f
            binding.inEmpty.tvContent.updateLayoutParams<MarginLayoutParams> {
                topMargin = 8.dp
            }
        }
    }

    private fun request() {
        viewModel.requestJobList()
    }

    private fun listener() {
        binding.ivClose.onClick {
            dismissAllowingStateLoss()
        }
        binding.ivAllSelected.onClick {
            viewModel.onAllSelectedClick()
        }
        binding.tvDelivery.onClick {
            viewModel.deliveryClick()
        }
    }

}


