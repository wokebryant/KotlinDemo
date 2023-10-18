package com.example.kotlindemo.task.linkage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.widget.flowlayout.FlowLayoutOrigin1
import com.example.kotlindemo.widget.flowlayout.TagAdapterOrigin1
import com.example.kotlindemo.widget.flowlayout.TagFlowLayoutOrigin1
import com.example.kotlindemo.utils.setVisible
import com.example.kotlindemo.widget.flowlayout.TagState
import com.zhaopin.common.widget.linkage.adapter.viewholder.LinkageSecondaryHeaderViewHolder
import com.zhaopin.common.widget.linkage.adapter.viewholder.LinkageSecondaryViewHolder
import com.zhaopin.common.widget.linkage.bean.BaseGroupedItem
import com.zhaopin.common.widget.linkage.contract.ILinkageSecondaryAdapterConfig
import com.zhaopin.common.widget.seekbar.OnRangeChangedListener
import com.zhaopin.common.widget.seekbar.RangeSeekBar
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast
import kotlin.math.max

/**
 * @Description 职位二级筛选列表适配器配置（右侧）
 * @Author LuoJia
 * @Date 2023/7/3
 */
class SecondaryAdapterConfig(
    private val selectedCallback: (Set<LinkageChildItem>, TagState) -> Unit,
    private val onAllClickListener: (List<LinkageChildItem>) -> Unit
) : ILinkageSecondaryAdapterConfig<LinkageGroupItem.ItemInfo> {

    private var context: Context? = null
    private var sliderTag: LinkageChildItem?= null

    override fun setContext(context: Context?) {
        this.context = context
    }

    override fun getLinearLayoutId() = R.layout.item_linkage_secondary

    override fun getHeaderLayoutId() = R.layout.item_linkage_secondary_header

    override fun getHeaderTextViewId() = R.id.secondaryHeader

    override fun getFooterLayoutId() = R.layout.item_linkage_secondary_footer

    override fun onBindHeaderViewHolder(
        holder: LinkageSecondaryHeaderViewHolder?,
        item: BaseGroupedItem<LinkageGroupItem.ItemInfo>
    ) {
        holder?.getView<TextView>(R.id.secondaryHeader)?.text = item.header
        holder?.getView<TextView>(R.id.tvAll)?.visibility = if ("行业" == item.header) View.VISIBLE else View.GONE
        holder?.getView<TextView>(R.id.tvAll)?.onClick {
            onAllClickListener.invoke(mutableListOf())
        }
    }

    override fun onBindViewHolder(
        holder: LinkageSecondaryViewHolder,
        item: BaseGroupedItem<LinkageGroupItem.ItemInfo>
    ) {
        val tagLayout = holder.getView<TagFlowLayoutOrigin1>(R.id.tagLayout)
        val seekBar = holder.getView<RangeSeekBar>(R.id.seekBar2)
        val salaryView = holder.getView<TextView>(R.id.tvSeekProgress)
        val sliderList = item.info.linkageItem?.slider

        initTagLayout(tagLayout, item.info.linkageItem) {
            handleSeekBarWhenTagClick(it, tagLayout, seekBar, item.info.linkageItem!!, sliderList)
        }
        initSeekBar(seekBar, tagLayout ,salaryView, sliderList)
    }

    /**
     * 做局部刷新
     */
    override fun onBindViewHolder(
        holder: LinkageSecondaryViewHolder,
        item: BaseGroupedItem<LinkageGroupItem.ItemInfo>,
        payloads: MutableList<Any>
    ) {
        val tagLayout = holder.getView<TagFlowLayoutOrigin1>(R.id.tagLayout)
        val seekBar = holder.getView<RangeSeekBar>(R.id.seekBar2)

        val payload = payloads[0] as? Pair<*, *>
        payload?.let {
            when(it.first) {
                // 取消选中
                "remove" -> {
                    onTagRemove(tagLayout, it.second)
                    seekBar.reset()
                }
                // 添加
                "add" -> {
                    onTagAdd(tagLayout, it.second, item)
                }
                // 清除所有选中
                "clear" -> {
                    tagLayout.clearAllSelected(0)
                    seekBar.reset()
                }
                else -> {}
            }
        }
    }

    /**
     * Tag删除
     */
    private fun onTagRemove(tagLayout: TagFlowLayoutOrigin1, position: Any?) {
        if (position is Int) {
            tagLayout.doSelect(position)
            if (tagLayout.selectedList.isEmpty()) {
                tagLayout.doSelect(0)
            }
        } else if (position is MutableSet<*>) {
            position.forEach {
                tagLayout.doSelect(it as Int)
            }
            if (tagLayout.selectedList.isEmpty()) {
                tagLayout.doSelect(0)
            }
        }
    }

    /**
     * Tag添加
     */
    private fun onTagAdd(
        tagLayout: TagFlowLayoutOrigin1,
        tagSelectedIndexList: Any?,
        item: BaseGroupedItem<LinkageGroupItem.ItemInfo>
    ) {
        (tagSelectedIndexList as? List<*>)?.let {
            val linkageItem = item.info.linkageItem
            val indexList = tagSelectedIndexList.map { it as Int }
            initTagLayout(tagLayout, linkageItem, indexList.toSet())
        }
    }

    /**
     * 初始化TagLayout
     */
    private fun initTagLayout(
        tagLayout: TagFlowLayoutOrigin1,
        linkageItem: LinkageItem?,
        selectList: Set<Int> = mutableSetOf(0),
        tagClick: ((Int) -> Unit)? = null
    ) {
        if (linkageItem?.list.isNullOrEmpty()) return
        val title = linkageItem!!.title
        val multi = linkageItem.multiple
        val tagList = linkageItem.list
        val tagStringList = tagList.map { it.name }

        // 设置适配器
        val tagAdapter = object : TagAdapterOrigin1<String>(tagStringList) {
            override fun getView(parent: FlowLayoutOrigin1, position: Int, str: Any): View {
                val tagView = LayoutInflater.from(context)
                    .inflate(R.layout.item_linkage_secondary_tag, parent, false) as TextView
                tagView.run {
                    text = str as String
                    post {
                        updateLayoutParams<ViewGroup.LayoutParams> {
                            width = (tagLayout.width - 24.dp) / 2
                        }
                    }
                }

                return tagView
            }
        }
        // 设置TagLayout
        tagLayout.run {
            setSelectedTextBold()
            adapter = tagAdapter
            setSelectedMode(title, multi)
            adapter.setSelectedList(selectList)
            setOnTagClickListener { _, position, _ ->
                onTagClick(tagLayout, position, title, tagList, tagClick)
                return@setOnTagClickListener true
            }
            setOnSelectStateChangeListener { state, position, prePosition ->
                onTagStateChange(state, mutableSetOf(position, prePosition), tagList)
            }
        }
    }

    /**
     * Tag点击
     */
    private fun onTagClick(
        tagLayout: TagFlowLayoutOrigin1,
        position: Int,
        group: String,
        tagList: List<LinkageChildItem>,
        tagClick: ((Int) -> Unit)? = null
    ) {
        val isFirstTagClick = position == 0
        if (isFirstTagClick) {
            // 点击不限/全部，删除所有已经选中的选项
            val selectedSet = tagLayout.clearAllSelected(position)
            if (selectedSet.isNotEmpty()) {
                onTagStateChange(TagState.Remove, selectedSet, tagList)
            }
        } else {
            // 如果不限/全部已被选中，则清除不限/全部
            if (tagLayout.isFirstTagSelected()) {
                tagLayout.clearAllSelected(position)
            }
        }
        // 所有选项取消，则选中不限/全部
        if (tagLayout.selectedList.isEmpty()) {
            tagLayout.doSelect(0)
        }
        // 如果存在Slider Tag，删除
        if (sliderTag != null) {
            selectedCallback.invoke(mutableSetOf(sliderTag!!), TagState.Remove)
        }
        // 当前Item是行业，如果超过3个就弹Toast
        if (tagLayout.selectedList.size >= 3 && group == "行业" && !tagLayout.selectedList.contains(position)) {
            context?.showToast("最多选3个")
        }

        tagClick?.invoke(position)
    }

    /**
     * TagLayout状态变更
     */
    private fun onTagStateChange(state: TagState, selectedSet: Set<Int>, tagList: List<LinkageChildItem>) {
        // 获取当前操作(选中/删除)发生改变的Tag列表
        val changeSet = mutableSetOf<LinkageChildItem>()
        selectedSet.forEach {
            if (it < 0) return@forEach
            changeSet.add(tagList[it])
        }
        selectedCallback.invoke(changeSet, state)
    }

    /**
     * 初始化SeekBar
     */
    private fun initSeekBar(
        seekBar: RangeSeekBar,
        tagLayout: TagFlowLayoutOrigin1,
        salaryView: TextView,
        slider: List<LinkageSlider>?,
    ) {
        if (slider.isNullOrEmpty()) {
            seekBar.setGone()
            return
        }
        salaryView.run {
            setVisible()
            text = "不限"
        }
        seekBar.run {
            setVisible()
            val maxValue = slider.size.toFloat()
            seekBarMode = RangeSeekBar.SEEKBAR_MODE_RANGE
            setRange(0f, maxValue, 1f)
            setProgress(0f, maxValue)
            setOnRangeChangedListener(object : OnRangeChangedListener {
                override fun onRangeChanged(
                    view: RangeSeekBar?,
                    leftValue: Float,
                    rightValue: Float,
                    isFromUser: Boolean
                ) {
                    // 更新工资范围文案
                    val realLeftValue = max(leftValue.toInt(), 1)
                    var realRightValue = minOf(rightValue.toInt(), maxValue.toInt() - 1)
                    // 极端情况
                    if (realRightValue == realLeftValue) {
                        realRightValue = realLeftValue + 1
                    }
                    realRightValue = minOf(realRightValue, maxValue.toInt() - 1)
                    val leftSalary = slider[realLeftValue].name
                    val rightSalary = slider[realRightValue].name
                    val salaryText = when {
                        leftValue <= 0 && rightValue >= maxValue -> "不限"
                        leftValue <= 0 && rightValue < maxValue -> "${rightSalary}以下"
                        leftValue > 0 && rightValue >= maxValue -> "${leftSalary}以上"
                        else -> "${leftSalary}-${rightSalary}"
                    }
                    salaryView.text = salaryText
                    // 只有手动拖动SeekBar才更新Tag
                    if (isFromUser) {
                        // 获取当前生成的Tag
                        val code = if (leftValue == seekBar.minProgress && rightValue == seekBar.maxProgress) "-1"
                                   else slider[realLeftValue].code + slider[realRightValue].code

                        val currentSliderTag = LinkageChildItem(
                            code = code,
                            name = salaryText,
                            parentType = "salary_for_search"
                        )
                        sliderTag = currentSliderTag

                        val changeSet = mutableSetOf(currentSliderTag)
                        selectedCallback.invoke(changeSet, TagState.Slider)

                        // 清除已选中的工资Tag
                        if (leftValue == seekBar.minProgress && rightValue == seekBar.maxProgress) {
                            tagLayout.clearAllSelected(0)
                        } else {
                            if (tagLayout.selectedList.isNotEmpty()) {
                                tagLayout.clearAllSelected(-1)
                            }
                        }
                    }
                }

                override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) { }

                override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) { }

            })
        }
    }

    /**
     * Tag点击时更新SeekBar
     */
    private fun handleSeekBarWhenTagClick(
        position: Int,
        tagLayout: TagFlowLayoutOrigin1,
        seekBar: RangeSeekBar,
        linkageItem: LinkageItem,
        sliderList: List<LinkageSlider>?
    ) {
        if (sliderList.isNullOrEmpty()) {
            return
        }
        if (position == 0 || tagLayout.isFirstTagSelected()) {
            seekBar.setProgress(0f, sliderList.size.toFloat())
        } else {
            // 获取当前工资Item对应的Slider数据
            val currentSelectTagCode = linkageItem.list[position].code
            val startCode = currentSelectTagCode.substring(0, 6)
            val endCode = currentSelectTagCode.substring(6, 12)
            val startValue = getSeekbarValueByCode(startCode, sliderList)
            var endValue = getSeekbarValueByCode(endCode, sliderList)
            if (endValue == sliderList.size - 1) {
                endValue = sliderList.size
            }
            seekBar.setProgress(startValue.toFloat(), endValue.toFloat())
        }
    }

    /**
     * 根据工资Code获取对应的SeekBar值
     */
    private fun getSeekbarValueByCode(code: String, sliderList: List<LinkageSlider>): Int {
        var value = 0
        sliderList.indices.forEach {
            if (sliderList[it].code == code) {
                value = it
            }
        }
        return value
    }

}

internal fun TagFlowLayoutOrigin1.setSelectedMode(group: String, multi: Boolean) {
    if (multi) {
        if (group == "行业") {
            this.setMaxSelectCount(3)
        } else {
            this.setMaxSelectCount(-1)
        }
    } else {
        this.setMaxSelectCount(1)
    }
}

internal fun TagFlowLayoutOrigin1.isFirstTagSelected() =
    this.selectedList.isNotEmpty() && this.selectedList.contains(0)


internal fun RangeSeekBar.reset() =
    setProgress(this.minProgress, this.maxProgress)