package com.example.kotlindemo.task.jobtag.card

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.databinding.LayoutJobRecommendEmptyBinding
import com.example.kotlindemo.study.mvi.MockDataStore
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible

/**
 * @Description 职位标签空页面
 * @Author LuoJia
 * @Date 2023/9/20
 */
class JobEmptyLayout  @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutJobRecommendEmptyBinding by binding()
    /** 点击标签刷新回调 */
    var refreshCallback: ((ClickInfo) -> Unit)? = null

    fun show(list: List<RecommendJobCardItem>?, clickInfo: ClickInfo) {
        list?.updateDisable(clickInfo.itemIndex, clickInfo.tagIndex)
        with(binding) {
            llRoot.setVisible()
            layoutGird.setData(list)
            layoutGird.onTagClickCallback = { itemIndex, tagIndex ->
                // 更新选中数据
                list?.updateSelected(itemIndex, tagIndex)
                val newClickInfo = ClickInfo(
                    style = list?.getOrNull(itemIndex)?.style ?: "",
                    code = list?.getOrNull(itemIndex)?.tagList?.getOrNull(tagIndex)?.code ?: "",
                    itemIndex = itemIndex,
                    tagIndex = tagIndex
                )
                refreshCallback?.invoke(newClickInfo)
            }
        }
    }

    fun hide() {
        binding.llRoot.setGone()
    }

}