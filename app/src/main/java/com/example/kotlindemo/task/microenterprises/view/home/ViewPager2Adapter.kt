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
open class ViewPager2Adapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val list: MutableList<Fragment> = mutableListOf()

    //存放ItemId
    private val itemIdList: MutableList<Long> = mutableListOf()

    constructor (fragment: Fragment) : this(fragment.childFragmentManager, fragment.lifecycle)
    constructor (fragmentActivity: FragmentActivity) : this(
        fragmentActivity.supportFragmentManager,
        fragmentActivity.lifecycle
    )


    /**
     * 设置数据
     * */
    @SuppressLint("NotifyDataSetChanged")
    fun setList(items: List<Fragment>?) {
        if (items.isNullOrEmpty()) {
            return
        }
        list.clear()
        itemIdList.clear()
        list.addAll(items)
        itemIdList.addAll(items.map { return@map it.hashCode().toLong() })
        notifyDataSetChanged()
    }

    /**
     * 添加数据
     * */
    fun addItems(items: List<Fragment>?) {
        if (items.isNullOrEmpty()) {
            return
        }
        val startPosition = list.size
        list.addAll(startPosition, items)
        itemIdList.addAll(startPosition, items.map { return@map it.hashCode().toLong() })
        notifyItemRangeInserted(startPosition, items.size)
    }

    /**
     * 添加单个数据
     * */
    fun addItem(item: Fragment?) {
        if (item == null) {
            return
        }
        val addPosition = list.size
        list.add(item)
        itemIdList.add(item.hashCode().toLong())
        notifyItemInserted(addPosition)
    }

    /**
     * 清除所有数据
     * */
    @SuppressLint("NotifyDataSetChanged")
    fun clear(){
        list.clear()
        itemIdList.clear()
        notifyDataSetChanged()
    }

    /**
     * 移除数据
     * */
    fun remove(item: Fragment) {
        val position = list.indexOf(item)
        remove(position)
    }

    /**
     * 根据位置移除数据
     * */
    fun remove(position: Int) {
        if (position < 0) {
            return
        }

        if (position >= list.size) {
            return
        }
        list.removeAt(position)
        itemIdList.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * 获取所有数据
     * */
    fun items(): List<Fragment> {
        return list
    }

    /**
     * 获取特定位置的fragment
     * */
    fun getItemAt(position: Int): Fragment? {
        if (position < 0 || position >= list.size) {
            return null
        }
        return list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }


    override fun getItemId(position: Int): Long {
        return itemIdList[position]
    }

    override fun containsItem(itemId: Long): Boolean {
        return itemIdList.contains(itemId)
    }

}