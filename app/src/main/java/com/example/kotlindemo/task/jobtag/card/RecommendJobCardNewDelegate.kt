package com.example.kotlindemo.task.jobtag.card

import com.example.kotlindemo.databinding.HomeIndexItemRecommendOpt2Binding
import com.example.kotlindemo.task.jobtag.CardDirect
import com.example.kotlindemo.task.jobtag.JobRecommendCardState
import com.example.kotlindemo.task.jobtag.RelatedLabelDTO
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/4
 */
class RecommendJobCardNewDelegate : BindingViewDelegate<JobRecommendCardState, HomeIndexItemRecommendOpt2Binding>() {

    /** 筛选标签列表适配器 */
    private val filterTagListAdapter by lazy { JobFlowSelectAdapter() }

    private var item: JobRecommendCardState? = null
    private var binding: HomeIndexItemRecommendOpt2Binding? = null

    override fun onBindViewHolder(
        binding: HomeIndexItemRecommendOpt2Binding,
        item: JobRecommendCardState,
        position: Int
    ) {
        this.item = item
        this.binding = binding
        with(binding) {
            // 标题
            tvTitle.text = item.title
            // 标签数据
            rvFilterTag.run {
                adapter = filterTagListAdapter
                itemAnimator = null
            }
            filterTagListAdapter.list = item.tagList.toFlowUiList()
            // 职位卡数据
            when (item.type) {
                CardDirect.Horizontal -> {
                    llCardHorizontal.setVisible()
                    viewCardVertical.setGone()
                    viewHorizontalLeftItem.setData(item.jobList.getOrNull(0))
                    viewHorizontalRightItem.setData(item.jobList.getOrNull(1))
                }
                CardDirect.Vertical -> {
                    llCardHorizontal.setGone()
                    viewCardVertical.setVisible()
                    viewCardVertical.setData(item.jobList)
                }
            }
        }
    }

    private fun onTagCLick(tagIndex: Int, state: JobFlowLayoutUIState) {
        if (state.selected) {
            return
        }
        item?.tagList?.let {
            it.forEachIndexed { index, tag ->
                tag.selected = tagIndex == index && !tag.selected
                filterTagListAdapter.list = it.toFlowUiList()
            }
        }

    }

    private fun List<RelatedLabelDTO>.toFlowUiList() = this.map {
        JobFlowLayoutUIState(
            name = it.name,
            code = it.code,
            selected = it.selected,
            enable = true,
            type = TagUiType.SearchResult,
            itemClick = ::onTagCLick
        )
    }
}