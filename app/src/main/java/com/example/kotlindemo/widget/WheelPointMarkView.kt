package com.example.kotlindemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.kotlindemo.utils.TouchArea
import com.example.kotlindemo.utils.dp
import com.example.kotlindemo.utils.getScreenSize

/**
 *  车轮打点工具
 */
class WheelPointMarkView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

    companion object {
        private const val TAG = "WheelPointMarkView"

        private val DIVIDE_LINE_SEGMENT = 3.dp
        private val DIVIDE_LINE_INTERVAL = 2.dp
        private val LINE_WIDTH_1_DP = 1.dp
        private val LINE_WIDTH_2_DP = 2.dp
        private val RAY_LENGTH = 160.dp
        private val MARK_CIRCLE_RADIUS = 17.dp
        private val INITIALIZER_TOUCH_POINT_MARGIN = 90.dp
        private val RAY_START_COLOR = Color.parseColor("#e6e316")
        private val RAY_END_COLOR = Color.parseColor("#00000000")
        private val GUIDE_LINE_COLOR = Color.parseColor("#ffff00")
        private val MARK_CIRCLE_COLOR = Color.parseColor("#000000")
    }

    private val leftRayPaint = Paint()
    private val rightRayPaint = Paint()
    private val guideLinePaint = Paint()
    private val markCirclePaint = Paint()

    private var previousLeftTouchPoint = Point()
    private var previousRightTouchPoint = Point()
    private lateinit var currentLeftTouchPoint: Point
    private lateinit var currentRightTouchPoint: Point

    private lateinit var leftTouchArea: TouchArea
    private lateinit var rightTouchArea: TouchArea

    private val markPointList = emptyList<Point>()

    /**
     *  分割线虚线样式
     */
    private val divideLinePathEffect
        get() = DashPathEffect(
            floatArrayOf(DIVIDE_LINE_SEGMENT, DIVIDE_LINE_INTERVAL, DIVIDE_LINE_SEGMENT, DIVIDE_LINE_INTERVAL),
            0f)

    init {
        initParams()
        initPaint()
    }

    private fun initParams() {
        val screenSize = getScreenSize(context)
        val halfHeight = screenSize.y / 2

        currentLeftTouchPoint = Point(INITIALIZER_TOUCH_POINT_MARGIN.toInt(), halfHeight)
        currentRightTouchPoint = Point((screenSize.x - INITIALIZER_TOUCH_POINT_MARGIN).toInt(), halfHeight)

        Log.i(TAG, " screenWidth= ${screenSize.x}, screenHeight= ${screenSize.y}")
    }

    private fun initPaint() {
        setCommonPaintAttributes(leftRayPaint, rightRayPaint, guideLinePaint, markCirclePaint)
        leftRayPaint.run {
            strokeWidth = LINE_WIDTH_1_DP
        }

        rightRayPaint.run {
            strokeWidth = LINE_WIDTH_1_DP
        }

        guideLinePaint.run {
            color = GUIDE_LINE_COLOR
            pathEffect = divideLinePathEffect
            strokeWidth = LINE_WIDTH_2_DP
        }

        markCirclePaint.run {
            color = MARK_CIRCLE_COLOR
            strokeWidth = LINE_WIDTH_2_DP
        }
    }

    private fun setCommonPaintAttributes(vararg paint: Paint) {
        paint.forEach {
            it.isAntiAlias = true
            it.flags = Paint.ANTI_ALIAS_FLAG
            it.style = Paint.Style.STROKE
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawRay(canvas)
        drawGuideLine(canvas)
        drawMarkCircle(canvas)
    }

    /**
     *  绘制射线
     */
    private fun drawRay(canvas: Canvas?) {
        val startLeftX = currentLeftTouchPoint.x.toFloat()
        val startLeftY = currentLeftTouchPoint.y.toFloat()
        val endLeftX = currentLeftTouchPoint.x.toFloat()
        val endLeftY = currentLeftTouchPoint.y - RAY_LENGTH
        val leftPaint = leftRayPaint.apply { shader = getRayColorShader(startLeftX, startLeftY) }
        canvas?.drawLine(startLeftX, startLeftY, endLeftX, endLeftY, leftPaint)

        val startRightX = currentRightTouchPoint.x.toFloat()
        val startRightY = currentRightTouchPoint.y.toFloat()
        val endRightX = currentRightTouchPoint.x.toFloat()
        val endRightY = currentRightTouchPoint.y.toFloat() - RAY_LENGTH
        val rightPaint = rightRayPaint.apply { shader = getRayColorShader(startRightX, startRightY) }
        canvas?.drawLine(startRightX, startRightY, endRightX, endRightY, rightPaint)
    }

    /**
     *  绘制引导线
     *  两点确定一条直线方程式，算出引导线起点和终点
     *  y = kx + b
     */
    private fun drawGuideLine(canvas: Canvas?) {
        val startPointX = 0f
        val startPointY = currentLeftTouchPoint.y.toFloat()
        val endPointX = width.toFloat()
        val endPointY = currentLeftTouchPoint.y.toFloat()
        canvas?.drawLine(startPointX, startPointY, endPointX, endPointY, guideLinePaint)
    }

    /**
     *  绘制打点圆圈
     *  左边，右边是以初始化时打点圆圈的位置决定
     */
    private fun drawMarkCircle(canvas: Canvas?) {
        //绘制左边的打点圆圈
        canvas?.drawCircle(
            currentLeftTouchPoint.x.toFloat(),
            currentLeftTouchPoint.y.toFloat(),
            MARK_CIRCLE_RADIUS,
            markCirclePaint
        )

        //绘制右边的打点圆圈
        canvas?.drawCircle(
            currentRightTouchPoint.x.toFloat(),
            currentRightTouchPoint.y.toFloat(),
            MARK_CIRCLE_RADIUS,
            markCirclePaint
        )

        Log.i(TAG, "viewWidth= $width, viewHeight= $height")
    }

    /**
     *  控制打点移动
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {

            }

            MotionEvent.ACTION_MOVE -> {

            }

            MotionEvent.ACTION_UP -> {

            }
        }

        return true
    }

    /**
     *  获取射线颜色Shader
     */
    private fun getRayColorShader(startX: Float, startY: Float) = LinearGradient(
        startX,
        startY,
        startX,
        startY - RAY_LENGTH,
        RAY_START_COLOR,
        RAY_END_COLOR,
        Shader.TileMode.CLAMP
    )

}