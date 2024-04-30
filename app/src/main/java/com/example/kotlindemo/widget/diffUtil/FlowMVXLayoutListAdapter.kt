package com.example.kotlindemo.widget.diffUtil

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.zhaopin.common.widget.mvx.flowLayout.MVXTagUIState
import com.zhaopin.common.widget.mvx.flowLayout.TagLayoutViewHolder

/**
 * @Description MVXFlowLayout适配器（支持Item下标实时刷新）
 * @Author LuoJia
 * @Date 2024/04/28
 */
abstract class FlowMVXLayoutListAdapter<T : MVXTagUIState, B : ViewBinding>
    : ListAdapter<MVXTagUIState, TagLayoutViewHolder<B>>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagLayoutViewHolder<B> {
        return TagLayoutViewHolder(getItemViewBinding(parent))
    }

    override fun onBindViewHolder(holder: TagLayoutViewHolder<B>, position: Int) {
        val item = getItem(position) as? T
        item?.let { itemData ->
            holder.binding.root.setOnClickListener {
                val realPosition = if (currentList.indexOf(itemData) >= 0) currentList.indexOf(itemData) else position
                itemClick.invoke(realPosition, itemData)
                onItemClick(realPosition, itemData)
            }
            onBindView(holder.binding, itemData, position)
        }
    }


    /**
     * 优先推荐在UIState中设置click函数
     */
    var itemClick: (position: Int, data: T) -> Unit = { _, _ -> }

    abstract fun getItemViewBinding(parent: ViewGroup): B

    abstract fun onBindView(binding: B, data: T, position: Int)

    open fun onItemClick(position: Int, data: T) {}

    fun curList(): List<T> {
        return (currentList as? List<T>) ?: emptyList()
    }

}

class ItemDiffCallback : DiffUtil.ItemCallback<MVXTagUIState>() {

    override fun areItemsTheSame(oldItem: MVXTagUIState, newItem: MVXTagUIState): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: MVXTagUIState, newItem: MVXTagUIState): Boolean {
        return oldItem == newItem
    }
}