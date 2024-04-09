package com.example.kotlindemo.task.resume

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 简历点后推卡片RecyclerView，解决滑动问题
 * @Author LuoJia
 * @Date 2024/03/25
 */
class ResumeRecommendCardRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    //记录上次手指位置
    private var mLastY = 0f

    //是否已经滑到了底部
    private var isToBottom = false

    //是否已经滑到了顶部
    private var isToTop = true

    private val isDiffPosition: Boolean
        get() {
            val itemRect = Rect()
            getLocalVisibleRect(itemRect)
            return (itemRect.bottom - itemRect.top) <= 110.dp
        }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //记录按下位置
                mLastY = event.y
                //如果手指按下触摸区域在自身，先不允许父View拦截事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                checkPosition(event.y)
                if (isToBottom || isToTop || isDiffPosition) {
                    //已经滑动到顶部或者底部时，不需要自己处理手势，无需下发
                    parent.requestDisallowInterceptTouchEvent(false)
                    return false
                } else {
                    parent.requestDisallowInterceptTouchEvent(true)
                }
                mLastY = event.y
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                false
            )
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * 判断item的位置情况，确认是否需要滑动
     */
    private fun checkPosition(nowY: Float) {
        //暂时仅处理LinearLayoutManager情况
        val manager = layoutManager as? LinearLayoutManager
        manager?.let {
            isToTop = false
            isToBottom = false
            //获取可见的item位置
            val firstVisiblePosition = it.findFirstVisibleItemPosition()
            val lastVisiblePosition = it.findLastVisibleItemPosition()

            //如果当前有item显示
            if (it.childCount > 0) {
                if (lastVisiblePosition == it.itemCount - 1) {
                    val lastVisibleView = it.findViewByPosition(lastVisiblePosition)
                    val lastVisibleItemBottom = lastVisibleView?.bottom ?: 0
                    val recyclerViewBottom = height - paddingBottom
                    val isInBottom = lastVisibleItemBottom <= recyclerViewBottom
                    //检查是否能向上滑，且滑动方向是向上
                    if (canScrollVertically(-1) && nowY < mLastY && isInBottom) {
                        //标记已经滑动到了底部，不能再向上滑动了
                        isToBottom = true
                    }
                } else if (firstVisiblePosition == 0) {
                    val firstVisibleView = it.findViewByPosition(firstVisiblePosition)
                    val firstVisibleItemTop = firstVisibleView?.top ?: 0
                    val recyclerViewTop = paddingTop
                    val isInTop = firstVisibleItemTop >= recyclerViewTop
                    //检查是否能向下滑，且滑动方向是向下
                    if (canScrollVertically(1) && nowY > mLastY && isInTop) {
                        //标记已经滑动到了顶部，不能再向下滑动了
                        isToTop = true
                    }
                }
            }
        }
    }

}
