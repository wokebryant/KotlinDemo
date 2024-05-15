package com.example.kotlindemo.compose.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * @Description Modifier扩展
 * @Author LuoJia
 * @Date 2023/11/17
 */

/**
 * 取消clickable方法的点击水波纹效果
 */
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


/**
 * Android13 以上点击水波纹去除
 */
val noRipple = object : MutableInteractionSource {
    override val interactions: Flow<Interaction> = emptyFlow()

    override suspend fun emit(interaction: Interaction) {}

    override fun tryEmit(interaction: Interaction) = true
}

// fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
//    this.then(
//        clickable(indication = null,
//            interactionSource = remember { MutableInteractionSource() }) {
//            onClick()
//        }
//    )
//}