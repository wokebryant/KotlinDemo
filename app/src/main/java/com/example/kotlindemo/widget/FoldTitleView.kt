package com.example.kotlindemo.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.kotlindemo.databinding.FoldTitleViewBinding
import com.example.kotlindemo.utils.binding

/**
 *  可折叠标题栏
 */
class FoldTitleView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: FoldTitleViewBinding by binding()

    init {

    }

    private fun setData() {

    }

}