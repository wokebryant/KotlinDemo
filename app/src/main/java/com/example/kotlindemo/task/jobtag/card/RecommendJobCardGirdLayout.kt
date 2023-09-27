package com.example.kotlindemo.task.jobtag.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutJobCardGirdBinding
import com.example.kotlindemo.databinding.LayoutJobCardGirdItemBinding
import com.example.kotlindemo.utils.binding
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext

/**
 * @Description 强准三期推荐标签通用布局
 * @Author LuoJia
 * @Date 2023/9/15
 */
class RecommendJobCardGirdLayout @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attr, defStyleAttr){

    private val binding: LayoutJobCardGirdBinding by binding()

    /** 标签点击回调 */
    var onTagClickCallback: ((Int, Int) -> Unit)? = null
    /** 上一次被选中的Item下标 */
    private var preSelectedItemIndex: Int = -1
    /** 是否允许选中态 */
    private var canSelect = true
    private val lisAdapter by lazy {
        MultiTypeAdapter().apply {
            register(RecommendJobCardItemDelegate())
        }
    }

    fun setData(list: List<RecommendJobCardItem>?, canSelect: Boolean = true) {
        this.canSelect = canSelect
        if (!list.isNullOrEmpty()) {
            binding.rvJobCard.adapter = lisAdapter
            binding.rvJobCard.setItemViewCacheSize(10)
            lisAdapter.setList(list)
        }
    }

    /**
     * 如果选中的标签没有推荐职位，将标签置灰
     */
    fun setTagDisable(itemIndex: Int, tagIndex: Int) {
        lisAdapter.notifyItemChanged(itemIndex, Pair("disable", tagIndex))
    }

    /**
     * 清除上次选中的Tag
     */
    private fun clearPreSelectedTag(curSelectedItemIndex: Int) {
        if (preSelectedItemIndex >= 0 && preSelectedItemIndex != curSelectedItemIndex) {
            lisAdapter.notifyItemChanged(preSelectedItemIndex, Pair("clear", ""))
        }
    }

    /**
     * 单条推荐标签列表
     */
    inner class RecommendJobCardItemDelegate : BindingViewDelegate<RecommendJobCardItem, LayoutJobCardGirdItemBinding>() {

        override fun onBindViewHolder(
            binding: LayoutJobCardGirdItemBinding,
            item: RecommendJobCardItem,
            position: Int
        ) {
            with(binding) {
                tvTitle.text = item.title
                initTagFlowLayout(position, layoutFlow, item.tagList)
            }
        }

        private fun initTagFlowLayout(itemIndex: Int, flowLayout: TagFlowLayoutOrigin, tagList: List<RecommendJobCardTag>?) {
            // 获取被选中的tag列表
            val selectedList = tagList?.getSelectedList()
            if (!selectedList.isNullOrEmpty()) {
                preSelectedItemIndex = itemIndex
            }
            // 获取置灰的tag列表
            val disableList = tagList?.getDisableList()
            val tagAdapter = object : TagAdapterOrigin<RecommendJobCardTag>(tagList) {
                override fun getView(
                    parent: FlowLayoutOrigin?,
                    position: Int,
                    t: RecommendJobCardTag?
                ): View {
                    val layout =
                        if (canSelect) R.layout.position_recommend_opt_tag else R.layout.position_recommend_opt_no_select_tag
                    val tagView = LayoutInflater.from(curContext).inflate(layout, parent, false) as TextView
                    tagView.text = t?.name
                    return tagView
                }
            }
            flowLayout.run {
                if (canSelect) {
                    setSelectedTextBold()
                    setOppositeSelect(false)
                }
                adapter = tagAdapter
                adapter.setSelectedList(selectedList)
                adapter.setDisableList(disableList)
                setOnTagClickListener { _, tagIndex, _ ->
                    clearPreSelectedTag(itemIndex)
                    preSelectedItemIndex = itemIndex
                    onTagClickCallback?.invoke(itemIndex, tagIndex)
                    true
                }
            }
        }

        /**
         * 做局部刷新
         */
        override fun onBindViewHolder(
            holder: BindingViewHolder<LayoutJobCardGirdItemBinding>,
            item: RecommendJobCardItem,
            position: Int,
            payloads: List<Any>
        ) {
            if (payloads.isNotEmpty()) {
                val payload = payloads[0] as? Pair<*, *>
                val flowLayout = holder.binding.layoutFlow
                payload?.let {
                    when (it.first) {
                        // 设置Tag置灰
                        "disable" -> {
                            flowLayout.setTagDisable(it.second as Int)
                        }
                        // 清除选中的Tag
                        "clear" -> {
                            flowLayout.selectedList.toList().getOrNull(0)?.let { position ->
                                flowLayout.doSelect(position)
                            }
                        }
                        else -> {}
                    }
                }
            } else {
                onBindViewHolder(holder.binding, item, position)
            }
        }
    }

    private fun List<RecommendJobCardTag>.getSelectedList(): Set<Int> {
        val set = mutableSetOf<Int>()
        forEachIndexed { index, tag ->
            if (tag.selected) {
                set.add(index)
            }
        }
        return set
    }

    private fun List<RecommendJobCardTag>.getDisableList(): Set<Int> {
        val set = mutableSetOf<Int>()
        forEachIndexed { index, tag ->
            if (!tag.enable) {
                set.add(index)
            }
        }
        return set
    }

}