package com.example.kotlindemo.utils

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.example.kotlindemo.R
import com.zhaopin.social.appbase.util.curContext

/**
 * @Description 字体管理
 * @Author LuoJia
 * @Date 2024/9/3
 */
object FontUtil {

    /** 字体资源引用 */
    private val ZL_FONT_NORMAL = R.font.zl_shuzixinzi_regular

    /**缓存字体 Map*/
    private val cacheTypeFaceMap: HashMap<Int, Typeface> = HashMap()

    /**
     * 设置指定的字体
     */
    @JvmStatic
    fun setZlFontRegular(textView: TextView){
        try {
            textView.typeface = getZlTypeface(ZL_FONT_NORMAL)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 获取指定字体
     */
    fun getZlTypefaceRegular() =
        getZlTypeface(ZL_FONT_NORMAL)

    /**
     * 获取字体 Typeface 对象
     */
    fun getZlTypeface(fontResName: Int): Typeface? {
        val cacheTypeface = cacheTypeFaceMap[fontResName]
        if (cacheTypeface != null) {
            return cacheTypeface
        }
        return try {
            val typeface: Typeface? = ResourcesCompat.getFont(curContext, fontResName)
            cacheTypeFaceMap[fontResName] = typeface!!
            typeface
        } catch (e: Exception) {
            e.printStackTrace()
            Typeface.DEFAULT
        }
    }

}

/**
 * TextView设置指定的字体 扩展方法
 */
fun TextView.setZlFontRegular() {
    this.typeface = FontUtil.getZlTypefaceRegular()
}