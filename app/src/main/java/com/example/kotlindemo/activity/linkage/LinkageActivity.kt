package com.example.kotlindemo.activity.linkage

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindemo.R
import com.example.kotlindemo.activity.BaseActivity
import com.example.kotlindemo.activity.flow.FlowActivity
import com.example.kotlindemo.databinding.ActivityLinagePageBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.collectLast
import com.example.kotlindemo.utils.colorSpan
import com.example.kotlindemo.utils.getColor
import com.example.kotlindemo.utils.setVisible
import com.example.kotlindemo.activity.linkage.origin.TagState
import com.zhaopin.common.widget.linkage.contract.ILinkageSecondaryScrollListener
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 二级联动页面
 * @Author LuoJia
 * @Date 2023/6/30
 */
class LinkageActivity : BaseActivity(),
    ILinkageSecondaryScrollListener {

    private val binding: ActivityLinagePageBinding by binding()

    private val viewModel by viewModels<LinkageViewModel>()
    private val needRefreshItems = mutableMapOf<Int, MutableSet<Int>>()
    private var needClearItems = mutableListOf<Int>()
    private var isSelectedRvVisible = false

    private val listAdapter: MultiTypeAdapter by lazy {
        MultiTypeAdapter(
            diffEnable = true
        ).apply {
            register(LinkageSelectedDelegate(onSelectedTagDeleteCallback))
        }
    }

    private val launcherActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        viewModel.sendUiIntent(
            LinkageIntent.IndustrySelectedItemsChange(
                LinkageConstant.getAllIndustryPageBackData()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        binding.rvSelected.run {
            itemAnimator = null
            adapter = listAdapter
        }
        binding.tvSubmit.run {
            background = Bovb.with().color(getColor(R.color.C_587CF7)).radius(22f.dp).build()
            onClick { viewModel.sendUiIntent(LinkageIntent.SubmitFilterData) }
        }
        binding.ivMask.run {
            val backGround = Bovb.with()
                .gradientColor(intArrayOf(getColor(R.color.C_FFFFFF), Color.parseColor("#05FFFFFF")))
                .gradientType(GradientDrawable.LINEAR_GRADIENT)
                .gradientOrientation(GradientDrawable.Orientation.BOTTOM_TOP)
                .build()
            background = backGround
        }
        binding.tvBack.onClick { finish() }
        binding.tvClear.onClick { viewModel.sendUiIntent(LinkageIntent.ClearAll) }
    }

    private fun initData() {
        collect()
        requestAllData()
    }


    private fun requestAllData() {
        viewModel.sendUiIntent(LinkageIntent.RequestLinkageData(mutableMapOf()))
    }

    private fun collect() {
        viewModel.uiStateFlow.collectLast(this) {
            updateTopUi(it.selectedList)
            updateLinkageUi(it.linkageList)
        }
        viewModel.uiEventFlow.collectLast(this) { event ->
            updateLinkageItemUi(event)
        }
    }

    /**
     * 更新标题栏和顶部选中的标签
     */
    @SuppressLint("SetTextI18n")
    private fun updateTopUi(data: Pair<List<LinkageChildItem>, TagState>) {
        val (list, state) = data
        if (list.isNotEmpty()) {
            binding.rvSelected.setVisible()
            listAdapter.submitList(list)
            if (state == TagState.Add || state == TagState.Update) {
                binding.rvSelected.smoothScrollToPosition(list.size - 1)
            }

            binding.tvTitle.text = "筛选·${list.size}".colorSpan(list.size.toString(), "#5B7BE9")
            binding.tvClear.run {
                setTextColor(getColor(R.color.C_222222))
                isEnabled = true
            }
            needClearItems.clear()
        } else {
            listAdapter.submitList(emptyList())

            binding.tvTitle.text = "筛选·0".colorSpan("0", "#5B7BE9")
            binding.tvClear.run {
                setTextColor(getColor(R.color.C_999999))
                isEnabled = false
            }
        }
        showSelectedRvAnim(list.isNotEmpty())
    }

    /**
     * 展示横向选择RecyclerView动画
     */
    private fun showSelectedRvAnim(show: Boolean) {
        if (show && isSelectedRvVisible) return
        if (!show && !isSelectedRvVisible) return
        isSelectedRvVisible = show
        val start = if (show) 0 else 56.dp
        val end = if (show) 56.dp else 0
        ValueAnimator.ofInt(start, end).apply {
            duration = 200
            addUpdateListener {
                binding.rvSelected.updateLayoutParams<ViewGroup.LayoutParams> {
                    height = it.animatedValue as Int
                }
            }
            start()
        }
    }

    /**
     * 更新二级联动页Ui
     */
    private fun updateLinkageUi(list: MutableList<LinkageGroupItem>) {
        binding.rvLinkage.init(
            list,
            PrimaryAdapterConfig(),
            SecondaryAdapterConfig(onTagSelectedCallback, onClickAllIndustryListener),
            this
        )
        binding.rvLinkage.setPercent(0.24f)
    }

    /**
     * 二级联动页Item Ui，由于TagFlowLayout框架原因，Item只能单独做局部刷新,不然会闪烁...
     */
    private fun updateLinkageItemUi(event: LinkageEvent) {
        val adapter = binding.rvLinkage.secondaryAdapter
        val layoutManager = binding.rvLinkage.secondaryRecyclerView.layoutManager as LinearLayoutManager
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()
        when(event) {
            is LinkageEvent.OnLinkageItemRemove -> {
                val (itemIndex, tagIndex) = event.data
                // 判断当前要删除的Tag是否可见，可见才执行
                if (itemIndex in firstVisiblePosition..lastVisiblePosition) {
                    adapter.notifyItemChanged(itemIndex, Pair("remove", tagIndex))
                } else {
                    needRefreshItems.getOrPut(itemIndex) { mutableSetOf(tagIndex) }.add(tagIndex)
                }
            }
            is LinkageEvent.OnLinkageItemClear -> {
                needRefreshItems.clear()
                for (i in 0 until event.size) {
                    needClearItems.add(i)
                }
                val itemCount = lastVisiblePosition - firstVisiblePosition + 1
                adapter.notifyItemRangeChanged(firstVisiblePosition, itemCount, Pair("clear", null))
            }

            is LinkageEvent.OnLinkageItemAdd -> {
                val (itemIndex, tagIndexList) = event.items
                adapter.notifyItemChanged(itemIndex, Pair("add", tagIndexList))
            }
        }
    }

    /**
     * 二级筛选Tag选择回调
     */
    private val onTagSelectedCallback: (Set<LinkageChildItem>, TagState) -> Unit = { items, state ->
        viewModel.sendUiIntent(LinkageIntent.LinkageTagClick(items, state))
    }

    /**
     * 选中的Tag删除回调
     */
    private val onSelectedTagDeleteCallback: (LinkageChildItem) -> Unit = {
        viewModel.sendUiIntent(LinkageIntent.RemoveSelectedTag(mutableSetOf(it)))

    }

    /**
     * 行业点击全部监听
     */
    private val onClickAllIndustryListener: (List<LinkageChildItem>) -> Unit = {
        launcherActivity.launch(Intent(this@LinkageActivity, FlowActivity::class.java))
    }

    /**
     * 滚动时更新之前不可见的Item，用TagFlowLayout留下的坑。。。用RecyclerView会简单很多
     */
    override fun onScroll(firstVisiblePosition: Int, lastVisiblePosition: Int) {
        val adapter = binding.rvLinkage.secondaryAdapter
        // 删除逻辑
        val iterator = needRefreshItems.iterator()
        while (iterator.hasNext()) {
            val (itemIndex, tagIndexList) = iterator.next()
            if (tagIndexList.isNotEmpty() && itemIndex in firstVisiblePosition .. lastVisiblePosition) {
                adapter.notifyItemChanged(itemIndex, Pair("remove", tagIndexList))
                iterator.remove()
            }
        }
        // 清除逻辑
        val clearIterator = needClearItems.iterator()
        while (clearIterator.hasNext()) {
            val itemIndex = clearIterator.next()
            if (itemIndex in firstVisiblePosition .. lastVisiblePosition) {
                adapter.notifyItemChanged(itemIndex, Pair("clear", null))
                clearIterator.remove()
            }
        }
    }


}