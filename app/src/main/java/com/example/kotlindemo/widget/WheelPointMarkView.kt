package com.example.kotlindemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Magnifier
import com.example.kotlindemo.utils.TouchArea
import com.example.kotlindemo.utils.contains
import com.example.kotlindemo.utils.dp
import com.example.kotlindemo.utils.getScreenSize
import kotlin.math.abs

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

        private const val CLICK_INTERVAL = 100

        private val DIVIDE_LINE_SEGMENT = 3.dp
        private val DIVIDE_LINE_INTERVAL = 2.dp
        private val LINE_WIDTH_1_DP = 1.dp
        private val LINE_WIDTH_2_DP = 2.dp
        private val RAY_LENGTH = 160.dp
        private val MARK_CIRCLE_RADIUS = 17.dp
        private val MARK_POINT_RADIUS = 2.5.dp
        private val MAGNIFIER_RADIUS = 43.dp
        private val MAGNIFIER_MARGIN = 45.dp
        private val INITIALIZER_TOUCH_POINT_MARGIN = 90.dp

        private val RAY_START_COLOR = Color.parseColor("#E6E316")
        private val RAY_END_COLOR = Color.parseColor("#00000000")
        private val GUIDE_LINE_COLOR = Color.parseColor("#FFFF00")
        private val MARK_CIRCLE_COLOR = Color.parseColor("#000000")
    }

    private val leftRayPaint = Paint()
    private val rightRayPaint = Paint()
    private val guideLinePaint = Paint()
    private val markCirclePaint = Paint()
    private val markPointPaint = Paint()
    private val magnifierPaint = Paint()
    private val magnifierBorderPaint = Paint()

    private var leftMarkPoint: PointF? = null
    private var rightMarkPoint: PointF? = null
    private lateinit var leftCenterPoint: PointF
    private lateinit var rightCenterPoint: PointF

    private lateinit var leftTouchArea: TouchArea
    private lateinit var rightTouchArea: TouchArea

    val magnifierXfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    var magnifierBitmap: Bitmap? = null
        set(value) {
            field = value
            invalidate()
        }

    private var bitmapShader: BitmapShader? = null
    private var bitmapMatrix = Matrix()

    private var eventDownX = 0f
    private var eventDownY = 0f

    private var downTime = 0L
    private var isMoving = false
    private var isGuideLineParallelY = false

    private val markPointList
        get() = listOf(leftMarkPoint, rightMarkPoint)

    /**
     *  分割线虚线样式
     */
    private val divideLinePathEffect
        get() = DashPathEffect(
            floatArrayOf(DIVIDE_LINE_SEGMENT, DIVIDE_LINE_INTERVAL, DIVIDE_LINE_SEGMENT, DIVIDE_LINE_INTERVAL),
            0f)

    /**
     *  打点判断
     */
    private val timeToMark
        get() = System.currentTimeMillis() - downTime < CLICK_INTERVAL

    private val canMarkLeftPoint
        get() = leftMarkPoint == null && leftTouchArea.contains(eventDownX, eventDownY)

    private val canMarkRightPoint
        get() = rightMarkPoint == null && rightTouchArea.contains(eventDownX, eventDownY)

    init {
        initParams()
        initPaint()
    }

    private fun initParams() {
        val screenSize = getScreenSize(context)
        val halfHeight = screenSize.y.toFloat() / 2

        leftCenterPoint = PointF(INITIALIZER_TOUCH_POINT_MARGIN, halfHeight)
        rightCenterPoint = PointF(screenSize.x - INITIALIZER_TOUCH_POINT_MARGIN, halfHeight)

        updateTouchArea()
    }

    private fun initPaint() {
        setCommonPaintAttributes(
            leftRayPaint,
            rightRayPaint,
            guideLinePaint,
            markCirclePaint,
            markPointPaint,
            magnifierBorderPaint
        )

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

        magnifierPaint.run {
            color = GUIDE_LINE_COLOR
            pathEffect = divideLinePathEffect
            strokeWidth = LINE_WIDTH_1_DP
        }

        magnifierBorderPaint.run {
            color = MARK_CIRCLE_COLOR
            pathEffect = divideLinePathEffect
            strokeWidth = LINE_WIDTH_1_DP
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
        drawMagnifier(canvas)
        drawMarkPoint(canvas, leftMarkPoint)
        drawMarkPoint(canvas, rightMarkPoint)
    }

    /**
     *  绘制射线
     */
    private fun drawRay(canvas: Canvas?) {
        val startLeftX = leftCenterPoint.x
        val startLeftY = leftCenterPoint.y
        val endLeftX = leftCenterPoint.x
        val endLeftY = leftCenterPoint.y - RAY_LENGTH
        val leftPaint = leftRayPaint.apply { shader = getRayColorShader(startLeftX, startLeftY) }
        canvas?.drawLine(startLeftX, startLeftY, endLeftX, endLeftY, leftPaint)

        val startRightX = rightCenterPoint.x
        val startRightY = rightCenterPoint.y
        val endRightX = rightCenterPoint.x
        val endRightY = rightCenterPoint.y - RAY_LENGTH
        val rightPaint = rightRayPaint.apply { shader = getRayColorShader(startRightX, startRightY) }
        canvas?.drawLine(startRightX, startRightY, endRightX, endRightY, rightPaint)
    }

    /**
     *  绘制引导线
     */
    private fun drawGuideLine(canvas: Canvas?) {
        val intersection = getAxisIntersection()

        Log.i(TAG, " " +
                "x1= ${intersection.xAxis.x} " +
                "y1= ${intersection.xAxis.y} " +
                "x2= ${intersection.yAxis.x} " +
                "y2= ${intersection.yAxis.y}")

        val startX = intersection.xAxis.x
        val startY = intersection.xAxis.y
        val endX = intersection.yAxis.x
        val endY = intersection.yAxis.y

        canvas?.drawLine(startX, startY, endX, endY, guideLinePaint)
    }

    /**
     *  绘制打点圆圈
     *  左边，右边是以初始化时打点圆圈的位置决定
     */
    private fun drawMarkCircle(canvas: Canvas?) {
        //绘制左边的打点圆圈
        if (leftMarkPoint == null) {
            canvas?.drawCircle(
                leftCenterPoint.x,
                leftCenterPoint.y,
                MARK_CIRCLE_RADIUS,
                markCirclePaint
            )
        }

        //绘制右边的打点圆圈
        if (rightMarkPoint == null) {
            canvas?.drawCircle(
                rightCenterPoint.x,
                rightCenterPoint.y,
                MARK_CIRCLE_RADIUS,
                markCirclePaint
            )
        }
    }

    /**
     * 绘制打点
     */
    private fun drawMarkPoint(canvas: Canvas?, point: PointF?) {
        if (point == null) {
            return
        }
        markPointPaint.run {
            style = Paint.Style.FILL
            color = GUIDE_LINE_COLOR
        }
        canvas?.drawCircle(point.x, point.y, MARK_POINT_RADIUS, markPointPaint)

        markPointPaint.run {
            style = Paint.Style.STROKE
            color = MARK_CIRCLE_COLOR
        }
        canvas?.drawCircle(point.x, point.y, MARK_POINT_RADIUS,markPointPaint)
    }

    /**
     *  绘制放大镜
     *  先以手指触摸点为中心点绘制放大镜，然后移动到右上角
     */
    private fun drawMagnifier(canvas: Canvas?) {
        if (isMoving && magnifierBitmap != null) {
            val magnifierCenterX = when {
                canMarkLeftPoint -> leftCenterPoint.x
                canMarkRightPoint -> rightCenterPoint.x
                else -> -1f
            }

            val magnifierCenterY = when {
                canMarkLeftPoint -> leftCenterPoint.y
                canMarkRightPoint -> rightCenterPoint.y
                else -> -1f
            }

            if (magnifierCenterX == -1f || magnifierCenterY == -1f) {
                return
            }

            bitmapMatrix.setScale(2f, 2f, magnifierCenterX, magnifierCenterY)
            bitmapMatrix.postTranslate(- MAGNIFIER_MARGIN, - MAGNIFIER_MARGIN)

            bitmapShader = BitmapShader(magnifierBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            bitmapShader!!.setLocalMatrix(bitmapMatrix)

            magnifierPaint.shader = bitmapShader

            //xfermode绘制开始
            val saved = canvas?.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)

            canvas?.drawCircle(
                magnifierCenterX - MAGNIFIER_MARGIN,
                magnifierCenterY - MAGNIFIER_MARGIN,
                MAGNIFIER_RADIUS,
                magnifierPaint)

            magnifierPaint.shader = null
            magnifierPaint.xfermode = magnifierXfermode

            val intersection = getAxisIntersection()
            val startX = intersection.xAxis.x - MAGNIFIER_MARGIN
            val startY = intersection.xAxis.y - MAGNIFIER_MARGIN
            val endX = intersection.yAxis.x - MAGNIFIER_MARGIN
            val endY = intersection.yAxis.y - MAGNIFIER_MARGIN

            canvas?.drawLine(startX, startY, endX, endY, magnifierPaint)
            magnifierPaint.xfermode = null

            canvas?.restoreToCount(saved!!)
            //xfermode绘制结束

            canvas?.drawCircle(
                magnifierCenterX - MAGNIFIER_MARGIN,
                magnifierCenterY - MAGNIFIER_MARGIN,
                MAGNIFIER_RADIUS,
                magnifierBorderPaint)
        }
    }

    /**
     *  控制打点移动
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                eventDownX = event.x
                eventDownY = event.y
                downTime = System.currentTimeMillis()
            }

            MotionEvent.ACTION_MOVE -> {
                if (!timeToMark) {
                    if (canMarkLeftPoint)  {
                        leftCenterPoint.x += event.x - eventDownX
                        leftCenterPoint.y += event.y - eventDownY
                    } else if (canMarkRightPoint) {
                        rightCenterPoint.x += event.x - eventDownX
                        rightCenterPoint.y += event.y - eventDownY
                    }

                    isMoving = true
                    eventDownX = event.x
                    eventDownY = event.y

                    updateTouchArea()
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (timeToMark) {
                    if (canMarkLeftPoint) {
                        leftMarkPoint = leftCenterPoint
                    } else if (canMarkRightPoint) {
                        rightMarkPoint = rightCenterPoint
                    }
                }

                isMoving = false
                invalidate()
            }
        }

        return true
    }

    /**
     *  更新触碰区域
     */
    private fun updateTouchArea() {
        leftTouchArea = TouchArea(
            leftCenterPoint.x - MARK_CIRCLE_RADIUS,
            leftCenterPoint.x + MARK_CIRCLE_RADIUS,
            leftCenterPoint.y - MARK_CIRCLE_RADIUS,
            leftCenterPoint.y + MARK_CIRCLE_RADIUS
        )

        rightTouchArea = TouchArea(
            rightCenterPoint.x - MARK_CIRCLE_RADIUS,
            rightCenterPoint.x + MARK_CIRCLE_RADIUS,
            rightCenterPoint.y - MARK_CIRCLE_RADIUS,
            rightCenterPoint.y + MARK_CIRCLE_RADIUS
        )
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

    /**
     *  获取引导线和XY轴点交点坐标
     */
    private fun getAxisIntersection(): Intersection {
        val x1 = leftCenterPoint.x
        val y1 = - leftCenterPoint.y
        val x2 = rightCenterPoint.x
        val y2 = - rightCenterPoint.y

        isGuideLineParallelY = false

        //引导线和Y轴平行
        if (x2 - x1 == 0f) {
            isGuideLineParallelY = true
            return Intersection(
                PointF(x1, 0f),
                PointF(x1, height.toFloat())
            )
        }

        val k = (y2 - y1) / (x2 -x1)
        val b = y1 - x1 * k

        //引导线和X轴平行
        if (k == 0f) {
            return Intersection(
                PointF(0f, abs(b)),
                PointF(width.toFloat(), abs(b))
            )
        }

        //当线段趋近于和坐标轴平行，此时x,y坐标趋近于无穷大，需要限制坐标范围
        //斜率为正(与X轴正方向和Y轴负方向相交)
        return if (k > 0) {
            var x3 = abs(b / k)
            var y3 = 0f
            var x4 = 0f
            var y4 = abs(b)

            if (x3 > width.toFloat()) {
                x3 = width.toFloat()
                y3 = abs(x3 * k + b)
            }

            if (y4 > height.toFloat()) {
                y4 = height.toFloat()
                x4 = abs((-y4 - b) / k)
            }
            Intersection(PointF(x3, y3), PointF(x4, y4))
        }
        //斜率为负(与X轴和X轴上横坐标为width的垂线相交)
        else {
            var x5 = - b / k
            var y5 = 0f
            var x6 = width.toFloat()
            var y6 = abs(k * width.toFloat() + b)

            if (x5 < 0f) {
                x5 = 0f
                y5 = abs(b)
            }

            if (y6 > height.toFloat()) {
                y6 = height.toFloat()
                x6 = abs((-y6 - b) / k)
            }
            Intersection(PointF(x5, y5), PointF(x6, y6))
        }
    }

    /**
     *  x: 与X轴相交的x坐标
     *  y: 与Y轴相交的Y坐标
     */
    data class Intersection (
        val xAxis: PointF,
        val yAxis: PointF
    )

}