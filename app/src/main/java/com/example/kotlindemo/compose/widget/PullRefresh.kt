package com.example.kotlindemo.compose.widget

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ui.ZlColors
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.loren.component.view.composesmartrefresh.SmartSwipeStateFlag
import kotlinx.coroutines.delay

/**
 * @Description 下拉刷新
 * @Author LuoJia
 * @Date 2023/11/17
 * http://www.likehide.com/blogs/compose/material_pull_refresh/#/%e5%ae%8c%e6%95%b4%e4%bb%a3%e7%a0%81
 */


const val SPACE = 0

/**
 * 下拉刷新
 */
@Composable
fun PullRefresh(
    content: @Composable () -> Unit,
) {
    val refreshTriggerDistance = 70.dp
    var refreshing by remember { mutableStateOf(false) }
    val refreshState = rememberSwipeRefreshState(isRefreshing = refreshing)
    var contentYOffsetDuration by remember { mutableStateOf(0) }
    var contentYOffsetTarget by remember { mutableStateOf(0.dp) }
    val contentYOffset by animateDpAsState(
        targetValue = contentYOffsetTarget,
        label = "contentYOffset",
        animationSpec = tween(
            durationMillis = contentYOffsetDuration
        )
    )

    LaunchedEffect(refreshing) {
        if (refreshing) {
            delay(2000)
            refreshing = false
        }
    }

    SwipeRefresh(
        state = refreshState,
        onRefresh = { refreshing = true },
        indicator = { state, trigger ->
            AnimationIndicator(
                swipeRefreshState = state,
                refreshTriggerDistance = trigger
            )
        },
        refreshTriggerDistance = refreshTriggerDistance
    ) {
        if (refreshState.isRefreshing) {
            contentYOffsetTarget = refreshTriggerDistance + SPACE.dp
        }
        else {
            if (refreshState.isSwipeInProgress) {
                with(LocalDensity.current) {
                    contentYOffsetTarget = refreshState.indicatorOffset.toDp().coerceAtMost(refreshTriggerDistance + SPACE.dp)
                }
                contentYOffsetDuration = 0
            }
            else {
                contentYOffsetTarget = 0.dp
                contentYOffsetDuration = 300
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(ZlColors.C_S2)
                .offset(y = contentYOffset),
        ) {
            content()
        }
    }
}

/**
 * 下拉刷新指示器
 */
@Composable
fun AnimationIndicator(
    swipeRefreshState: SwipeRefreshState,
    refreshTriggerDistance: Dp,
) {
    val trigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
    val totalProgress = (swipeRefreshState.indicatorOffset / (trigger + with(LocalDensity.current) { SPACE.dp.toPx() * 3 })).coerceIn(0f, 1f)

    var animationAlpha by remember { mutableStateOf(1f) }

    var isPlaying by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("refresh_header_loading.json"))
    val animationProgress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever, isPlaying = isPlaying)

    var animationTopOffset by remember { mutableStateOf(-refreshTriggerDistance) }

    if (swipeRefreshState.isRefreshing) {
        animationTopOffset = 0.dp
        isPlaying = true
        animationAlpha = 1f
    }
    else {
        animationTopOffset = with(LocalDensity.current) {
            (swipeRefreshState.indicatorOffset.toDp() - refreshTriggerDistance).coerceAtMost(0.dp)
        }

        isPlaying = false
        animationAlpha = totalProgress
    }

    LottieAnimation(
        modifier = Modifier
            .width(25.dp)
            .height(refreshTriggerDistance)
            .offset(y = animationTopOffset)
            .alpha(animationAlpha),
        composition = composition,
        progress = {
                   if (swipeRefreshState.isRefreshing) {
                       animationProgress
                   } else {
                       totalProgress
                   }
        },
    )

}

@Composable
fun LottieHeader(state: SmartSwipeStateFlag) {
    var isPlaying by remember {
        mutableStateOf(false)
    }
    isPlaying = state == SmartSwipeStateFlag.REFRESHING

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("refresh_header_loading.json"))
    val animationProgress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying
    )

    Box(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            modifier = Modifier
                .width(25.dp),
            composition = composition,
            progress = { animationProgress },
        )
    }

}

@Composable
fun LottieHeader2(state: com.zj.refreshlayout.SwipeRefreshState) {
    var isPlaying by remember {
        mutableStateOf(false)
    }
    isPlaying = state.isRefreshing

    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("refresh_header_loading.json"))
    val animationProgress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying
    )

    Box(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            modifier = Modifier
                .width(25.dp),
            composition = composition,
            progress = { animationProgress },
        )
    }

}