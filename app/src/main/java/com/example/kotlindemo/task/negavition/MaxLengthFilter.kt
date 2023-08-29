package com.example.kotlindemo.task.negavition

import android.text.InputFilter
import android.text.Spanned

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/24
 */
class MaxLengthFilter(private val maxLength: Int) : InputFilter{

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        val keep: Int = maxLength - (dest.toString().trim { it <= ' ' }.length - (dend - dstart))

        if (dest.toString().trimStart().length >= maxLength && source.toString() != " ") {
            return ""
        }

        return if (keep <= 0) {
            ""
        } else if (keep >= end - start) {
            null
        } else {
            source!!.subSequence(start, start + keep)
        }
    }
}