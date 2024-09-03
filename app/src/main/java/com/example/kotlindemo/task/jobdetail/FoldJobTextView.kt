package com.example.kotlindemo.task.jobdetail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.Layout
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import com.example.kotlindemo.R
import com.zhaopin.social.common.extension.getDrawable
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.social.module_common_util.ext.sp

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/30
 */
@SuppressLint("CustomViewStyleable")
class FoldJobTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatTextView(context, attrs, defStyle) {
    companion object {
        const val ELLIPSIZE_END = "..."
        const val MAX_LINE = 4
        const val EXPAND_TIP_TEXT = "  收起全文"
        const val FOLD_TIP_TEXT = "全文"
        const val TIP_COLOR = -0x1
        const val END = 0
    }

    /** 显示最大行数 */
    private var mShowMaxLine: Int = 0
    /** 折叠文本 */
    var mFoldText: String? = ""
    /** 展开文本 */
    var mExpandText: String? = ""
    /** 原始文本 */
    var mOriginalText: String = ""
    /** 是否展开*/
    var isExpand = false
    /** 全文显示的位置 0末尾 1下一行 */
    var mTipGravity = 0
    /** 提示文字颜色*/
    var mTipColor: Int = 0
    /** 提示是否可点击 */
    var mTipClickable = false
    var flag = false
    private var mPaint: Paint = Paint()
    /** 展开后是否显示文字提示 */
    private var isShowTipAfterExpand = false
    /** 提示文字坐标范围 */
    private var minX: Float = 0f
    private var maxX: Float = 0f
    private var minY: Float = 0f
    private var maxY: Float = 0f
    /** 扩展的点击范围 */
    private val clickExpandRangeX = 200.dp
    private val clickExpandRangeY = 20.dp
    private val clickInterval = 250
    /** 收起全文不在同一行时，增加一个变量记录坐标*/
    private var middleY: Float = 0f
    /** 原始文本行数 */
    private var originalLineCount = 0
    /** 是否超过最大行数 */
    private var isOverMaxLine = false
    /** 点击时间 */
    private var clickTime = 0L
    /** 最大展示高度 */
    private var maxHeight = 100.dp.toFloat()

    var onTransCallback: ((Boolean) -> Unit)? = null

    init {
        mShowMaxLine = MAX_LINE
        if (attrs != null) {
            val arr = context.obtainStyledAttributes(attrs, R.styleable.FoldTextView)
            mShowMaxLine = arr.getInt(R.styleable.FoldTextView_showMaxLine, MAX_LINE)
            maxHeight = arr.getDimension(R.styleable.FoldTextView_showMaxHeight, maxHeight)
            mTipGravity = arr.getInt(R.styleable.FoldTextView_tipGravity, END)
            mTipColor = arr.getColor(R.styleable.FoldTextView_tipColor, TIP_COLOR)
            mTipClickable = arr.getBoolean(R.styleable.FoldTextView_tipClickable, false)
            mFoldText = arr.getString(R.styleable.FoldTextView_foldText)
            mExpandText = arr.getString(R.styleable.FoldTextView_unFoldText)
            isShowTipAfterExpand = arr.getBoolean(R.styleable.FoldTextView_showTipAfterExpand, false)
            arr.recycle()
        }
        if (mExpandText.isNullOrEmpty()) {
            mExpandText = EXPAND_TIP_TEXT
        }
        if (mFoldText.isNullOrEmpty()) {
            mFoldText = FOLD_TIP_TEXT
        }
        if (mTipGravity == END) {
            mFoldText = "  $mFoldText"
        }
        mPaint.apply {
            textSize = 13.sp.toFloat()
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
            minX =
                paddingLeft + layout.getPrimaryHorizontal(spannable.lastIndexOf(mExpandText!![0]) - 1)
            maxX =
                paddingLeft + layout.getPrimaryHorizontal(spannable.lastIndexOf(mExpandText!![mExpandText!!.length - 1]) + 1)
            val bound = Rect()
            try {
                layout.getLineBounds(mLieCount, bound)
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
            } catch (_: Exception){
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
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
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
        // 最大行数由最大高度决定
        mShowMaxLine = (maxHeight / lineHeight).toInt()
        //记录原始行数
        originalLineCount = l.lineCount
        onTransCallback?.invoke(l.lineCount > mShowMaxLine)
        if (l.lineCount > mShowMaxLine) {
            isOverMaxLine = true
            val span = SpannableStringBuilder()
            val start = l.getLineStart(mShowMaxLine - 1)
            var end = l.getLineVisibleEnd(mShowMaxLine - 1)
            if (mTipGravity == END) {
                val builder = StringBuilder(ELLIPSIZE_END).append("  ").append(mFoldText)
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
            val ellipsize = mOriginalText.subSequence(0, end)
            span.append(ellipsize).append(ELLIPSIZE_END)
            if (mTipGravity != END) {
                span.append("\n")
            }
            super.setText(span, type)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isOverMaxLine && !isExpand) {
            var offsetY = 0
            val foldTextWidth = paint.measureText(mFoldText)
            val drawable = getDrawable(R.drawable.ic_fold_arrow_down)
            val drawablePadding = 2.dp
            val drawableWidth = 12.dp
            val drawableHeight = 12.dp

            //折叠
            if (mTipGravity == END) {
                minX = width - paddingLeft - paddingRight - foldTextWidth
                maxX = (width - paddingLeft - paddingRight).toFloat()
            } else {
                val textAndDrawableWidth = foldTextWidth + drawableWidth  + drawablePadding
                minX = (width.toFloat() - textAndDrawableWidth) / 2
                maxX = minX + textAndDrawableWidth
                offsetY = 7.dp
            }
            minY = height - (paint.fontMetrics.descent - paint.fontMetrics.ascent) - paddingBottom + offsetY
            maxY = (height - paddingBottom).toFloat() + offsetY

            val startTextX = minX
            val startTextY = height - paint.fontMetrics.descent - paddingBottom + offsetY
            canvas?.drawText(
                mFoldText!!,
                startTextX,
                startTextY,
                mPaint
            )

            // 换行才展示图片
            if (mTipGravity != END) {
                val startDrawableX = startTextX + foldTextWidth - drawableWidth / 2 + drawablePadding
                val startDrawableY = startTextY - drawableHeight + 1.dp
                drawable?.setBounds(
                    startDrawableX.toInt(),
                    startDrawableY.toInt(),
                    (startDrawableX + drawableWidth).toInt(),
                    (startDrawableY + drawableHeight).toInt()
                )
                canvas?.let {
                    drawable?.draw(it)
                }
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

//                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    if (!isShowTipAfterExpand && isExpand) {
                        return super.onTouchEvent(event)
                    }
                    val delTime = System.currentTimeMillis() - clickTime
                    clickTime = 0L
                    if (delTime < clickInterval && isInRange(
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
            x in (minX - clickExpandRangeX)..(maxX + clickExpandRangeX) && y in (minY - clickExpandRangeY)..(maxY + clickExpandRangeY)
        } else {
            //两行
            x <= maxX && y in middleY..maxY || x >= minX && y in minY..middleY
        }
    }

}