package com.example.kotlindemo.task.jobtag.card

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.databinding.LayoutJobHorizontalScrollBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位标签横向选择View
 * @Author LuoJia
 * @Date 2023/9/18
 */
class JobHorizontalScrollView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutJobHorizontalScrollBinding by binding()
    private val flowAdapter by lazy { JobFlowSelectAdapter() }

    private var item: RecommendJobCardItem? = null
    private var itemIndex = 0

    /** 展开按钮点击 */
    var onExpandClick: (() -> Unit)? = null
    /** 点击标签刷新回调 */
    var refreshCallback: ((ClickInfo) -> Unit)? = null

    init {
        binding.ivExpand.onClick {
            onExpandClick?.invoke()
        }
    }

    fun initData(data: RecommendJobCardItem?, itemIndex: Int, tagIndex: Int) {
        item = data
        this.itemIndex = itemIndex
        // 设置横向列表数据
        data?.let {
            binding.tvTitle.text = it.title
            it.tagList?.let { tagList ->
                if (tagList.size > 1)
                    binding.ivExpand.setVisible()
                else
                    binding.ivExpand.setGone()
                binding.rvFlow.adapter = flowAdapter
                binding.rvFlow.setHasFixedSize(true)
                binding.rvFlow.itemAnimator = null
                flowAdapter.list = tagList.toFlowUiState()
                binding.rvFlow.smoothScrollToPosition(tagIndex)
            }
        }
    }

    fun updateData(data: RecommendJobCardItem?, clickInfo: ClickInfo) {
        item = data
        this.itemIndex = clickInfo.itemIndex
        data?.let {
            binding.tvTitle.text = it.title
            it.tagList?.let { tagList ->
                flowAdapter.list = tagList.toFlowUiState()
                binding.rvFlow.smoothScrollToPosition(clickInfo.tagIndex)
            }
        }
    }

    private fun onTagClick(tagIndex: Int, data: JobFlowLayoutUIState) {
        if (data.selected) {
            return
        }
        val clickInfo = ClickInfo(
            style = item?.style ?: "",
            code = item?.tagList?.getOrNull(tagIndex)?.code ?: "",
            itemIndex = itemIndex,
            tagIndex = tagIndex
        )
        refreshCallback?.invoke(clickInfo)
        item?.tagList?.let {
            it.forEachIndexed { index, tag ->
                tag.selected = tagIndex == index && !tag.selected
                flowAdapter.list = it.toFlowUiState()
            }
        }
    }

    private fun List<RecommendJobCardTag>.toFlowUiState() = this.map {
        JobFlowLayoutUIState(
            name = it.name,
            selected = it.selected,
            enable = it.enable,
            itemClick = ::onTagClick
        )
    }

}