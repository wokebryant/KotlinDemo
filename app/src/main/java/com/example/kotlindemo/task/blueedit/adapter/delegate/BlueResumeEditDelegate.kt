package com.example.kotlindemo.task.blueedit.adapter.delegate

import com.example.kotlindemo.databinding.ItemBlueResumeEditDelegateBinding
import com.example.kotlindemo.task.blueedit.adapter.BlueResumeEditTagAdapter
import com.example.kotlindemo.task.blueedit.adapter.BlueTagType
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
    private val onThreeLevelSelectedCallback: (BlueEditInfoSaveData) -> Unit
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
        val originList = item.list
        val selectedList = mutableSetOf<Int>()

        // 初始化Adapter
        val tagAdapter = BlueResumeEditTagAdapter().apply {
            itemClick = { tagPosition, state ->
                itemClick(
                    adapter = this,
                    originList = originList,
                    selectedList = selectedList,
                    position = tagPosition,
                    state = state,
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
        val showList = getFirstShowList(originList)
        tagAdapter.submitList(showList)
        // 初始化选中的Set
        initSelectedList(originList, selectedList)
    }

    /**
     * 初始化选中的Set
     */
    private fun initSelectedList(
        originList: List<BlueResumeTagState>,
        selectedList: MutableSet<Int>
    ) {
        originList.forEachIndexed { index, blueResumeTagState ->
            if (blueResumeTagState.selected) {
                selectedList.add(index)
            }
        }
    }

    /**
     * 获取首次展示的列表
     */
    private fun getFirstShowList(originList: List<BlueResumeTagState>): List<BlueResumeTagState> {
        return if (originList.size > 9) {
            val subList = originList.subList(0, 8).toMutableList()
            val footTag = BlueResumeTagState(
                name = "",
                selected = false,
                type = BlueTagType.Expand
            )
            subList.add(footTag)
            subList
        } else {
            originList
        }
    }

    /**
     * 点击监听
     */
    private fun itemClick(
        adapter: BlueResumeEditTagAdapter,
        originList: List<BlueResumeTagState>,
        selectedList: MutableSet<Int>,
        position: Int,
        state: BlueResumeTagState,
    ) {
        // 展开全部
        if (state.type == BlueTagType.Expand) {
            val showList = originList.mapIndexed { index, blueResumeTagState ->
                blueResumeTagState.copy(selected = selectedList.contains(index))
            }
            adapter.submitList(showList)
            return
        }

        // 点击标签
        if (selectedList.contains(position)) {
            selectedList.remove(position)
        } else {
            selectedList.add(position)
        }

        val newTagList = adapter.curList().mapIndexed { index, blueResumeTagState ->
            blueResumeTagState.copy(selected = selectedList.contains(index))
        }
        adapter.submitList(newTagList)
        // 三级标签点击回调
        val saveData = BlueEditInfoSaveData(
            answerList = getSelectedAnswerIdList(selectedList, adapter.curList()),
            level = "3",
            questionId = state.questionId,
            questionType = state.questionType,
            parentAnswerId = state.parentAnswerId,
            parentQuestionId = state.parentQuestionId
        )
        onThreeLevelSelectedCallback.invoke(saveData)
    }

    private fun getSelectedAnswerIdList(
        selectedList: MutableSet<Int>,
        curList: List<BlueResumeTagState>
    ): List<String> {
        val idList = mutableListOf<String>()
        curList.forEachIndexed { index, blueResumeTagState ->
            if (selectedList.contains(index)) {
                idList.add(blueResumeTagState.id)
            }
        }

        return idList
    }

}