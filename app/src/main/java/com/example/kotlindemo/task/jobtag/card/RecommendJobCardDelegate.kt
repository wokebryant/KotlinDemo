package com.example.kotlindemo.task.jobtag.card

import android.content.Context
import com.example.kotlindemo.databinding.HomeIndexItemRecommendJobCardBinding
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.copyOf
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位推荐卡片不折叠
 * @Author LuoJia
 * @Date 2023/9/15
 */
class RecommendJobCardDelegate : BindingViewDelegate<RecommendJobCardState, HomeIndexItemRecommendJobCardBinding>() {

    override fun onBindViewHolder(
        binding: HomeIndexItemRecommendJobCardBinding,
        item: RecommendJobCardState,
        position: Int
    ) {
        with(binding) {
            tvTitle.text = item.title
            ivClose.onClick {

            }
            layoutGird.setData(item.cardList, canSelect = false)
            layoutGird.onTagClickCallback = { itemIndex, tagIndex ->
                AppUtil.startActivity<RecommendJobActivity>(currentActivity() as Context) {
                    item.cardList?.reset()
                    val extraData = LocalJobIntentData(
                        itemIndex = itemIndex,
                        tagIndex = tagIndex,
                        itemList = item.cardList?.apply {
                            // 将选中的tag标记为选中状态
                            getOrNull(itemIndex)?.tagList?.getOrNull(tagIndex)?.selected = true
                        }
                    )
                    putExtra("jobData", extraData)
                }
            }
        }
    }

    /**
     * 重置标签列表数据选中态
     */
    private fun List<RecommendJobCardItem>.reset() {
        forEach { item ->
            item.tagList?.forEach { tag ->
                tag.selected = false
            }
        }
    }

}