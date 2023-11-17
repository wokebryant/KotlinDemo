package com.example.kotlindemo.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/16
 */

/**
 * @param title 标题
 * @param modifier Modifier，设置后会和已经设置好Modifier进行合并
 * @param navigationIcon 左边图标
 * @param backgroundColor 背景颜色
 * @param actions 右边图标
 */
@Composable
fun CenterTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    backgroundColor: Color = Color.White,
    actions: @Composable RowScope.() -> Unit = {},
) {
    val constraintSet = ConstraintSet {
        val titleRef = createRefFor("title")
        val navigationIconRef = createRefFor("navigationIcon")
        val actionsRef = createRefFor("actions")
        constrain(titleRef) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
        constrain(navigationIconRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
        constrain(actionsRef) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }
    }
    ConstraintLayout(constraintSet, modifier = Modifier
        .fillMaxWidth()
        .background(backgroundColor)
        .height(56.dp)
        .then(modifier)
    ) {
        Box(
            Modifier
                .layoutId("title")
                .padding(horizontal = 4.dp)
        ) {
            ProvideTextStyle(value = MaterialTheme.typography.h6) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    content = title
                )
            }
        }
        if (navigationIcon != null) {
            Box(modifier = Modifier
                .layoutId("navigationIcon")
                .padding(start = 10.dp)) {
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.high,
                    content = navigationIcon
                )
            }
        }
        Row(
            Modifier
                .layoutId("actions")
                .padding(end = 10.dp),
            content = actions
        )

    }

}

