package com.example.kotlindemo.task.microenterprises.view.home

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/09
 */
@SuppressLint("NotifyDataSetChanged")
class ViewPager2Adapter2(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    constructor (fragment: Fragment) : this(fragment.childFragmentManager, fragment.lifecycle)

    constructor (fragmentActivity: FragmentActivity) : this(
        fragmentActivity.supportFragmentManager,
        fragmentActivity.lifecycle
    )

    private val itemList = mutableListOf<ZLFragment>()
    private val itemIdList = mutableListOf<Long>()

    /**
     * 添加全新数据
     */
    fun setList(items: List<ZLFragment>?) {
        items?.let {
            itemList.clear()
            itemIdList.clear()
            itemList.addAll(it)
            itemIdList.addAll(it.map { zlFragment ->
                return@map zlFragment.hashCode().toLong() }
            )
            notifyDataSetChanged()
        }
    }

    /**
     * 追加数据列表
     */
    fun addItems(items: List<ZLFragment>?) {
        items?.let {
            val startPosition = itemList.size
            itemList.addAll(startPosition, it)
            itemIdList.addAll(startPosition, it.map { zlFragment ->
                return@map zlFragment.hashCode().toLong()
            })
            notifyItemRangeInserted(startPosition, itemList.size)
        }
    }

    /**
     * 添加单个数据
     */
    fun addItem(item: ZLFragment?) {
        item?.let {
            val addPosition = itemList.size
            itemList.add(it)
            itemIdList.add(it.hashCode().toLong())
            notifyItemInserted(addPosition)
        }
    }

    /**
     * 清除所有数据
     */
    fun clear() {
        itemList.clear()
        itemIdList.clear()
        notifyDataSetChanged()
    }

    /**
     * 根据位置移除数据
     */
    fun remove(position: Int) {
        if (position < 0) {
            return
        }

        if (position >= itemList.size) {
            return
        }

        itemList.removeAt(position)
        itemIdList.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * 获取特定位置的fragment
     */
    fun getItemAt(position: Int): Fragment? {
        if (position < 0 || position >= itemList.size) {
            return null
        }
        return itemList[position].invoke()
    }

    override fun getItemCount() =
        itemList.size

    override fun createFragment(position: Int) =
        itemList[position].invoke()

    override fun getItemId(position: Int) =
        itemIdList[position]

    override fun containsItem(itemId: Long) =
        itemIdList.contains(itemId)

}

//typealias ZLFragment = () -> Fragment