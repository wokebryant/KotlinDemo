package com.example.kotlindemo.study.mvi.view

import androidx.recyclerview.widget.RecyclerView

/**
 * @Description 职位推荐卡骨架屏管理
 * @Author LuoJia
 * @Date 2024/1/6
 */
class JobRecommendSkeletonManager(
    private val recyclerView: RecyclerView,
    private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) {

//    private var skeletonScreen: RecyclerViewSkeletonScreen? = null
//
//    fun show() {
//        recyclerView.setVisible()
//        skeletonScreen = Skeleton.bind(recyclerView)
//            .adapter(adapter)
//            .load(R.layout.layout_recommend_job_skeleton)
//            .color(R.color.white)
//            .show()
//    }
//
//    fun hide() {
//        if (skeletonScreen != null) {
//            skeletonScreen!!.hide()
//            skeletonScreen = null
//            recyclerView.setGone()
//        }
//    }
}