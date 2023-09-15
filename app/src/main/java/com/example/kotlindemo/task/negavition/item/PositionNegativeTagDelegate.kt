package com.example.kotlindemo.task.negavition.item

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.PositionNegativeFeedbackTagItemBinding
import com.example.kotlindemo.task.negavition.FeedbackLabel
import com.example.kotlindemo.task.negavition.LabelItem
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位负反馈Item
 * @Author LuoJia
 * @Date 2023/8/11
 */
class PositionNegativeTagDelegate(
    private val clickCallback: ((String, String?) -> Unit)? = null
) : BindingViewDelegate<FeedbackLabel, PositionNegativeFeedbackTagItemBinding>() {

    override fun onBindViewHolder(
        binding: PositionNegativeFeedbackTagItemBinding,
        item: FeedbackLabel,
        position: Int
    ) {
        initTagFlowLayout(binding.layoutTagFlow, item)
        binding.tvSetting.visibility = if (item.style == "no") View.VISIBLE else View.GONE
        binding.tvSetting.onClick {
            openSettingPage()
        }
        binding.tvTitle.text = item.title
    }

    /**
     * 局部刷新
     */
    override fun onBindViewHolder(
        holder: BindingViewHolder<PositionNegativeFeedbackTagItemBinding>,
        item: FeedbackLabel,
        position: Int,
        payloads: List<Any>
    ) {
        if (payloads.isNotEmpty()) {
            val payLoad = payloads[0] as? String
            payLoad?.let {
                if (it == "clear") {
                    cancelSelected(holder.binding.layoutTagFlow)
                }
            }
        } else {
            onBindViewHolder(holder.binding, item, position)
        }
    }

    /**
     * 初始化标签
     */
    private fun initTagFlowLayout(flowLayout: TagFlowLayoutOrigin, item: FeedbackLabel) {
        val tagList = item.itemList
        // 设置适配器
        val tagAdapter = object : TagAdapterOrigin<LabelItem>(tagList) {
            @SuppressLint("CutPasteId")
            override fun getView(parent: FlowLayoutOrigin?, position: Int, item: LabelItem?): View {
                val tagView = LayoutInflater.from(curContext)
                    .inflate(R.layout.position_negative_feedback_tag_2, parent, false) as LinearLayout
                tagView.run {
                    post {
                        updateLayoutParams<ViewGroup.LayoutParams> {
                            width = (flowLayout.width - 32.dp) / 2
                        }

                        val (left, center, right) = getLabelName(item)
                        findViewById<TextView>(R.id.tv_left).text = left
                        findViewById<TextView>(R.id.tv_center).text = center
                        findViewById<TextView>(R.id.tv_right).text = right
                        // 如果中间TextView宽度大于文本长度，需要重新计算TextView宽度
                        findViewById<TextView>(R.id.tv_center).run {
                            post {
                                val centerTextPaintWidth = paint.measureText(center)
                                if (width > centerTextPaintWidth) {
                                    val offset = width - centerTextPaintWidth
                                    var padding = if (offset > 4.dp) 4.dp else 2.dp
                                    if (offset <= 1.dp) {
                                        padding = 1.dp
                                    }
                                    updateLayoutParams<LinearLayout.LayoutParams> {
                                        weight = 0f
                                        // 多2dp兼容粗体
                                        width = centerTextPaintWidth.toInt() + padding
                                    }
                                }
                            }
                        }
                    }

                }
                return tagView
            }

        }
        // 设置FlowLayout
        flowLayout.run {
            setSelectedTextBold()
            adapter = tagAdapter
            setOnTagClickListener { _, position , _ ->
                val selectedItemCode = if (selectedList.isNullOrEmpty()) null else item.itemList[position].code
                clickCallback?.invoke(item.style, selectedItemCode)
                true
            }
        }
    }

    /**
     * 获取标签名字
     */
    private fun getLabelName(item: LabelItem?): LabelName {
        if (item?.name?.contains("「{0}」") == false) {
            return LabelName()
        }
        // 标签位置在左边
        if (item?.name?.first().toString() == "「") {
            return LabelName(
                left = "「",
                center = "${item?.keyword}",
                right = "」" + item?.name?.replace("「{0}」", "")
            )
        } else if (item?.name?.last().toString() == "」") {
            // 标签位置在右边
            return LabelName(
                left = item?.name?.replace("「{0}」", "") + "「",
                center = "${item?.keyword}",
                right = "」"
            )
        } else {
            // 标签位置在中间
            return LabelName(
                left = item?.name?.substringBefore("「") + "「",
                center = "${item?.keyword}",
                right = "」" + item?.name?.substringAfter("」")
            )

        }
    }

    /**
     * 打开设置页面
     */
    private fun openSettingPage() {

    }

    /**
     * 取消选中
     */
    private fun cancelSelected(flowLayout: TagFlowLayoutOrigin) {
        flowLayout.selectedList.toList().getOrNull(0)?.let {
            flowLayout.doSelect(it)
        }
    }

    /**
     * 标签名
     */
    data class LabelName(
        val left: String = "",
        val center: String = "",
        val right: String = ""
    )

}