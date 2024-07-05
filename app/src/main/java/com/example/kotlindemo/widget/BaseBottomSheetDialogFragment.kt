package com.example.kotlindemo.widget

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.example.kotlindemo.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.binding.FragmentBinding
import com.zhaopin.social.module_common_util.binding.FragmentBindingDelegate
import com.zhaopin.toast.showToast
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Description: BottomSheetDialogFragment基类
 * @Author: LuoJia
 * @Date: 2023/5/6
 */
abstract class BaseBottomSheetDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment(),
    FragmentBinding<VB> by FragmentBindingDelegate() {

    var listener: BottomSheetDialogListener? = null

    /** 设置Dialog初始状态 */
    var initState: Int = BottomSheetBehavior.STATE_EXPANDED
    /** 设置Dialog折叠状态下的高度 */
    var peekHeight = 0
    /** 设置Dialog展开状态下的高度 */
    var expandHeight = ViewGroup.LayoutParams.WRAP_CONTENT

    /** 设置是否允许拖动 */
    var isDraggable = false
    /** 设置拖动时Dialog是否可以隐藏 */
    var isHideable = true
    /** 设置拖动时是否跳过折叠态 */
    var skipCollapsed = true

    /** 设置是否屏蔽返回键 */
    var disableBackClose = true
    /** 设置点击空白处是否能够关闭弹窗 */
    var outsideClickClose = false

    /** 滑动监听 */
    private val bottomSheetBehaviorCallback = object : BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            listener?.onStateChanged(bottomSheet, newState)
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            listener?.onSlide(bottomSheet, slideOffset)
        }

    }

    /**
     *  设置Dialog样式
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DefaultBottomSheetDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return createViewWithBinding(inflater, container)
    }

    /**
     *  设置Dialog属性(根据需求自定义)
     *  偶现[RuntimeException: InputChannel is not initialized]
     *  为Google已知Bug,暂时没有解决方案,暂时try-catch
     *  https://issuetracker.google.com/issues/37018931
     */
    @SuppressLint("ResourceType")
    override fun onStart() {
        try {
            super.onStart()
            val dialog = dialog as BottomSheetDialog
            val rootView = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)!!
            rootView.layoutParams.height = expandHeight
            dialog.apply {
                setCanceledOnTouchOutside(outsideClickClose)
                behavior.state = initState
                behavior.peekHeight = peekHeight
                behavior.isDraggable = isDraggable
                behavior.isHideable = isHideable
                behavior.skipCollapsed = skipCollapsed
                behavior.addBottomSheetCallback(bottomSheetBehaviorCallback)
                setOnKeyListener { _, keyCode, _ ->
                    return@setOnKeyListener if (disableBackClose) {
                        keyCode == KeyEvent.KEYCODE_BACK
                    } else {
                        false
                    }
                }
            }

            val outSide = dialog.findViewById<View>(R.id.touch_outside)
            if (outsideClickClose) {
                outSide?.setOnClickListener {
                    onOutSideClick()
                }
            }
            rootView.postDelayed(
                { this.dialog?.window?.setWindowAnimations(R.style.BottomSheetAnim) },
                200
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        // 去除后台切换至前台的动画
        dialog?.window?.setWindowAnimations(R.style.BottomSheetNoAnim)
    }

    fun updateDraggable(enable: Boolean) {
        val dialog = dialog as BottomSheetDialog
        val rootView = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)!!
        rootView.layoutParams.height = expandHeight
        dialog.apply {
            behavior.isDraggable = enable
            isDraggable = enable
            initState = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    /**
     * 默认不限制最大高度，如需要这里设置
     */
    fun setMaxHeight(maxHeight: Int) {
        val dialog = dialog as BottomSheetDialog
        dialog.apply {
            behavior.maxHeight = maxHeight
        }
    }

    /**
     *  展示Dialog
     */
    @JvmOverloads
    fun show(manager: FragmentManager, listener: BottomSheetDialogListener? = null) {
        this.listener = listener
        showAllowingStateLoss(manager, javaClass.simpleName)
    }

    /**
     *  如果在ShowDialog之前宿主Activity被销毁或者跳转到下一个Activity。
     *  会报错: Can not perform this action after onSaveInstanceState
     */
    private fun showAllowingStateLoss(manager: FragmentManager, tag: String?) {
        try {
            // 反射修改变量值
            DialogFragment::class.java.getDeclaredField("mDismissed").apply {
                isAccessible = true
                set(this, false)
            }

            DialogFragment::class.java.getDeclaredField("mShownByMe").apply {
                isAccessible = true
                set(this, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // commitAllowingStateLoss代替commit
        val ft = manager.beginTransaction()
        ft.add(this, tag)
        ft.commitAllowingStateLoss()
    }

    /**
     * 空白区域点击
     */
    open fun onOutSideClick() {
        dismissAllowingStateLoss()
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