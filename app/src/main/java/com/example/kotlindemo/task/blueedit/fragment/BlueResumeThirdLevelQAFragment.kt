package com.example.kotlindemo.task.blueedit.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentBlueResumeThirdQaBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.study.mvi.core.collectStateLast
import com.example.kotlindemo.task.blueedit.adapter.delegate.BlueResumeEditDelegate
import com.example.kotlindemo.task.blueedit.inter.IBlueResumeCallback
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState
import com.example.kotlindemo.task.blueedit.viewmodel.BlueResumeActivityEditViewModel
import com.example.kotlindemo.task.blueedit.viewmodel.BlueResumeSecondLevelEditViewModel
import com.example.kotlindemo.task.blueedit.viewmodel.BlueResumeThirdLevelEditViewModel
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setItem
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.toast.showToast

/**
 * @Description 蓝领通用问答Fragment (三级)
 * @Author LuoJia
 * @Date 2024/04/28
 */
class BlueResumeThirdLevelQAFragment(
    private val position: Int,
    private val pageState: BlueEditPageState,
) : BaseFragment<FragmentBlueResumeThirdQaBinding>(), IBlueResumeCallback {

    private val parentViewModel: BlueResumeActivityEditViewModel by activityViewModels()
    private val viewModel: BlueResumeThirdLevelEditViewModel by viewModels()

    /** 三级问题列表适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(BlueResumeEditDelegate(this@BlueResumeThirdLevelQAFragment))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 初始化状态
        viewModel.initPageState(pageState)

        initView()
        collect()
    }

    private fun initView() {
        // 初始化RecyclerView
        binding.rvList.run {
            itemAnimator = null
            adapter = listAdapter
            setItemViewCacheSize(20)
        }
    }

    private fun collect() {
        viewModel.stateFlow.collectStateLast(this) {
            // 更新标题
            binding.inTitle.tvTitle.text = pageState.title
            binding.inTitle.tvExtra.text = if (pageState.max > 1) "（多选）" else ""
            // 更新底部按钮状态
            parentViewModel.updateBottom(it.hasSelected, pageState.must)
        }
        viewModel.stateFlow.collectStateLast(this, BlueEditPageState::itemList) {
            // 更新Item列表
            if (it.isNotEmpty()) {
                listAdapter.setList(it)
            }
        }
        viewModel.eventFlow.collectEvent(this) {
            when (it) {
                is BlueEditEvent.SaveCommonAnswer -> {
                    parentViewModel.saveCommonPageAnswer(position, it.answerList)
                }
                is BlueEditEvent.ShowLimitToast -> {
                    currentActivity()?.showToast(it.content)
                }
                is BlueEditEvent.UpdateThirdLevelItem -> {
                    listAdapter.setItem(it.position, it.item)
                }

                else -> {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 更新底部按钮状态
        parentViewModel.updateBottom(viewModel.hasSelected(), pageState.must)
    }

    /**
     * 标签点击
     */
    override fun onThirdLevelTagClick(
        itemPosition: Int,
        tagPosition: Int,
        selectedList: MutableSet<Int>,
        fromFoldItem: Boolean,
        isAdd: Boolean
    ) {
        viewModel.onTagClick(itemPosition, tagPosition, selectedList, fromFoldItem, isAdd)
    }

    /**
     * 展开更多点击
     */
    override fun onThirdLevelExpandClick(position: Int, selectedList: MutableSet<Int>) {
        viewModel.onExpandClick(position, selectedList)
    }

}