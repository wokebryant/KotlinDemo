package com.example.kotlindemo.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.appbase.util.currentActivity

/**
 * Created by lwq on 2019-10-21.
 */
object SizeUtils {


    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dpValue
     * @return
     */
    @JvmStatic
    fun dp2px(context: Context, dpValue: Int): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        return displayMetrics.widthPixels
    }

    /**
     *  获取屏幕尺寸
     *  getSize: 不包含状态栏，导航栏
     */
    fun getScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getSize(point)

        return point
    }

    /**
     *  获取屏幕尺寸
     *  getRealSize: 包含状态栏，导航栏
     */
    fun getRealScreenSize(context: Context): Point {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val point = Point()
        display.getRealSize(point)

        return point
    }

    /**
     *  获取状态栏高度
     */
    fun getStatusBarHeight(context: Context): Int {
        val resources: Resources = context.resources
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

        return resources.getDimensionPixelSize(resourceId)
    }

    /**
     *  获取导航栏高度
     */
    fun getNavBarHeight(context: Context): Int {
        val res: Resources = context.resources
        val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")

        return if (resourceId != 0) {
            res.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }


    /**
     * 导航栏是否显示 (能够判断全面屏手机小白条是否展示)
     */
    fun isNavigationBarExist(activity: Activity?): Boolean {
        val vp = activity?.window?.decorView as? ViewGroup
        if (vp != null) {
            for (i in 0 until vp.childCount) {
                vp.getChildAt(i).context.packageName
                if (vp.getChildAt(i).id != View.NO_ID && "navigationBarBackground" == activity.resources.getResourceEntryName(
                        vp.getChildAt(i).id
                    )
                ) {
                    return true
                }
            }
        }
        return false
    }

    fun getMetricsFull(): DisplayMetrics {
        val  metrics = DisplayMetrics()
        val windowMgr = curContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowMgr.defaultDisplay.getRealMetrics(metrics)
        return metrics
    }

}