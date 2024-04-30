package com.example.kotlindemo.task.blueedit.adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.BlueResumeEditTagBinding
import com.example.kotlindemo.task.blueedit.model.BlueResumeTagState
import com.example.kotlindemo.widget.diffUtil.FlowMVXLayoutListAdapter
import com.zhaopin.common.widget.mvx.flowLayout.MVXTagUIState
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/26
 */
class BlueResumeEditTagAdapter : FlowMVXLayoutListAdapter<BlueResumeTagState, BlueResumeEditTagBinding>() {

    override fun getItemViewBinding(parent: ViewGroup) =
        BlueResumeEditTagBinding.inflate(LayoutInflater.from(parent.context))

    @SuppressLint("ResourceAsColor")
    override fun onBindView(
        binding: BlueResumeEditTagBinding,
        data: BlueResumeTagState,
        position: Int
    ) {
        when(data.type) {
            // 常规标签
            BlueTagType.Normal -> {
                binding.tvTag.setVisible()
                binding.llAdd.setGone()
                binding.llCustom.setGone()
                binding.rvExpand.setGone()

                binding.tvTag.text = data.name
                binding.tvTag.typeface = if (data.selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT

                binding.flRoot.setBackgroundResource(R.drawable.bg_blue_resume_edit_tag)
            }
            // 添加标签
            BlueTagType.Add -> {
                binding.llAdd.setVisible()
                binding.llCustom.setGone()
                binding.tvTag.setGone()
                binding.rvExpand.setGone()

                binding.flRoot.setBackgroundResource(0)
            }
            // 删除标签
            BlueTagType.Delete -> {
                binding.llCustom.setVisible()
                binding.tvTag.setGone()
                binding.llAdd.setGone()
                binding.rvExpand.setGone()

                binding.tvCustom.text = data.name
                binding.tvCustom.typeface = if (data.selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                binding.flRoot.setBackgroundResource(R.drawable.bg_blue_resume_edit_tag)
                binding.ivDelete.onClick {
                    onDeleteClick.invoke(position, data)
                }
                // 测量TextView宽度和文本宽度
                binding.tvCustom.post {
                    val textViewWidth = binding.tvCustom.width
                    val textContentWidth = binding.tvCustom.paint.measureText(data.name)
                    if (textViewWidth >= textContentWidth) {
                        binding.tvCustom.updateLayoutParams<LinearLayout.LayoutParams> {
                            width = textContentWidth.toInt()
                            weight = 0f
                        }
                    } else {
                        binding.tvCustom.updateLayoutParams<LinearLayout.LayoutParams> {
                            width = 0
                            weight = 1f
                        }
                    }
                }
            }
            // 展开更多
            BlueTagType.Expand -> {
                binding.rvExpand.setVisible()
                binding.tvTag.setGone()
                binding.llAdd.setGone()
                binding.llCustom.setGone()

                binding.flRoot.setBackgroundResource(0)
            }
        }
        binding.flRoot.isSelected = data.selected
    }

    /**
     * 标签删除
     */
    var onDeleteClick: EditTagDeleteClick = { _, _ -> }

}

/**
 * 蓝领编辑简历标签类型
 */
enum class BlueTagType {
    // 常规
    Normal,
    // 添加
    Add,
    // 自定义
    Delete,
    // 展开更多
    Expand
}

typealias EditTagDeleteClick = (position: Int, state: BlueResumeTagState) -> Unit
