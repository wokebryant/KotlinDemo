package com.example.kotlindemo.study

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException


/**
 *  求N个数的最大值
 */
fun <T : Comparable<T>> getMaxNum(vararg nums: T): T {
    if (nums.isEmpty()) throw RuntimeException("Params can not be empty")
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) maxNum = num
    }
    return maxNum
}

fun String.showToast(duration: Int = Toast.LENGTH_SHORT,context: Context) {
    Toast.makeText(context, this, duration).show()
}

fun View.showSnackbar(text: String, actionText: String? = null,
                      duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, text, duration)
    if (actionText != null && block != null) {
        snackbar.setAction(actionText) {
            block
        }
    }

}

fun main(context: Context, view: View) {
    getMaxNum(2.1, 4.9, 0.1)
    "".showToast(context = context)
    view.showSnackbar("This is snackbar", "do action") {
        println("do action")
    }
}

/**
 *  DSL，允许编写一种看似不符合该语言语法的代码
 */
fun dsl() {

}