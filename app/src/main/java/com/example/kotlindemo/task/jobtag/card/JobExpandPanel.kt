package com.example.kotlindemo.task.jobtag.card

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutJobExpandPanelBinding
import com.example.kotlindemo.utils.binding
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 职位标签展开面板View
 * @Author LuoJia
 * @Date 2023/9/19
 */
@SuppressLint("ClickableViewAccessibility")
class JobExpandPanel @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr) {

    private val binding: LayoutJobExpandPanelBinding by binding()
    private var itemList: List<RecommendJobCardItem>? = null

    /** 点击标签刷新回调 */
    var refreshCallback: ((ClickInfo) -> Unit)? = null

    init {
        // 标签面板背景
        binding.llContainer.run {
            background =
                Bovb.with()
                    .bottomLeftRadius(16.dp.toFloat())
                    .bottomRightRadius(16.dp.toFloat())
                    .color(getColor(R.color.C_W1))
                    .build()
        }
        // 蒙层点击事件（MotionLayout拦截了Click事件，需要在Touch事件中给处理）
        binding.viewSpace.setOnTouchListener { _, event ->
            if (binding.motionContainer.progress == 1f && event.action == MotionEvent.ACTION_DOWN) {
                collapse()
                return@setOnTouchListener true
            }
            false
        }
        // 标签点击回调
        binding.layoutGird.run {
            onTagClickCallback = { itemIndex, tagIndex ->
                // 更新选中数据
                itemList?.updateSelected(itemIndex, tagIndex)
                postDelayed( { collapse() }, 50)
                val clickInfo = ClickInfo(
                    style = itemList?.getOrNull(itemIndex)?.style ?: "",
                    code = itemList?.getOrNull(itemIndex)?.tagList?.getOrNull(tagIndex)?.code ?: "",
                    itemIndex = itemIndex,
                    tagIndex = tagIndex
                )
                refreshCallback?.invoke(clickInfo)
            }
        }
    }

    /**
     * 展开面板
     */
    fun expand(list: List<RecommendJobCardItem>?) {
        itemList = list
        binding.layoutGird.setData(list)
        binding.motionContainer.transitionToEnd()
    }

    /**
     * 收起面板
     */
    private fun collapse() {
        binding.motionContainer.transitionToStart()
    }

}