package com.example.kotlindemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.kotlindemo.R
import kotlin.properties.Delegates

/**
 *  分段进度条
 */
class SegmentProgressBar @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr){

    companion object {
        private const val TAG = "SegmentProgressBar"
        private const val DEFAULT_CURRENT_PROGRESS = 0
        private const val DEFAULT_MAX_PROGRESS = 0
        private const val DEFAULT_WIDTH = 720f
        private const val DEFAULT_HEIGHT = 0f
        private val DEFAULT_PROGRESS_COLOR = Color.parseColor("#00C87A")
        private val DEFAULT_PROGRESS_BACKGROUND_COLOR = Color.parseColor("#242F35")
        private val DEFAULT_PROGRESS_SEGMENT_COLOR = Color.parseColor("#90A7B23")
        private val DEFAULT_PROGRESS_INTERVAL_COLOR = Color.parseColor("#000000")
    }

    private var currentProgress by Delegates.notNull<Int>()
    private var maxProgress by Delegates.notNull<Int>()
    private var progressBarWidth by Delegates.notNull<Float>()
    private var progressBarHeight by Delegates.notNull<Float>()

    private var progressColor by Delegates.notNull<Int>()
    private var progressBackgroundColor by Delegates.notNull<Int>()
    private var progressSegmentColor by Delegates.notNull<Int>()
    private var progressInterValColor by Delegates.notNull<Int>()

    private var startDrawX = 0f
    private var startDrawY = 0f

    private val progressPaint = Paint()
    private val progressBackGroundPaint = Paint()
    private val progressSegmentPaint = Paint()
    private val progressIntervalPaint = Paint()

    private var progressWidth = 0f
    private var progressSegmentWidth = 0f
    private var progressSize = 0                //完成数
    private var progressSegmentSize = 0         //解锁数
    private var progressAllSize = 0             //分段数
    private var eachSegmentWidth = 0f           //每段长度

    init {
        initAttributes()
        initPaint()
    }

    private fun initAttributes() {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.SegmentProgressBar, defStyleAttr, 0)

        currentProgress = attributes.getInteger(R.styleable.SegmentProgressBar_progressBar_progressValue, DEFAULT_CURRENT_PROGRESS)
        maxProgress = attributes.getInteger(R.styleable.SegmentProgressBar_progressBar_maxValue, DEFAULT_MAX_PROGRESS)
        progressBarWidth = attributes.getDimension(R.styleable.SegmentProgressBar_progressBar_width, DEFAULT_WIDTH)
        progressBarHeight = attributes.getDimension(R.styleable.SegmentProgressBar_progressBar_width, DEFAULT_HEIGHT)

        progressColor = attributes.getColor(R.styleable.SegmentProgressBar_progressBar_progressColor, DEFAULT_PROGRESS_COLOR)
        progressBackgroundColor = attributes.getColor(R.styleable.SegmentProgressBar_progressBar_backgroundColor, DEFAULT_PROGRESS_BACKGROUND_COLOR)
        progressSegmentColor = attributes.getColor(R.styleable.SegmentProgressBar_progressBar_segmentColor, DEFAULT_PROGRESS_SEGMENT_COLOR)
        progressInterValColor= attributes.getColor(R.styleable.SegmentProgressBar_progressBar_intervalColor, DEFAULT_PROGRESS_INTERVAL_COLOR)

        attributes.recycle()
    }

    private fun initPaint() {
        setCommonPaintAttributes(progressPaint, progressBackGroundPaint, progressSegmentPaint, progressIntervalPaint)
        progressPaint.color = progressColor
        progressBackGroundPaint.color = progressBackgroundColor
        progressSegmentPaint.color = progressSegmentColor
        progressIntervalPaint.apply {
            color = progressInterValColor
            strokeWidth = 1f
        }
    }

    private fun setCommonPaintAttributes(vararg paint: Paint) {
        paint.forEach {
            it.isAntiAlias = true
            it.flags = Paint.ANTI_ALIAS_FLAG
            it.style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //绘制背景
        canvas?.drawRect(startDrawX, startDrawY, progressBarWidth, progressBarHeight, progressPaint)
        //绘制分段
        canvas?.drawRect(startDrawX, startDrawY, progressSegmentWidth, progressBarHeight, progressSegmentPaint)
        //绘制进度
        canvas?.drawRect(startDrawX, startDrawY, progressWidth, progressBarHeight, progressPaint)
        //绘制间隔
        startDrawX = eachSegmentWidth
        for(i in 0 until progressAllSize - 1) {
            canvas?.drawLine(startDrawX, startDrawY, startDrawX, startDrawY + (progressBarHeight - startDrawY), progressIntervalPaint)
            startDrawX += eachSegmentWidth
        }
        startDrawX = 0f
    }

    fun setProgress(progressSize: Int, segmentSize: Int = 0, allSize: Int = 0) {
        if (segmentSize > allSize || progressSize > allSize) {
            return
        }
        progressAllSize = allSize
        eachSegmentWidth = progressBarWidth / allSize
        progressWidth = eachSegmentWidth * progressSize
        progressSegmentWidth = eachSegmentWidth * segmentSize
    }

}