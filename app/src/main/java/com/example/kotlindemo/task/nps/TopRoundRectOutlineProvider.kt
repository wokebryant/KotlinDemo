package com.example.kotlindemo.task.nps

import android.graphics.Outline
import android.graphics.Rect
import android.view.View
import android.view.ViewOutlineProvider

class TopRoundRectOutlineProvider(private val cornerRadius: Float) : ViewOutlineProvider() {
    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            0, 0, view.width, view.height + cornerRadius.toInt(), cornerRadius)
    }
}