package com.example.kotlindemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.*
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import com.example.kotlindemo.R
import com.example.kotlindemo.utils.dip2px
import kotlinx.android.synthetic.main.activity_motion.view.*

/**
 *  可折叠文本
 */
class FoldTextView @JvmOverloads constructor(
        context: Context,
        private val attributeSet: AttributeSet? = null,
        private val defStyleAttr: Int = 0)
    : AppCompatTextView(context, attributeSet, defStyleAttr) {

    companion object {
        private const val ELLIPSIZE_END = "..."
        private const val MAX_LINE = 2
        private const val EXPAND_TIP_TEXT = "  收起全文"
        private const val FOLD_TIP_TEXT = "全文"
        private const val TIP_COLOR = -0x1
        private const val TAG_COLOR = -0x1
        private val BEHAVIOR_COLOR = Color.parseColor("#ffff00")
        private val TARGET_COLOR = Color.parseColor("#01ffce")
        private const val END = 0
    }

    /**
     *  Tag留白间隔
     */
    private val tagInterval = dip2px(6f)

    /**
     *  Tag圆角
     */
    private val tagCorner = dip2px(4f).toFloat()

    /**
     * 显示最大行数
     */
    private var mShowMaxLine: Int = 0
    /**
     * 折叠文本
     */
    var mFoldText: String? = ""

    /**
     * 展开文本
     */
    private var mExpandText: String? = ""
    /**
     * 原始文本
     */
    private var mOriginalText: String = ""
    /**
     * 是否展开
     */
    private var isExpand = false
    /**
     * 全文显示的位置 0末尾 1下一行
     */
    private var mTipGravity = 0
    /**
     * 提示文字颜色
     */
    private var mTipColor: Int = 0

    /**
     * 标签颜色
     */
    private var mTagColor: Int = 0

    /**
     * 提示是否可点击
     */
    private var mTipClickable = false
    private var flag = false
    private var mPaint: Paint = Paint()

    /**
     *  提示是否展示(不展示的话此时相当于普通的TextView)
     */
    var isTipShow = false

    /**
     * 展开后是否显示文字提示
     */
    private var isShowTipAfterExpand = false

    /**
     * 提示是否添加背景
     */
    var isTipWithTag = false

    /**
     * 提示文字坐标范围
     */
    private var minX: Float = 0f
    private var maxX: Float = 0f
    private var minY: Float = 0f
    private var maxY: Float = 0f

    /**
     * 收起全文不在同一行时，增加一个变量记录坐标
     */
    private var middleY: Float = 0f
    /**
     * 原始文本行数
     */
    private var originalLineCount = 0
    /**
     * 是否超过最大行数
     */
    private var isOverMaxLine = false

    /**
     * 点击时间
     */
    private var clickTime = 0L

    init {
        initAttributes()
        initPaint()
    }

    private fun initAttributes() {
        mShowMaxLine = MAX_LINE
        if (attributeSet != null) {
            val arr = context.obtainStyledAttributes(attributeSet, R.styleable.FoldTextView, defStyleAttr, 0)
            mShowMaxLine = arr.getInt(R.styleable.FoldTextView_showMaxLine, MAX_LINE)
            mTipGravity = arr.getInt(R.styleable.FoldTextView_tipGravity, END)
            mTipColor = arr.getColor(R.styleable.FoldTextView_tipColor, TIP_COLOR)
            mTagColor = arr.getColor(R.styleable.FoldTextView_tagColor, TAG_COLOR)
            mTipClickable = arr.getBoolean(R.styleable.FoldTextView_tipClickable, false)
            mFoldText = arr.getString(R.styleable.FoldTextView_foldText)
            mExpandText = arr.getString(R.styleable.FoldTextView_expandText)
            isTipShow = arr.getBoolean(R.styleable.FoldTextView_showTip, false)
            isShowTipAfterExpand = arr.getBoolean(R.styleable.FoldTextView_showTipAfterExpand, false)
            isTipWithTag = arr.getBoolean(R.styleable.FoldTextView_tipWithTag, false)
            arr.recycle()
        }
        if (TextUtils.isEmpty(mExpandText)) {
            mExpandText = EXPAND_TIP_TEXT
        }
        if (TextUtils.isEmpty(mFoldText)) {
            mFoldText = FOLD_TIP_TEXT
        }
    }

    private fun initPaint() {
        mPaint.apply {
            textSize = this@FoldTextView.textSize
            color = mTipColor
            isAntiAlias = true
            flags = Paint.ANTI_ALIAS_FLAG
            style = Paint.Style.FILL
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        if (TextUtils.isEmpty(text) || mShowMaxLine == 0) {
            super.setText(text, type)
        } else if (isExpand) {
            //文字展开
            val spannable = SpannableStringBuilder(mOriginalText)
            if (isShowTipAfterExpand) {
                spannable.append(mExpandText)
                spannable.setSpan(ForegroundColorSpan(mTipColor), spannable.length - mExpandText!!.length, spannable.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
            }
            super.setText(spannable, type)
            val mLieCount = lineCount
            val layout = layout
            minX = paddingLeft + layout.getPrimaryHorizontal(spannable.lastIndexOf(mExpandText!![0]) - 1)
            maxX = paddingLeft + layout.getPrimaryHorizontal(spannable.lastIndexOf(mExpandText!![mExpandText!!.length - 1]) + 1)
            val bound = Rect()
            layout.getLineBounds(originalLineCount - 1, bound)
            if (mLieCount > originalLineCount) {
                //不在同一行
                minY = (paddingTop + bound.top).toFloat()
                middleY = minY + paint.fontMetrics.descent - paint.fontMetrics.ascent
                maxY = middleY + paint.fontMetrics.descent - paint.fontMetrics.ascent
            } else {
                //同一行
                minY = (paddingTop + bound.top).toFloat()
                maxY = minY + paint.fontMetrics.descent - paint.fontMetrics.ascent
            }

        } else {
            if (!flag) {
                viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        viewTreeObserver.removeOnPreDrawListener(this)
                        flag = true
                        formatText(text, type)
                        return true
                    }
                })
            } else {
                formatText(text, type)
            }
        }
    }

    fun formatText(text: CharSequence?, type: BufferType?) {
        mOriginalText = text.toString()
        var l = layout
        if (l == null || !l.text.equals(mOriginalText)) {
            super.setText(mOriginalText, type)
            l = layout
        }
        if (l == null) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    translateText(layout, type)
                }
            })
        } else {
            translateText(l, type)
        }
    }

    fun translateText(l: Layout, type: BufferType?) {
        //记录原始行数
        originalLineCount = l.lineCount
        if (l.lineCount > mShowMaxLine) {
            isOverMaxLine = true
            val span = SpannableStringBuilder()
            //获取指定行的开始坐标
            val start = l.getLineStart(mShowMaxLine - 1)
            //获取指定行的结束坐标
            var end = l.getLineVisibleEnd(mShowMaxLine - 1)
            if (mTipGravity == END) {
                //减去尾部字符串的长度
                val builder = StringBuilder(ELLIPSIZE_END).append("  ").append(if(isTipShow) mFoldText else "")
                end -= paint.breakText(mOriginalText, start, end, false, paint.measureText(builder.toString()), null)
            } else {
                end--
            }
            //获取裁剪后的文本,如果显示标签的话，需要额外减去1，预留一个字符串的空间
            val ellipsize = if (isTipWithTag) mOriginalText.subSequence(0, end - 1) else mOriginalText.subSequence(0, end)
            //裁剪后的文本和...完成拼接
            span.append(ellipsize).append(ELLIPSIZE_END)
            if (mTipGravity != END) {
                span.append("\n")
            }
            super.setText(span, type)
        } else {
            //针对非裁剪情况下tag展示
            if (isTipWithTag) {
                showBackgroundSpan()
            }
        }
    }

    /**
     *  展示背景Span
     */
    private fun showBackgroundSpan() {
        val span = SpannableStringBuilder()
        val tagSpan = RadiusBackgroundSpan(mTagColor, mTipColor, tagCorner)
        mOriginalText += "  $mFoldText"
        val startIndex = mOriginalText.indexOf(mFoldText.toString())
        val endIndex = startIndex + mFoldText!!.length
        span.append(mOriginalText)
        span.setSpan(tagSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        super.setText(span, BufferType.NORMAL)
    }

    /**
     *  展示前景Span
     */
    private fun showForegroundSpan() {
        val span = SpannableStringBuilder()
        val textColorSpan = ForegroundColorSpan(BEHAVIOR_COLOR)
        val startIndex = 3
        val endIndex = 5
        span.append(mOriginalText)
        span.setSpan(textColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        super.setText(span, BufferType.NORMAL)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (isOverMaxLine && !isExpand) {
            //折叠
            if (mTipGravity == END) {
                minX = width - paddingLeft - paddingRight - paint.measureText(mFoldText)
                maxX = (width - paddingLeft - paddingRight).toFloat()
            } else {
                minX = paddingLeft.toFloat()
                maxX = minX + paint.measureText(mFoldText)
            }
            minY = height - (paint.fontMetrics.descent - paint.fontMetrics.ascent) - paddingBottom
            maxY = (height - paddingBottom).toFloat()

            if (isTipWithTag && isTipShow) {
                val tagRect = RectF(minX - tagInterval , minY , minX + paint.measureText(mFoldText) , maxY)
                canvas?.drawRoundRect(tagRect, tagCorner, tagCorner, mPaint.apply { color =  mTagColor})
                canvas?.drawText(mFoldText!!, minX - tagInterval / 2, height - paint.fontMetrics.descent - paddingBottom, mPaint.apply { color =  mTipColor})
            } else {
                mFoldText = if (isTipShow) mFoldText else ""
                canvas?.drawText(mFoldText!!, minX, height - paint.fontMetrics.descent - paddingBottom, mPaint)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (mTipClickable) {
            when (event?.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    clickTime = System.currentTimeMillis()
                    if (!isClickable && isInRange(event.x, event.y)) {
                        return true
                    }
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    val delTime = System.currentTimeMillis() - clickTime
                    clickTime = 0L
                    if (delTime < ViewConfiguration.getTapTimeout() && isInRange(event.x, event.y)) {
                        isExpand = !isExpand
                        text = mOriginalText
                        return true
                    }
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun isInRange(x: Float, y: Float): Boolean {
        return if (minX < maxX) {
            //同一行
            x in minX..maxX && y in minY..maxY
        } else {
            //两行
            x <= maxX && y in middleY..maxY || x >= minX && y in minY..middleY
        }
    }

}