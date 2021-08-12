package com.example.kotlindemo.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.style.ReplacementSpan
import com.example.kotlindemo.utils.dip2px

/**
 *  圆角背景
 */
class RadiusBackgroundSpan(var bgColor: Int, var textColor: Int = Color.parseColor("#000000"), var radius: Float) : ReplacementSpan() {

    private var mSize: Int = 0
    private var interval = dip2px(3f)

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        mSize = (paint.measureText(text, start, end) + 2 * radius).toInt()
        return mSize
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        paint.apply {
            color = bgColor
            isAntiAlias = true
        }
        val oval = RectF(x + interval / 2, y + paint.ascent(), x + mSize - interval / 2, y + paint.descent())
        canvas.drawRoundRect(oval, radius, radius, paint)
        canvas.drawText(text!!, start, end, x + radius, y.toFloat() , paint.apply { color = textColor })
    }
}