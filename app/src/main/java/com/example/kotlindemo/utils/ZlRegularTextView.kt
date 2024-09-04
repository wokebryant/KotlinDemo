package com.example.kotlindemo.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * @Description 智联字体TextView
 * @Author LuoJia
 * @Date 2024/9/3
 */
class ZlRegularTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle,
) : AppCompatTextView(context, attr, defStyleAttr) {

    init {
        typeface = FontUtil.getZlTypefaceRegular()
    }

}