package com.example.kotlindemo.task.appbar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.databinding.DataBindingUtil
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.CampusRoundImageBinding

/**
 * @Description 校园轮播条
 * @Author LuoJia
 * @Date 2022-10-12
 */
class CampusFlipper @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
) : ViewFlipper(context, attr) {

    private val interval = 1000

    private fun setCommonParams() {
        flipInterval = interval
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_in)
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_marquee_out)
    }

    fun startImageFlipper(iconList: List<Int>) {
        setCommonParams()
        for (icon in iconList) {
            val layout = DataBindingUtil.inflate<CampusRoundImageBinding>(LayoutInflater.from(context), R.layout.campus_round_image, this, false)
            addView(layout.root)
        }
//        startFlipping()
    }

    fun startTextFlipper(contentList: List<String>) {
        setCommonParams()
        for (content in contentList) {
            val textView = TextView(context).apply {
                text = content
                textSize = 14f
                includeFontPadding = false
                setTextColor(Color.parseColor("#222222"))
            }
            addView(textView)
        }
//        startFlipping()
    }

}