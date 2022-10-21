package com.example.kotlindemo.utils


import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import com.example.kotlindemo.study.getResultWithContext


/**
 *  View相关的拓展
 */

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.isGone() = visibility == View.GONE

fun View.isVisible() = visibility == View.VISIBLE

fun View.isInvisible() = visibility == View.INVISIBLE


/**
 *  获取颜色，替代过时的resources.getColor方法
 */
fun View.getColor(color: Int) = ContextCompat.getColor(context, color)
fun Fragment.getColor(color: Int) = ContextCompat.getColor(requireContext(), color)

/**
 *  获取drawable，替代过时的resources.getDrawable方法
 */
fun View.getDrawable(drawable: Int) = ContextCompat.getDrawable(context, drawable)

/**
 *  获取Bitmap
 */
fun View.getBitmap(drawable: Int) = getDrawable(drawable)?.toBitmap()

/**
 * 获取String
 */
fun View.getString(string: Int) = context.getString(string)

fun <T> Collection<T>.copyOf(): Collection<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}

fun <T> Collection<T>.mutableCopyOf(): MutableCollection<T> {
    val original = this
    return mutableListOf<T>().apply { addAll(original) }
}

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
