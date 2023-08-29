package com.example.kotlindemo.task.negavition.item

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText


/**
 * Created by Administrator on 2017/2/17.
 */
class AppNPSEditText : AppCompatEditText {
    //滑动距离的最大边界
    private var mOffsetHeight = 0

    //是否到顶或者到底的标志
    private var mBottomFlag = false

    constructor(context: Context?) : super(context!!) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
//        viewTreeObserver.addOnScrollChangedListener {
//            invalidate() // 重新绘制控件
//        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val paddingTop: Int
        val paddingBottom: Int
        val mHeight: Int
        val mLayoutHeight: Int

        //获得内容面板
        val mLayout = layout
        //获得内容面板的高度
        mLayoutHeight = mLayout.height
        //获取上内边距
        paddingTop = totalPaddingTop
        //获取下内边距
        paddingBottom = totalPaddingBottom

        //获得控件的实际高度
        mHeight = height

        //计算滑动距离的边界
        mOffsetHeight = mLayoutHeight + paddingTop + paddingBottom - mHeight
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) //如果是新的按下事件，则对mBottomFlag重新初始化
        {
            mBottomFlag = false
        }
        //如果已经不要这次事件，则传出取消的信号，这里的作用不大
        if (mBottomFlag) {
            event.action = MotionEvent.ACTION_CANCEL
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = super.onTouchEvent(event)
        //如果是需要拦截，则再拦截，这个方法会在onScrollChanged方法之后再调用一次
        if (!mBottomFlag) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return result
    }

    override fun onScrollChanged(horiz: Int, vert: Int, oldHoriz: Int, oldVert: Int) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert)
        if (vert == mOffsetHeight || vert == 0) {
            //这里触发父布局或祖父布局的滑动事件
            parent.requestDisallowInterceptTouchEvent(false)
            mBottomFlag = true
        }
    }

    private val mRect = Rect()


    override fun onDraw(canvas: Canvas?) {
//        // 动态修改顶部空白区域的高度
//        mRect.set(0, 0, getWidth(), Math.max(0, getPaddingTop() - getScrollY()));
//        canvas?.drawRect(mRect, getPaint());
        super.onDraw(canvas)
    }
}