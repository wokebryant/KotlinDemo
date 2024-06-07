package com.example.kotlindemo.task.afterdelivery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
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
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.common.StatusBarUtils
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

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
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            super.onSlide(bottomSheet, slideOffset)
            binding.motionContainer.progress = slideOffset
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 设置弹窗属性
        setDialogParams()
        // 设置列表
        setRecyclerView()
        // 动态设置投递区域 TopMargin
        setDeliveryBottomMargin()

        // 收集数据
        collect()
        // 请求数据
        request()
        // 监听
        listener()
    }

    private fun setDialogParams() {
        outsideClickClose = true
        isDraggable = true
        isHideable = false
        initState = BottomSheetBehavior.STATE_COLLAPSED
        peekHeight = 575.dp
    }

    private fun setRecyclerView() {
        binding.rvList.run {
            adapter = listAdapter
            itemAnimator = SlideInUpAnimator().apply {
                addDuration = 300
            }
        }
    }

    private fun setDeliveryBottomMargin() {
        val screenHeight = SizeUtils.getMetricsFull().heightPixels
        val statusBarHeight = StatusBarUtils.getStatusBarHeight()
        val topMargin = screenHeight - 56.dp - statusBarHeight
        val endConstraintSet = binding.motionContainer.getConstraintSet(R.id.end)
        endConstraintSet?.setMargin(R.id.fl_bottom, ConstraintSet.TOP, topMargin)
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


