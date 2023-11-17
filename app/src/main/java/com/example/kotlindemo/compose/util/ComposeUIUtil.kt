package com.example.kotlindemo.compose.util

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/16
 */
object ComposeUIUtil {

    /**
     * 修改TabRow最小Tab宽度
     */
    fun hackTabMinWidth() {
        val clazz = Class.forName("androidx.compose.material.TabRowKt")
        val field = clazz.getDeclaredField("ScrollableTabRowMinimumTabWidth")
        field.isAccessible = true
        field.set(null, 0.0f) // set tab min width to 0
    }

}