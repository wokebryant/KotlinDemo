package com.example.kotlindemo.task.jobdetail

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.ViewFlipper
import com.example.kotlindemo.R

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/1/22
 */
class JobDetailFlipper @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
) : ViewFlipper(context, attr) {

    private val interval = 2000

    init {
        flipInterval = interval
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in)
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out)
    }

    fun startTextFlipper(contentList: List<String>) {
        for (content in contentList) {
            val textView = TextView(context).apply {
                text = content
                textSize = 14f
                ellipsize = TextUtils.TruncateAt.END
                maxLines = 1
                setTextColor(Color.parseColor("#FFFFFF"))
            }
            addView(textView)
        }
        if (contentList.size >= 2) {
            startFlipping()
        }
    }

}