package com.example.kotlindemo.task.microenterprises.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutResumeFiledInfoBinding
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 自定义年龄/薪资/工作城市等等
 * @Author LuoJia
 * @Date 2023/8/29
 */
class FieldListRecyclerView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RecyclerView(context, attr, defStyleAttr) {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(FieldViewDelegate())
        }
    }

    /** 内容颜色 */
    var fieldColor = R.color.C_B2
    /** 分割线颜色 */
    @ColorRes
    var divideLineColor = R.color.C_EEEEEE
    /** 分割线高度 */
    var divideLineHeight = 8.dp
    /** 分割线Margin */
    var divideLineMargin = 4.dp

    init {
        layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        adapter = listAdapter
    }

    fun setData(data: List<String>) {
        listAdapter.setList(data)
    }

    inner class FieldViewDelegate : BindingViewDelegate<String, LayoutResumeFiledInfoBinding>() {

        override fun onBindViewHolder(
            binding: LayoutResumeFiledInfoBinding,
            item: String,
            position: Int
        ) {
            binding.tvField.run {
                text = item
                setTextColor(getColor(fieldColor))
            }
            binding.vDivide.run {
                // 最后一个字段去掉分割线
                val isLastItem = position == listAdapter.items.size - 1
                visibility = if (isLastItem) View.GONE else View.VISIBLE
                setBackgroundColor(getColor(divideLineColor))
                updateLayoutParams<MarginLayoutParams> {
                    marginStart = divideLineMargin
                    marginEnd = divideLineMargin
                }
                updateLayoutParams<ViewGroup.LayoutParams> {
                    height = divideLineHeight
                }
            }
        }
    }

}