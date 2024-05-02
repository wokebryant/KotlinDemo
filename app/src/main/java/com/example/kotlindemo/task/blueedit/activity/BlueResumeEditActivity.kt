package com.example.kotlindemo.task.blueedit.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.size
import androidx.core.view.updateLayoutParams
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityBlueResumeEditBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.study.mvi.core.collectStateLast
import com.example.kotlindemo.task.blueedit.extend.showNext
import com.example.kotlindemo.task.blueedit.fragment.BlueResumeSecondLevelQAFragment
import com.example.kotlindemo.task.blueedit.fragment.BlueResumeThirdLevelQAFragment
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState
import com.example.kotlindemo.task.blueedit.model.BlueEditState
import com.example.kotlindemo.task.blueedit.viewmodel.BlueResumeActivityEditViewModel
import com.example.kotlindemo.task.microenterprises.view.home.ViewPager2Adapter2
import com.example.kotlindemo.task.microenterprises.view.home.ZLFragment
import com.zhaopin.social.appcommon.c.OsUtils
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 蓝领简历编辑页面
 * @Author LuoJia
 * @Date 2024/04/26
 */
class BlueResumeEditActivity : BaseActivity() {

    private val binding: ActivityBlueResumeEditBinding by binding()

    private val viewModel: BlueResumeActivityEditViewModel by viewModels()

    /** ViewPager2容器 */
    private val viewPager2Adapter2 by lazy { ViewPager2Adapter2(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        initBar()
        setViewPager()

        setListener()
        collect()

        request()
    }

    private fun initBar() {
        binding.llHeader.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = OsUtils.getStatusBarHeight()
        }
    }

    private fun setViewPager() {
        binding.vpBlue.run {
            adapter = viewPager2Adapter2
            isUserInputEnabled = false
            registerOnPageChangeCallback(onPageChangeCallback)
        }
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            viewModel.curPageIndex = position
        }
    }

    private fun setListener() {
        binding.flBottom.onClick {
            showNext()
        }
        binding.flBack.onClick {
            finish()
        }
    }

    private fun collect() {
        viewModel.stateFlow.collectStateLast(this) {
            // 更新进度
            binding.resumeProgress.progress = it.progress
            // 按钮状态
            binding.flBottom.visibility = if (it.showBottomButton) View.VISIBLE else View.GONE
            binding.tvBottom.text = it.bottomBtnContent
            binding.flBottom.isEnabled = it.bottomBtnEnable
        }
        viewModel.stateFlow.collectStateLast(this, BlueEditState::list) {
            updateViewPager2(it)
        }
        viewModel.eventFlow.collectEvent(this) {
            when(it) {
                is BlueEditEvent.ShowNextPage -> {
                    showNext()
                }

                else -> {}
            }
        }
    }

    private fun updateViewPager2(pageList: List<Any>) {
        val fragmentList = mutableListOf<ZLFragment>()
        pageList.forEachIndexed { index, pageState ->
            when ((pageState as? BlueEditPageState)?.pageLevel ?: 2) {
                2 -> {
                    val fragment = {
                        BlueResumeSecondLevelQAFragment(position = index, pageState = pageState as BlueEditPageState)
                    }
                    fragmentList.add(fragment)
                }

                3 -> {
                    val fragment = {
                        BlueResumeThirdLevelQAFragment(position = index, pageState = pageState as BlueEditPageState)
                    }
                    fragmentList.add(fragment)
                }
            }
        }

        viewPager2Adapter2.setList(fragmentList)
    }

    private fun showNext() {
        val curItemIndex = binding.vpBlue.currentItem
        val isLastItem = curItemIndex == viewPager2Adapter2.itemCount - 1
        if (isLastItem) {
            viewModel.uploadSaveAnswer()
        } else {
            binding.vpBlue.showNext()
            viewModel.updateProgress()
        }
    }

    private fun request() {
        viewModel.requestQAData()
    }

}