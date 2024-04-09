package com.example.kotlindemo.task.resume

import androidx.recyclerview.widget.RecyclerView
import com.ethanhua.skeleton.RecyclerViewSkeletonScreen
import com.ethanhua.skeleton.Skeleton
import com.example.kotlindemo.R
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible

/**
 * @Description 骨架屏管理
 * @Author LuoJia
 * @Date 2024/03/25
 */
class SkeletonManager {

    private var skeletonScreen: RecyclerViewSkeletonScreen? = null

    var recyclerView: RecyclerView ? = null
    var adapter: RecyclerView.Adapter<RecyclerView.ViewHolder> ? = null
    var itemLayout: Int = R.layout.layout_default_item_skeleton

    fun show() {
        if (recyclerView != null && adapter != null) {
            recyclerView?.setVisible()
            skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(adapter)
                .load(itemLayout)
                .color(R.color.white)
                .show()
        }
    }

    fun hide() {
        if (skeletonScreen != null) {
            skeletonScreen!!.hide()
            skeletonScreen = null
            recyclerView?.setGone()
        }
    }

}