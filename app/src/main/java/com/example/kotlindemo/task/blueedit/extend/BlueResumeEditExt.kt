package com.example.kotlindemo.task.blueedit.extend

import androidx.viewpager2.widget.ViewPager2

/**
 * @Description 蓝领简历编辑扩展
 * @Author LuoJia
 * @Date 2024/04/29
 */


internal fun ViewPager2.showNext() {
    val curItemIndex = this.currentItem
    this.currentItem = curItemIndex + 1
}

internal fun ViewPager2.showPre(callback: () -> Unit) {
    val curItemIndex = this.currentItem
    this.currentItem = curItemIndex - 1
    if (curItemIndex == 0) {
        callback.invoke()
    }
}


