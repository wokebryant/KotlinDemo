package com.example.kotlindemo.task.blueedit.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.databinding.FragmentBlueResumeQaBinding
import com.example.kotlindemo.study.mvi.core.collectEvent
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.study.mvi.core.collectStateLast
import com.example.kotlindemo.task.blueedit.adapter.BlueResumeEditTagAdapter
import com.example.kotlindemo.task.blueedit.model.BlueEditEvent
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState
import com.example.kotlindemo.task.blueedit.model.BlueResumeTagState
import com.example.kotlindemo.task.blueedit.viewmodel.BlueResumeActivityEditViewModel
import com.example.kotlindemo.task.blueedit.viewmodel.BlueResumeSecondLevelEditViewModel
import com.zhaopin.basedata.bean.SearchImagineSkillTag
import com.zhaopin.selectwidget.R
import com.zhaopin.selectwidget.activity.ZPWSKeyWordMatchActivity
import com.zhaopin.selectwidget.bean.ZPWSPositionKeyWordConfigBean
import com.zhaopin.selectwidget.bean.ZPWSStaticConfig
import com.zhaopin.selectwidget.config.ZPWSConfigBridge
import com.zhaopin.selectwidget.util.DialogUtil
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.toast.showToast

/**
 * @Description 蓝领通用问答Fragment (二级)
 * @Author LuoJia
 * @Date 2024/04/26
 */
class BlueResumeSecondLevelQAFragment(
    private val position: Int,
    private val pageState: BlueEditPageState,
) : BaseFragment<FragmentBlueResumeQaBinding>() {

    private val parentViewModel: BlueResumeActivityEditViewModel by activityViewModels()
    private val viewModel: BlueResumeSecondLevelEditViewModel by viewModels()

    private val tagAdapter by lazy { BlueResumeEditTagAdapter() }

    /**
     * 跳转自定义标签页面回调
     */
    private val customActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == AppCompatActivity.RESULT_OK) {
            val result = activityResult.data?.getSerializableExtra(ZPWSKeyWordMatchActivity.SEARCH_IMAGINE_SKILL_TAG)
                    as? SearchImagineSkillTag.SearchImagineSkillTagDataList
            viewModel.secondLevelTagAdd("新增标签")
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        listener()
        collect()

        // 初始化状态
        viewModel.initPageState(pageState)
    }

    private fun initView() {
        binding.rvTag.run {
            adapter = tagAdapter
            itemAnimator = null
        }
    }

    private fun listener() {
        // 点击标签
        tagAdapter.itemClick = { position, state ->
            viewModel.secondLevelTagSelect(position, state)
        }
        // 删除标签
        tagAdapter.onDeleteClick = { _, state ->
            viewModel.secondLevelTagDelete(state)
        }
    }

    private fun collect() {
        viewModel.stateFlow.collectStateLast(this) {
            // 更新标题
            binding.inTitle.tvTitle.text = pageState.title
            binding.inTitle.tvExtra.text = if (pageState.max > 1) "（多选）" else ""
            parentViewModel.updateBottom(it.hasSelected, pageState.must)
        }
        viewModel.stateFlow.collectStateLast(this, BlueEditPageState::itemList) {
            // 更新标签列表
            val tagList = it.first().list
            tagAdapter.submitList(tagList)
        }
        viewModel.eventFlow.collectEvent(this) {
            when(it) {
                is BlueEditEvent.JumpCustomTagPage -> {
                    jumpCustomTagPage()
                }
                is BlueEditEvent.ShowDeleteTagConfirmDialog -> {
                    showTagDeleteDialog(it.state)
                }
                is BlueEditEvent.SaveWorkDurationId -> {
                    parentViewModel.saveWorkDurationPageAnswer(it.id)
                }
                is BlueEditEvent.SaveCommonAnswer -> {
                    parentViewModel.saveCommonPageAnswer(position, it.answerList)
                }
                is BlueEditEvent.ShowNextPage -> {
                    parentViewModel.showNextPage()
                }
                is BlueEditEvent.ShowLimitToast -> {
                    currentActivity()?.showToast(it.content)
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
     * 跳转自定义标签页
     */
    private fun jumpCustomTagPage() {
        val intent = Intent(currentActivity(), ZPWSKeyWordMatchActivity::class.java).apply {
            putExtra(ZPWSKeyWordMatchActivity.EXTRA_JOB_TYPE, "")
            putExtra(ZPWSKeyWordMatchActivity.GROUP_ID, "")
            putExtra(ZPWSKeyWordMatchActivity.GROUP_TYPE, "")
        }
        ZPWSConfigBridge.getInstance().zpwsPositionKeyWordConfigBean = ZPWSPositionKeyWordConfigBean().apply {
            pageEnterDirection = ZPWSStaticConfig.PageEnterDirection.BOTTOM
        }
        customActivityResultLauncher.launch(intent)
    }

    /**
     * 展示删除标签弹窗
     */
    private fun showTagDeleteDialog(state: BlueResumeTagState) {
        currentActivity()?.let {
            val mTextString = "确认删除么？"
            val dialog = DialogUtil.newDialog(
                it,
                mTextString, "确认", "取消", object : DialogUtil.OnDialogListener {
                    override fun sureClick() {
                        viewModel.confirmDelete(state)
                    }

                    override fun cancleClick() {}
                }, ContextCompat.getColor(it, R.color.zpws_color_5b7be9),
                ContextCompat.getColor(it, R.color.zpws_color_222222)
            )
            runCatching { dialog?.show() }
                .onFailure {
                    currentActivity()?.finish()
                }
        }
    }

}