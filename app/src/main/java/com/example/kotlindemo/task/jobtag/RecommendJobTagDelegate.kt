package com.example.kotlindemo.task.jobtag

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.HomeIndexItemRecommendOptBinding
import com.example.kotlindemo.databinding.PositionRecommendJobTagItemBinding
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位推荐卡片
 * @Author LuoJia
 * @Date 2023/8/15
 */
class RecommendJobTagDelegate : BindingViewDelegate<RecommendJobTagBean, HomeIndexItemRecommendOptBinding>() {

    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(RecommendJobTagItemDelegate())
        }
    }

    /** 展开状态集合 */
    private lateinit var expandList: BooleanArray
    private lateinit var divideLineList: BooleanArray
    /** 是否开启折叠动画 */
    private var openFoldAnim = false
    private lateinit var binding: HomeIndexItemRecommendOptBinding
    private lateinit var item: RecommendJobTagBean
    /** 设置当前卡片状态 */
    private var curState: RecommendState = RecommendState.Choose
        set(value) {
            field = value
            when(value) {
                RecommendState.Choose -> changeToChooseState()
                RecommendState.Loading -> changeToLoadingState()
                RecommendState.Modify -> changeToModifyIState()
            }
        }

    override fun onBindViewHolder(
        binding: HomeIndexItemRecommendOptBinding,
        item: RecommendJobTagBean,
        position: Int
    ) {
        this.binding = binding
        this.item = item
        curState = RecommendState.Choose
    }

    /**
     * 切换选择状态
     */
    private fun changeToChooseState() {
        with(binding) {
            llChoose.setVisible()
            flModify.setGone()
            llLoading.setGone()

            tvTitle.text = item.title
            item.cardList?.let {
                expandList = BooleanArray(it.size).apply {
                    this[0] = true
                }
                divideLineList = BooleanArray(it.size).apply {
                    this[0] = true
                }
                rvTag.adapter = listAdapter
                listAdapter.setList(it)
            }
        }
    }

    /**
     * 切换Loading状态
     */
    private fun changeToLoadingState() {
        with(binding) {
            llChoose.setGone()
            flModify.setGone()
            llLoading.setVisible()
            llLoading.postDelayed({
                curState = RecommendState.Modify
            }, 3000)
        }
    }

    /**
     * 切换编辑状态
     */
    private fun changeToModifyIState() {
        with(binding) {
            llChoose.setGone()
            flModify.setVisible()
            llLoading.setGone()

            tvModify.run {
                background = Bovb.with().radius(14.dp.toFloat()).color(getColor(R.color.C_P1)).build()
                onClick { curState = RecommendState.Choose }
            }
        }
    }

    var preExpandIndex = 0

    /**
     * 可折叠布局适配器
     */
    inner class RecommendJobTagItemDelegate : BindingViewDelegate<RecommendJobCard, PositionRecommendJobTagItemBinding>() {

        @SuppressLint("NotifyDataSetChanged")
        override fun onBindViewHolder(
            binding: PositionRecommendJobTagItemBinding,
            item: RecommendJobCard,
            position: Int
        ) {
            val isItemExpand = expandList[position]
            val isDivideLineHide = divideLineList[position]
            with(binding) {
                tvTitle.text = item.title
                motionContainer.progress = if (isItemExpand) 1.0f else 0f
                // 设置分割线
                binding.viewDivide.visibility = if (isDivideLineHide) View.GONE else View.VISIBLE
                // 设置Title样式
                setTitleStyle(isItemExpand, flTitle, ivArrow)
                // 设置内容区域样式
                setContentStyle(flContent)
                // 初始化标签
                initTagFlowLayout(layoutTagFlow, item.tagList)
                // 折叠点击
                root.onClick {
                    if (binding.motionContainer.progress == 1f) {
                        return@onClick
                    }
                    // 开启动画
                    if (openFoldAnim) {
                        // 改变分割线状态
                        divideLineList.fill(false)
                        divideLineList[position] = true
                        if (position > 0) {
                            divideLineList[position - 1] = true
                        }
                        divideLineList.indices.forEach {
                            listAdapter.notifyItemChanged(it, "divideLine")
                        }

                        preExpandIndex = expandList.indexOf(true)
                        root.post {
                            listAdapter.notifyItemChanged(preExpandIndex, "collapse")
                        }
                        // 改变展开状态
                        expandList.fill(false)
                        expandList[position] = true
                        motionContainer.transitionToEnd()
                    } else {
                        // 关闭动画
                        expandList.fill(false)
                        expandList[position] = true
                        // 改变分割线状态
                        divideLineList.fill(false)
                        divideLineList[position] = true
                        if (position > 0) {
                            divideLineList[position - 1] = true
                        }
                        // 刷新
                        listAdapter.notifyDataSetChanged()
//                        divideLineList.indices.forEach {
//                            listAdapter.notifyItemChanged(it, "divideLine")
//                        }
                    }

                    // 更新展开的Item背景
                    setTitleStyle(isItemExpand = true, flTitle, ivArrow)
                }
            }
        }

        private fun setTitleStyle(isItemExpand: Boolean, flTitle: FrameLayout, ivArrow: ImageView) {
            if (isItemExpand) {
                flTitle.run {
                    background = Bovb.with()
                        .topLeftRadius(8.dp.toFloat())
                        .topRightRadius(8.dp.toFloat())
                        .color(Color.parseColor("#F5F7FA"))
                        .build()
                }
                ivArrow.setGone()
            } else {
                flTitle.background = null
                ivArrow.setVisible()
            }
        }

        private fun setContentStyle(flContent: FrameLayout) {
            flContent.run {
                background = Bovb.with()
                    .bottomLeftRadius(8.dp.toFloat())
                    .bottomRightRadius(8.dp.toFloat())
                    .color(Color.parseColor("#F5F7FA"))
                    .build()
            }
        }

        private fun initTagFlowLayout(flowLayout: TagFlowLayoutOrigin, tagList: List<RecommendJobTag>) {
            val tagAdapter = object : TagAdapterOrigin<RecommendJobTag>(tagList) {
                override fun getView(
                    parent: FlowLayoutOrigin?,
                    position: Int,
                    t: RecommendJobTag?
                ): View {
                    val tagView = LayoutInflater.from(curContext)
                        .inflate(R.layout.position_recommend_opt_tag, parent, false) as TextView
                    tagView.text = t?.name
                    return tagView
                }
            }
            flowLayout.run {
                setSelectedTextBold()
                adapter = tagAdapter
                adapter.setSelectedList(0)
                setOnTagClickListener { view, position, parent ->
                    curState = RecommendState.Loading
                    true
                }
            }
        }

        /**
         * 局部刷新
         */
        override fun onBindViewHolder(
            holder: BindingViewHolder<PositionRecommendJobTagItemBinding>,
            item: RecommendJobCard,
            position: Int,
            payloads: List<Any>
        ) {
            if (payloads.isNotEmpty()) {
                when(payloads[0]) {
                    "collapse" -> {
                        holder.binding.motionContainer.transitionToStart()

                        // 更新收起的Item背景
                        holder.binding.flTitle.run {
                            background = null
                        }
                        holder.binding.ivArrow.setVisible()
                    }

                    "divideLine" -> {
                        holder.binding.viewDivide.visibility =
                            if (divideLineList[position]) View.GONE else View.VISIBLE
//                        holder.binding.llContainer.updateLayoutParams<ViewGroup.LayoutParams> {
//                            if (divideLineList[position]) 44.dp else 44.dp + 1.dp / 2
//                        }
                        holder.binding.motionContainer.getConstraintSet(R.id.start)?.let {set ->
                            set.getConstraint(R.id.ll_container)?.let {
                                it.layout.mHeight = if (divideLineList[position]) 44.dp else 44.dp + 1.dp / 2
                            }
                        }
                    }
                }
            } else {
                onBindViewHolder(holder.binding, item, position)
            }
        }

    }

    /**
     * 卡片状态
     */
    sealed class RecommendState{
        /** 选择态 */
        object Choose: RecommendState()
        /** 编辑态 */
        object Modify: RecommendState()
        /** 加载态 */
        object Loading: RecommendState()
    }

}