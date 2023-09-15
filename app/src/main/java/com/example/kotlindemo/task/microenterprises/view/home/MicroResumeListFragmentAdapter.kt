package com.example.kotlindemo.task.microenterprises.view.home

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlindemo.task.microenterprises.bean.MircoResumeJobBean
import com.example.kotlindemo.task.microenterprises.view.page.MicroResumeListFragment

/**
 * @Description 小微企业简历列表Fragment管理器
 * @Author LuoJia
 * @Date 2023/8/30
 */
class MicroResumeListFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val jobList: MutableList<MircoResumeJobBean> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<MircoResumeJobBean>) {
        jobList.clear()
        jobList.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: MircoResumeJobBean?) {
        item?.let {
            jobList.add(it)
            notifyItemInserted(itemCount - 1)
        }
    }

    override fun getItemCount() = jobList.size

    override fun createFragment(position: Int): MicroResumeListFragment = MicroResumeListFragment(jobList[position])
}