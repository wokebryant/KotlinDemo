package com.example.kotlindemo.widget

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatTextView
import com.example.kotlindemo.R
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.string.RegexConstants
import com.zhaopin.social.module_common_util.string.RegexUtils
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @Description 高亮文本
 * @Author LuoJia
 * @Date 2023/7/20
 */
class BrightTextView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle,
) : AppCompatTextView(context, attr, defStyleAttr) {

    /**
     * 设置文本
     * @param originText 原始文本
     * @param brightTexts 需要高亮的文本
     * @param brightStyle 高亮风格
     */
    @JvmOverloads
    fun setBrightText(
        originText: String,
        vararg brightTexts: String = emptyArray(),
        brightStyle: BrightStyle = BrightStyle()
    ) {
        if (originText.isEmpty() || brightTexts.isEmpty()) {
            text = originText
            return
        }

        if (brightStyle.maxLines > 0) {
            if (layout == null) {
                text = originText
                viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                        this@BrightTextView.layout?.let {
                            val lines = it.lineCount
                            if (lines > brightStyle.maxLines) {
                                // 裁剪字符串
                                val subText = text.substring(0, it.getLineEnd(1) - 1) + "..."
                                renderBrightText(
                                    originText = subText,
                                    brightTexts = brightTexts,
                                    brightStyle = brightStyle
                                )
                            }
                        }
                    }
                })
            }
        } else {
            renderBrightText(
                originText = originText,
                brightTexts = brightTexts,
                brightStyle = brightStyle
            )
        }
    }

    private fun renderBrightText(
        originText: String,
        vararg brightTexts: String = emptyArray(),
        brightStyle: BrightStyle = BrightStyle()
    ) {
        var richText: Spanned? = null
        brightTexts.forEachIndexed { index, text ->
            richText = if (richText == null) {
                getRichText(
                    originText = originText,
                    brightText = text,
                    brightColor = brightStyle.brightColor,
                    brightRange = brightStyle.brightRangeList.getOrNull(index) ?: Pair(0, 0)
                )
            } else {
                getRichText(
                    originText = richText!!,
                    brightText = text,
                    brightColor = brightStyle.brightColor,
                    brightRange = brightStyle.brightRangeList.getOrNull(index) ?: Pair(0, 0)
                )
            }
        }
        text = richText
//        movementMethod = LinkMovementMethod.getInstance()
        isFocusable = false
        isClickable = false
    }


    /**
     * 拼接高亮文本
     */
    private fun getRichText(
        originText: CharSequence,
        brightText: String,
        @ColorRes brightColor: Int,
        brightRange: Pair<Int, Int> = Pair(0, 0)
    ): Spanned {
        val regexBrightText = RegexUtils.getReplaceAll(
            brightText, RegexConstants.REGEX_SPECIAL_CHARACTERS, ""
        )
        if (regexBrightText.isNullOrEmpty()) {
            return SpannableString(originText)
        }

        val originSpan = SpannableString(originText)
        val bright = Pattern.compile(brightText)
        val match = bright.matcher(originSpan)
        var count = 0
        while (match.find()) {
//            if (count > 0) return originSpan
            val start = match.start()
            val end = match.end()
            // 满足范围限制条件
            if (isAvailableRange(match, brightRange)) {
                originSpan.setSpan(
                    HighLightSpan(
                        getColor(brightColor),
                        (textSize * 1.5).toInt()
                    ), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                count ++

            }
        }
        return originSpan
    }

    /**
     * 判断高亮的文本范围是否满足条件
     */
    private fun isAvailableRange(match: Matcher, brightRange: Pair<Int, Int>): Boolean {
        val (brightStart, brightEnd) = brightRange
        // 都为0代表默认值，不限制高亮
        if (brightStart == 0 && brightEnd == 0) {
            return true
        }
        // 值瞎传，不限制高亮
        if (brightStart < 0 || brightEnd < 0) {
            return true
        }
        // 值相等，不限制高亮
        if (brightStart == brightEnd) {
            return true
        }
        // 值能匹配上, 不限制高亮
        if (match.start() == brightStart && match.end() == brightEnd) {
            return true
        }
        return false
    }

}

/**
 * 高亮风格
 */
data class BrightStyle(
    // 颜色
    @ColorRes val brightColor: Int = R.color.C_D4DEFF,
    // 高亮范围(高亮范围列表长度和高亮文本列表长度需要保持一致，且一一对应)
    val brightRangeList: List<Pair<Int, Int>> = emptyList(),
    val maxLines: Int = 0
)