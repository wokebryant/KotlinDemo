package com.example.kotlindemo.task.resume

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/03/26
 */
class CustomLayoutManager : LinearLayoutManager {

    constructor(context: Context) : super(context)

    constructor(context: Context, orientation: Int, reverseLayout: Boolean) : super(context, orientation, reverseLayout)

//    override fun calculateExtraLayoutSpace(state: RecyclerView.State, extraLayoutSpace: IntArray) {
//        super.calculateExtraLayoutSpace(state, extraLayoutSpace)
//        // 设置额外的布局空间，可以根据需要动态计算
//        extraLayoutSpace[0] = 200
//        extraLayoutSpace[1] = 200
//    }

    override fun collectAdjacentPrefetchPositions(dx: Int, dy: Int, state: RecyclerView.State?, layoutPrefetchRegistry: LayoutPrefetchRegistry) {
        super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry)

        // 根据滑动方向(dx, dy)收集相邻的预取位置
        val anchorPos = findFirstVisibleItemPosition()
        if (dy > 0) {
            // 向下滑动，预取下面的Item数据
            for (i in (anchorPos + 1) until (anchorPos + 4)) {
                layoutPrefetchRegistry.addPosition(i, 0)
            }
        } else {
            // 向上滑动，预取上面的Item数据
            for (i in anchorPos - 1 downTo 0) {
                layoutPrefetchRegistry.addPosition(i, 0)
            }
        }
    }


}