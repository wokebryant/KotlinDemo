package com.example.kotlindemo.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager

val Number.dp get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)


data class TouchArea(
    var xMin: Float,
    var xMax: Float,
    var yMin: Float,
    var yMax: Float
)

fun TouchArea.contains(x: Float, y: Float) = x in xMin..xMax && y in yMin..yMax

/**
 *  获取屏幕尺寸
 */
fun getScreenSize(context: Context): Point {
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val point = Point()
    display.getSize(point)

    getStatusBarHeight(context)
    getNavBarHeight(context)

    return point
}

fun getScreenHeight(context: Context): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        ?: return -1
    val point = Point()
    wm.defaultDisplay.getRealSize(point)
    return point.y
}

fun getStatusBarHeight(context: Context): Int {
    val resources: Resources = context.getResources()
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")

    Log.i("WheelPointMarkView ", "statusBarHeight= ${resources.getDimensionPixelSize(resourceId)}")
    return resources.getDimensionPixelSize(resourceId)
}

fun getNavBarHeight(context: Context): Int {
    val res: Resources = context.getResources()
    val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")

    Log.i("WheelPointMarkView ", "navBarHeight= ${res.getDimensionPixelSize(resourceId)}")

    return if (resourceId != 0) {
        res.getDimensionPixelSize(resourceId)
    } else {
        0
    }
}
