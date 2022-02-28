package com.example.kotlindemo.widget

import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import com.example.kotlindemo.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * @Author: LuoJia
 * @Date:
 * @Description: BottomSheetDialogFragment基类
 */
open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_ANSWER = "extra_answer"
        const val EXTRA_BITMAP_INFO = "extra_bitmap_info"
    }

    var listener: BottomSheetDialogListener? = null

    /** 设置Dialog初始状态 */
    var initState: Int = BottomSheetBehavior.STATE_EXPANDED
    /** 设置Dialog折叠状态下的高度 */
    var peekHeight = 0
    /** 设置Dialog展开状态下的高度 */
    var expandHeight = -1
    /** 设置是否允许拖动 */
    var isDraggable = false
    /** 设置拖动时Dialog是否可以隐藏 */
    var isHideable = true
    /** 设置拖动时是否跳过折叠态 */
    var skipCollapsed = true
    /** 设置是否屏蔽返回键 */
    var allowBackClose = true

    /**
     *  设置Dialog样式
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DefaultBottomSheetDialogStyle)
    }

    /**
     *  设置Dialog属性(根据需求自定义)
     */
    override fun onStart() {
        super.onStart()
        val dialog = dialog as BottomSheetDialog
        val rootView = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)!!
        rootView.layoutParams.height = expandHeight
        dialog.apply {
            behavior.state = initState
            behavior.peekHeight = peekHeight
            behavior.isDraggable = isDraggable
            behavior.isHideable = isHideable
            behavior.skipCollapsed = skipCollapsed
            // 去除从后台切到前台的动画
//            window?.setWindowAnimations(R.style.BottomSheetNoAnim)
            setOnKeyListener { _, keyCode, _ ->
                return@setOnKeyListener if (allowBackClose) {
                    keyCode == KeyEvent.KEYCODE_BACK
                } else {
                    keyCode != KeyEvent.KEYCODE_BACK
                }
            }
        }
    }

    /**
     *  展示Dialog
     */
    fun show(manager: FragmentManager, listener: BottomSheetDialogListener) {
        this.listener = listener
        super.show(manager, javaClass.simpleName)
    }

    /**
     *  Dialog关闭监听
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.dismiss()
    }

    interface BottomSheetDialogListener {

        /** 隐藏 */
        fun dismiss()

        /**
         * Dialog状态变换
         * @param [bottomSheet]
         * @param [newState]
         *  STATE_DRAGGING: 拖动
         *  STATE_SETTLING: 停顿
         *  STATE_EXPANDED: 展开
         *  STATE_COLLAPSED: 折叠
         *  STATE_HIDDEN: 隐藏
         *  STATE_HALF_EXPANDED: 半展开
         * */
        fun onStateChanged(bottomSheet: View, newState: Int) {}

        /**
         * 拖动
         * @param [bottomSheet]
         * @param [slideOffset] 拖动偏移量
         * */
        fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

}