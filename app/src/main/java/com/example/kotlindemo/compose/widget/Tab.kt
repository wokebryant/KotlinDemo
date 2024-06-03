package com.example.kotlindemo.compose.widget

import androidx.annotation.FloatRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.compose.noRippleClickable
import com.zhaopin.social.compose.ui.ZlColor
import kotlin.math.abs

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/16
 */

/**
 * 自定义Tab
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTab(pagerState: PagerState, index: Int, title: String, onClick: () -> Unit) {
    val selected = pagerState.currentPage == index
    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
    Tab(
        selected = selected,
        selectedContentColor = ZlColor.C_B1,
        unselectedContentColor = ZlColor.C_B2,
        onClick = {
            onClick.invoke()
        },
        modifier = Modifier.width(60.dp)
    ) {
        Text(
            text = title,
            fontWeight = fontWeight,
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 9.dp)
        )
    }
}

/**
 * Tab指示器
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerTabIndicator(
    tabPositions: List<TabPosition>,
    pagerState: PagerState,
    color: Color = ZlColor.C_P1,
    @FloatRange(from = 0.0, to = 1.0) percent: Float = 0.25f,
    height: Dp = 3.dp,
    heightOffset: Dp = 5.dp
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val currentPage = minOf(tabPositions.lastIndex, pagerState.currentPage)
        val currentTab = tabPositions[currentPage]
        val previousTab = tabPositions.getOrNull(currentPage - 1)
        val nextTab = tabPositions.getOrNull(currentPage + 1)
        val fraction = pagerState.currentPageOffsetFraction

        val indicatorWidth = currentTab.width.toPx() * percent

        val indicatorOffset = if (fraction > 0 && nextTab != null) {
            lerp(currentTab.left, nextTab.left, fraction).toPx()
        } else if (fraction < 0 && previousTab != null) {
            lerp(currentTab.left, previousTab.left, -fraction).toPx()
        } else {
            currentTab.left.toPx()
        }

        val canvasHeight = size.height
        drawRoundRect(
            color = color,
            topLeft = Offset(
                indicatorOffset + (currentTab.width.toPx() * (1 - percent) / 2),
                canvasHeight - height.toPx() - heightOffset.toPx()
            ),
            size = Size(indicatorWidth + indicatorWidth * abs(fraction), height.toPx()),
            cornerRadius = CornerRadius(50f)
        )
    }
}