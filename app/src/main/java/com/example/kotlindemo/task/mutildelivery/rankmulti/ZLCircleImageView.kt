package com.example.kotlindemo.task.mutildelivery.rankmulti

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.core.view.setPadding
import com.example.kotlindemo.R
import com.example.kotlindemo.utils.dp
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * @Description
 * @Author
 * @Date
 */
class ZLCircleImageView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
)  : ShapeableImageView(context, attr, defStyleAttr){

    override fun onDraw(canvas: Canvas?) {
        try {
            if (width != 0 && height != 0 && width == height) {
                shapeAppearanceModel = ShapeAppearanceModel
                    .builder()
                    .setAllCorners(CornerFamily.ROUNDED, width.toFloat() / 2)
                    .build()
            }
            super.onDraw(canvas)
        } catch (e: Exception) {

        }
    }

    /**
     * 设置边框颜色
     */
    fun setBorderColor(color: Int) {
        setStrokeColorResource(color)
    }

    /**
     * 设置边框宽
     */
    fun setBorderWidth(width: Float) {
        strokeWidth = width
    }

    /**
     * 设置边距
     */
    fun setAllPadding(padding: Int) {
        setPadding(padding, padding, padding, padding)
    }

}