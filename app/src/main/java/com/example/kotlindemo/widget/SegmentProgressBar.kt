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
        private const val DEFAULT_PROGRESS_SIZE = 0
        private const val DEFAULT_CURRENT_PROGRESS = 0
        private const val DEFAULT_SEGMENT_PROGRESS = 0
        private const val DEFAULT_MAX_PROGRESS = 100
        private const val DEFAULT_WIDTH = 720f
        private const val DEFAULT_HEIGHT = 0f
        private val DEFAULT_PROGRESS_COLOR = Color.parseColor("#00C87A")
        private val DEFAULT_PROGRESS_BACKGROUND_COLOR = Color.parseColor("#242F35")
        private val DEFAULT_PROGRESS_SEGMENT_COLOR = Color.parseColor("#90A7B2")
        private val DEFAULT_PROGRESS_INTERVAL_COLOR = Color.parseColor("#000000")
    }

    //分段数
    private var progressSize by Delegates.notNull<Int>()

    private var currentProgress by Delegates.notNull<Int>()
    private var segmentProgress by Delegates.notNull<Int>()
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

    init {
        initAttributes()
        initPaint()
    }

    private fun initAttributes() {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.SegmentProgressBar, defStyleAttr, 0)

        progressSize = attributes.getInteger(R.styleable.SegmentProgressBar_progressBar_size, DEFAULT_PROGRESS_SIZE)

        currentProgress = attributes.getInteger(R.styleable.SegmentProgressBar_progressBar_progressValue, DEFAULT_CURRENT_PROGRESS)
        segmentProgress = attributes.getInteger(R.styleable.SegmentProgressBar_progressBar_segmentValue, DEFAULT_SEGMENT_PROGRESS)
        maxProgress = attributes.getInteger(R.styleable.SegmentProgressBar_progressBar_maxValue, DEFAULT_MAX_PROGRESS)

        progressBarWidth = attributes.getDimension(R.styleable.SegmentProgressBar_progressBar_width, DEFAULT_WIDTH)
        progressBarHeight = attributes.getDimension(R.styleable.SegmentProgressBar_progressBar_height, DEFAULT_HEIGHT)

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
        canvas?.drawRect(
            startDrawX,
            startDrawY,
            progressBarWidth,
            progressBarHeight,
            progressBackGroundPaint)
        //绘制分段
        canvas?.drawRect(
            startDrawX,
            startDrawY,
            segmentProgress.toFloat() / maxProgress.toFloat() * progressBarWidth,
            progressBarHeight, progressSegmentPaint)
        //绘制进度
        canvas?.drawRect(
            startDrawX,
            startDrawY,
            currentProgress.toFloat() / maxProgress.toFloat() * progressBarWidth,
            progressBarHeight, progressPaint)
        //绘制间隔
        startDrawX = progressBarWidth / progressSize
        for(i in 0 until progressSize - 1) {
            canvas?.drawLine(
                startDrawX,
                startDrawY,
                startDrawX,
                startDrawY + (progressBarHeight - startDrawY), progressIntervalPaint)
            startDrawX += progressBarWidth / progressSize
        }
        startDrawX = 0f
    }

    fun setProgress(currentProgress: Int = 0, segmentProgress: Int = 0, progressSize: Int = DEFAULT_PROGRESS_SIZE) {
        if (currentProgress > maxProgress || segmentProgress > maxProgress) {
            return
        }
        this.currentProgress = currentProgress
        this.segmentProgress = segmentProgress
        this.progressSize = progressSize

        invalidate()
    }

}