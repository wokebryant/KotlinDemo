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
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.widget.AppCompatTextView
import com.example.kotlindemo.R
import com.example.kotlindemo.utils.dip2px
import java.util.*

/**
 *  可折叠文本
 */
@Suppress("UNUSED_CHANGED_VALUE")
class FoldTextView @JvmOverloads constructor(
    context: Context,
    private val attributeSet: AttributeSet? = null,
    private val defStyleAttr: Int = 0
)
    : AppCompatTextView(context, attributeSet, defStyleAttr) {

    companion object {
        private const val ELLIPSIZE_END = "..."
        private const val MAX_LINE = 2
        private const val EXPAND_TIP_TEXT = "  收起全文"
        private const val FOLD_TIP_TEXT = "全文"
        private const val IMAGE_SPAN_TEXT = "[]"
        private const val TIP_COLOR = -0x1
        private const val TAG_COLOR = -0x1
        private val BEHAVIOR_COLOR = Color.parseColor("#ffff00")
        private val TARGET_COLOR = Color.parseColor("#01ffce")
        private val TARGET_HIDE_COLOR = Color.parseColor("#242F35")
        private const val LIGHT_TEXT_FLASH_NUM = 4
        private const val LIGHT_TEXT_FLASH_INTERVAL = 300L
        private const val END = 0
    }

    private var mPaint: Paint = Paint()

    //Tag留白间隔
    private val tagInterval = dip2px(6f)
    //Tag圆角
    private val tagCorner = dip2px(4f).toFloat()
    //显示最大行数
    private var mShowMaxLine: Int = 0
    //折叠文本
    var mFoldText: String? = ""
    //展开文本
    private var mExpandText: String? = ""
    //原始文本
    private var mOriginalText: String = ""
    //全文显示的位置 0末尾 1下一行
    private var mTipGravity = 0
    //提示文字颜色
    private var mTipColor: Int = 0
    //标签颜色
    private var mTagColor: Int = 0
    //提示是否可点击
    private var mTipClickable = false
    private var flag = false

    //提示文字坐标范围
    private var minX: Float = 0f
    private var maxX: Float = 0f
    private var minY: Float = 0f
    private var maxY: Float = 0f

    //收起全文不在同一行时，增加一个变量记录坐标
    private var middleY: Float = 0f
    //原始文本行数
    private var originalLineCount = 0
    //点击时间
    private var clickTime = 0L

    //展开后是否显示文字提示
    private var isShowTipAfterExpand = false
    //是否超过最大行数
    private var isOverMaxLine = false
    //是否展开
    private var isExpand = false
    //是否展示提示
    var isShowTip = false
    //是否展示tag,展示tag的前提是isShowTip必须为true
    var isShowTag = false
    //是否展示高亮文本
    var isShowLightText = false
    //是否展示图片
    var isShowImage = false

    //富文本效果
    private val textSpan by lazy { SpannableStringBuilder(mOriginalText) }
    private val tagSpan by lazy { RadiusBackgroundSpan(mTagColor, mTipColor, tagCorner) }
    private val behaviorSpan = ForegroundColorSpan(BEHAVIOR_COLOR)
    private var targetSpan = ForegroundColorSpan(TARGET_COLOR)
    private var imageSpan: CenterImageSpan? = null

    var behaviorText = "Mark: "
    var targetText = "ThePeople"
    var currentFlashNum = 0

    private var timer = Timer()
    private var flashTask: TimerTask? = null

    inner class FlashTask : TimerTask() {
        override fun run() {
            startTextFlashAnim()
        }
    }

    init {
        initAttributes()
        initPaint()
    }

    private fun initAttributes() {
        mShowMaxLine = MAX_LINE
        if (attributeSet != null) {
            val arr = context.obtainStyledAttributes(
                attributeSet,
                R.styleable.FoldTextView,
                defStyleAttr,
                0
            )
            mShowMaxLine = arr.getInt(R.styleable.FoldTextView_showMaxLine, MAX_LINE)
            mTipGravity = arr.getInt(R.styleable.FoldTextView_tipGravity, END)
            mTipColor = arr.getColor(R.styleable.FoldTextView_tipColor, TIP_COLOR)
            mTagColor = arr.getColor(R.styleable.FoldTextView_tagColor, TAG_COLOR)
            mTipClickable = arr.getBoolean(R.styleable.FoldTextView_tipClickable, false)
            mFoldText = arr.getString(R.styleable.FoldTextView_foldText)
            mExpandText = arr.getString(R.styleable.FoldTextView_expandText)
            isShowTip = arr.getBoolean(R.styleable.FoldTextView_showTip, false)
            isShowTipAfterExpand = arr.getBoolean(
                R.styleable.FoldTextView_showTipAfterExpand,
                false
            )
            isShowTag = arr.getBoolean(R.styleable.FoldTextView_tipWithTag, false)
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
                spannable.setSpan(
                    ForegroundColorSpan(mTipColor),
                    spannable.length - mExpandText!!.length,
                    spannable.length,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
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
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
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
            if (isShowImage) {
                setImageSpan()
            } else {
                textSpan.clear()
            }
            val stringBuilder = StringBuilder()
            //获取指定行的开始坐标
            val start = l.getLineStart(mShowMaxLine - 1)
            //获取指定行的结束坐标
            var end = l.getLineVisibleEnd(mShowMaxLine - 1)
            if (mTipGravity == END) {
                //减去尾部字符串的长度
                val builder = StringBuilder(ELLIPSIZE_END).append("  ").append(if (isShowTip) mFoldText else "")
                end -= paint.breakText(
                    mOriginalText,
                    start,
                    end,
                    false,
                    paint.measureText(builder.toString()),
                    null
                )
            } else {
                end--
            }
            //TODO 获取裁剪后的文本,如果显示标签的话，需要额外减去1，预留一个字符串的空间
            var ellipsize = if (isShowTag) mOriginalText.subSequence(0, end - 1) else mOriginalText.subSequence(0, end)
            //如果显示图片，需要裁剪图片字符
            if (isShowImage) {
                ellipsize = ellipsize.toString().replace(IMAGE_SPAN_TEXT, "")
            }
            //裁剪后的文本和...完成拼接
            stringBuilder.append(ellipsize).append(ELLIPSIZE_END)
            if (mTipGravity != END) {
                stringBuilder.append("\n")
            }
            textSpan.append(stringBuilder)
            //如果对象文本被裁剪，endIndex要单独计算
            if (isShowLightText) {
                showForegroundSpan()
            }
            super.setText(textSpan, type)
        } else {
            //针对非裁剪情况下tag展示
            if (isShowImage) {
                setImageSpan()
            }
            if (isShowTag) {
                showTagSpan()
            }
            if (isShowLightText) {
                showForegroundSpan()
            }
            super.setText(textSpan, BufferType.NORMAL)
        }
    }

    private fun handleFullTextInMaxLine() {
        //TODO 针对，一行，两行顶到头，还要展示标签的情况
    }

    /**
     *  拼接图片
     */
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setImageSpan() {
        textSpan.clear()
        val imageStr = SpannableString(IMAGE_SPAN_TEXT)
        val drawable = resources.getDrawable(R.drawable.ic_question_white)
        drawable.setBounds(0, 0, dip2px(15.6f), dip2px(15.6f))
        imageSpan = CenterImageSpan(drawable)
        imageStr.setSpan(imageSpan, 0, IMAGE_SPAN_TEXT.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textSpan.append(imageStr)
    }

    /**
     *  展示tag Span,针对没超过最大行数的情况
     */
    private fun showTagSpan() {
        val spaceLength = 2
        val startIndex = mOriginalText.length + spaceLength
        val endIndex = startIndex + mFoldText!!.length

        textSpan.append(
            if (isShowImage) {
                mOriginalText.replace(IMAGE_SPAN_TEXT, "")
            } else {
                mOriginalText
            })
        textSpan.append("  $mFoldText")
        textSpan.setSpan(tagSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     *  展示前景Span
     */
    private fun showForegroundSpan(cutEndTargetIndex: Int = 0) {
        if (!isOverMaxLine && !isShowTag) {
            textSpan.append(
                if (isShowImage) {
                    mOriginalText.replace(IMAGE_SPAN_TEXT, "")
                } else {
                    mOriginalText
                })
        }
        setBehaviorSpan()
        setTargetSpan(cutEndTargetIndex)
    }

    private fun setBehaviorSpan() {
        val startBehaviorIndex = mOriginalText.indexOf(behaviorText)
        val endBehaviorIndex = startBehaviorIndex + behaviorText.length
        textSpan.setSpan(
            behaviorSpan,
            startBehaviorIndex,
            endBehaviorIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun setTargetSpan(cutEndTargetIndex: Int = 0) {
        val startTargetIndex = mOriginalText.indexOf(targetText)
        val endTargetIndex = if (cutEndTargetIndex == 0) {
            startTargetIndex + targetText.length
        } else {
            cutEndTargetIndex
        }
        textSpan.setSpan(
            targetSpan,
            startTargetIndex,
            endTargetIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    /**
     *  高亮文本闪烁效果
     */
    fun startTextFlashTask () {
        flashTask?.cancel()
        flashTask = FlashTask()
        timer.schedule(flashTask, LIGHT_TEXT_FLASH_INTERVAL, LIGHT_TEXT_FLASH_INTERVAL)
    }

    private fun startTextFlashAnim() {
        if (currentFlashNum >= LIGHT_TEXT_FLASH_NUM) {
            flashTask?.cancel()
            return
        }
        currentFlashNum ++

        val currentColor = targetSpan.foregroundColor
        val nextColor = if (currentColor == TARGET_COLOR) TARGET_HIDE_COLOR else TARGET_COLOR

        targetSpan = ForegroundColorSpan(nextColor)
        setTargetSpan()

        super.setText(textSpan, BufferType.NORMAL)
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

            if (isShowTag && isShowTip) {
                val tagRect = RectF(
                    minX - tagInterval,
                    minY,
                    minX + paint.measureText(mFoldText),
                    maxY
                )
                canvas?.drawRoundRect(
                    tagRect,
                    tagCorner,
                    tagCorner,
                    mPaint.apply { color = mTagColor })
                canvas?.drawText(
                    mFoldText!!,
                    minX - tagInterval / 2,
                    height - paint.fontMetrics.descent - paddingBottom,
                    mPaint.apply { color = mTipColor })
            } else {
                mFoldText = if (isShowTip) mFoldText else ""
                canvas?.drawText(
                    mFoldText!!,
                    minX,
                    height - paint.fontMetrics.descent - paddingBottom,
                    mPaint
                )
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
                    if (delTime < ViewConfiguration.getTapTimeout() && isInRange(
                            event.x,
                            event.y
                        )
                    ) {
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