package com.example.kotlindemo.compose.widget

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/19
 */

@Composable
fun SimpleImage(
    id: Int,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}