package com.example.kotlindemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.kotlindemo.utils.*
import kotlin.math.abs
import kotlin.properties.Delegates

/**
 *  汽车分界线标记工具
 */
class CarDivideMarkView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    companion object {
        private const val TAG = "CarDivideMarkView"

        private val TOUCH_AREA_WIDTH = 66.dp
        private val DIVIDE_LINE_WIDTH  = 2.dp
        private val DIVIDE_LINE_SEGMENT = 3.dp
        private val DIVIDE_DARK_COLOR = Color.parseColor("#4D000000")
        private val DIVIDE_LINE_COLOR = Color.parseColor("#FFFF00")
        private val DIVIDE_LINE_SLIDER_COLOR = Color.parseColor("#FFFFFF")
        private val DIVIDE_LINE_SLIDER_BORDER_COLOR = Color.parseColor("#000000")
    }

    /**
     *  分界线方向, 默认: VERTICAL,即滑块横向移动
     */
    enum class DivideDirection {
        HORIZONTAL,
        VERTICAL
    }

    private val isVertical
        get() = divideOrientation == DivideDirection.VERTICAL

    /**
     *  分割线虚线样式
     */
    private val divideLinePathEffect
        get() = DashPathEffect(
            floatArrayOf(DIVIDE_LINE_SEGMENT, DIVIDE_LINE_SEGMENT, DIVIDE_LINE_SEGMENT, DIVIDE_LINE_SEGMENT),
            0f)

    private val divideDarkPaint = Paint()
    private val divideLinePaint = Paint()
    private val divideLineSliderPaint = Paint()

    private var previousSliderX by Delegates.notNull<Float>()
    private var previousSliderY by Delegates.notNull<Float>()
    private var currentSliderX by Delegates.notNull<Float>()
    private var currentSliderY by Delegates.notNull<Float>()
    private var originSliderWidth by Delegates.notNull<Float>()
    private var originSliderHeight by Delegates.notNull<Float>()

    private lateinit var sliderRect: RectF
    private lateinit var touchArea: TouchArea

    private var divideOrientation = DivideDirection.VERTICAL

    init {
        initPaint()
        initDrawParams()
    }

    private fun initPaint() {
        setCommonPaintAttributes(
            divideDarkPaint,
            divideLinePaint,
            divideLineSliderPaint,
        )

        divideDarkPaint.run {
            style = Paint.Style.FILL
            color = DIVIDE_DARK_COLOR
        }

        divideLinePaint.run {
            style = Paint.Style.STROKE
            color = DIVIDE_LINE_COLOR
            strokeWidth = DIVIDE_LINE_WIDTH
            divideLinePaint.pathEffect = divideLinePathEffect
        }
    }

    private fun initDrawParams() {
        val screenSize = getScreenSize(context)

        val halfWidth = screenSize.x / 2
        val halfHeight = screenSize.y / 2

        currentSliderX = halfWidth.toFloat()
        currentSliderY = halfHeight.toFloat()

        if (isVertical) {
            originSliderWidth = 5.dp
            originSliderHeight = 33.dp
        } else {
            originSliderWidth = 33.dp
            originSliderHeight = 5.dp
        }

        updateSlideRect()
    }

    private fun setCommonPaintAttributes(vararg paint: Paint) {
        paint.forEach {
            it.isAntiAlias = true
            it.flags = Paint.ANTI_ALIAS_FLAG
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawDarkBackground(canvas)
        drawDivideLine(canvas)
        drawDivideLineSlider(canvas)
    }

    /**
     *  绘制暗色背景
     */
    private fun drawDarkBackground(canvas: Canvas?) {
        if (isVertical) {
            canvas?.drawRect(currentSliderX, 0f, width.toFloat(), height.toFloat(), divideDarkPaint)
        } else {
            canvas?.drawRect(0f, currentSliderY, width.toFloat(), height.toFloat(), divideDarkPaint)
        }
    }

    /**
     *  绘制分界线
     */
    private fun drawDivideLine(canvas: Canvas?) {
        if (isVertical) {
            canvas?.drawLine(currentSliderX, 0f, currentSliderX, height.toFloat(), divideLinePaint)
        } else {
            canvas?.drawLine(0f, currentSliderY, width.toFloat(), currentSliderY, divideLinePaint)
        }
    }

    /**
     *  绘制分界线滑块及滑块边框
     */
    private fun drawDivideLineSlider(canvas: Canvas?) {
        divideLineSliderPaint.run {
            style = Paint.Style.FILL
            color = DIVIDE_LINE_SLIDER_COLOR
        }
        canvas?.drawRect(sliderRect, divideLineSliderPaint)

        divideLineSliderPaint.run {
            style = Paint.Style.STROKE
            color = DIVIDE_LINE_SLIDER_BORDER_COLOR
        }
        canvas?.drawRect(sliderRect, divideLineSliderPaint)
    }


    /**
     *  控制滑块移动
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (!touchArea.contains(event.x, event.y)) {
                    return false
                }
                previousSliderX = event.x
                previousSliderY = event.y
            }

            MotionEvent.ACTION_MOVE -> {

                if (isVertical) {
                    currentSliderX += event.x - previousSliderX
                } else {
                    currentSliderY += event.y - previousSliderY
                }

                previousSliderX = event.x
                previousSliderY = event.y

                updateSlideRect()
                invalidate()
            }
        }
        return true
    }

    /**
     *  更新滑块Rect
     */
    private fun updateSlideRect() {
        val sliderLeft = currentSliderX - originSliderWidth / 2
        val sliderTop = currentSliderY - originSliderHeight / 2
        val sliderRight = currentSliderX + originSliderWidth / 2
        val sliderBottom = currentSliderY + originSliderHeight / 2

        val touchLeft = currentSliderX - TOUCH_AREA_WIDTH / 2
        val touchTop = currentSliderY - TOUCH_AREA_WIDTH / 2
        val touchRight = currentSliderX + TOUCH_AREA_WIDTH / 2
        val touchBottom = currentSliderY + TOUCH_AREA_WIDTH / 2

        sliderRect = RectF(sliderLeft, sliderTop, sliderRight, sliderBottom)
        touchArea = TouchArea(touchLeft, touchRight, touchTop, touchBottom)
    }

}