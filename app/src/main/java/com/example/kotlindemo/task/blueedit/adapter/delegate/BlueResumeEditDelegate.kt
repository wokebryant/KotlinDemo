package com.example.kotlindemo.task.blueedit.adapter.delegate

import com.example.kotlindemo.databinding.ItemBlueResumeEditDelegateBinding
import com.example.kotlindemo.task.blueedit.adapter.BlueResumeEditTagAdapter
import com.example.kotlindemo.task.blueedit.adapter.BlueTagType
import com.example.kotlindemo.task.blueedit.inter.IBlueResumeCallback
import com.example.kotlindemo.task.blueedit.model.BlueEditInfoSaveData
import com.example.kotlindemo.task.blueedit.model.BlueEditPageState
import com.example.kotlindemo.task.blueedit.model.BlueResumeTagState
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description 蓝领简历遍历标签Item
 * @Author LuoJia
 * @Date 2024/04/28
 */
class BlueResumeEditDelegate(
    private val callback: IBlueResumeCallback? = null
) : BindingViewDelegate<BlueEditPageState.ItemState, ItemBlueResumeEditDelegateBinding>() {

    override fun onBindViewHolder(
        binding: ItemBlueResumeEditDelegateBinding,
        item: BlueEditPageState.ItemState,
        position: Int
    ) {
        setView(binding, item, position)
    }

    private fun setView(
        binding: ItemBlueResumeEditDelegateBinding,
        item: BlueEditPageState.ItemState,
        position: Int
    ) {
        val selectedList = mutableSetOf<Int>()
        val isFoldItem = item.list.size > 8 && item.list[8].type == BlueTagType.Expand

        // 初始化选中的Set
        initSelectedList(item.list, selectedList)

        // 初始化Adapter
        val tagAdapter = BlueResumeEditTagAdapter().apply {
            itemClick = { tagPosition, state ->
                itemClick(
                    selectedList = selectedList,
                    tagPosition = tagPosition,
                    itemPosition = position,
                    state = state,
                    isFoldItem = isFoldItem
                )
            }
        }
        // 初始化RecycleView
        binding.rvTag.run {
            adapter = tagAdapter
            itemAnimator = null
        }
        // 更新UI
        binding.tvTitle.text = item.title
        tagAdapter.submitList(item.list)
    }

    /**
     * 初始化选中的Set
     */
    private fun initSelectedList(
        tagList: List<BlueResumeTagState>,
        selectedList: MutableSet<Int>
    ) {
        tagList.forEachIndexed { index, blueResumeTagState ->
            if (blueResumeTagState.selected) {
                selectedList.add(index)
            }
        }
    }

    private fun itemClick(
        selectedList: MutableSet<Int>,
        tagPosition: Int,
        itemPosition: Int,
        state: BlueResumeTagState,
        isFoldItem: Boolean
    ) {
        if (state.type == BlueTagType.Expand) {
            callback?.onThirdLevelExpandClick(itemPosition, selectedList)
            return
        }

        // 点击标签
        val isAdd = if (selectedList.contains(tagPosition)) {
            selectedList.remove(tagPosition)
            false
        } else {
            selectedList.add(tagPosition)
            true
        }

        callback?.onThirdLevelTagClick(itemPosition, tagPosition, selectedList, isFoldItem, isAdd)
    }

}