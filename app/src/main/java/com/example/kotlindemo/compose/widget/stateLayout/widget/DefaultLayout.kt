package com.example.kotlindemo.compose.widget.stateLayout.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.changedToDown
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.widget.stateLayout.PageData

/**
 * @Description 默认状态布局
 * @Author LuoJia
 * @Date 2024/04/12
 */

/**
 * 加载状态组件
 */
@Composable
fun DefaultLoadingLayout() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        val isPlaying by remember {
            mutableStateOf(true)
        }

        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("loading_request.json"))
        val animationProgress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = isPlaying
        )

        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(ZlColors.C_B1_80),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = composition,
                progress = { animationProgress },
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

/**
 * 空状态组件
 */
@Composable
fun DefaultEmptyLayout(data: PageData) {
    if (data is PageData.Empty) {
        val state = data.state

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ZlColors.C_W1)
                .pointerInput(Unit) {
                    // 触摸拦截逻辑，防止触摸触发下拉刷新
                    awaitPointerEventScope {
                        do {
                            val event = awaitPointerEvent()
                            // 如果是按下事件，则忽略，并将焦点提供给focusTarget
                            if (event.changes.first().pressed) {
//                            focusTarget?.focus()
                            } else {
                                continue
                            }
                        } while (!event.changes.first().changedToDown())
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = state.emptyImage),
                    contentDescription = "",
                    modifier = Modifier.size(width = 200.dp, height = 150.dp)
                )
                Text(
                    text = state.emptyTip,
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(top = 16.dp, start = 56.dp, end = 56.dp)
                )
            }
        }
    }
}

/**
 * 错误状态组件
 */
@Composable
fun DefaultErrorLayout(data: PageData) {
    if (data is PageData.Error) {
        val state = data.state
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ZlColors.C_W1)
                .pointerInput(Unit) {
                    // 触摸拦截逻辑，防止触摸触发下拉刷新
                    awaitPointerEventScope {
                        do {
                            val event = awaitPointerEvent()
                            // 如果是按下事件，则忽略，并将焦点提供给focusTarget
                            if (event.changes.first().pressed) {
//                            focusTarget?.focus()
                            } else {
                                continue
                            }
                        } while (!event.changes.first().changedToDown())
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = state.errorImage),
                    contentDescription = "",
                    modifier = Modifier.size(width = 200.dp, height = 150.dp)
                )
                Text(
                    text = state.errorTip,
                    style = TextStyle(
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(top = 16.dp, start = 56.dp, end = 56.dp)
                )
                TextButton(
                    onClick = { data.retry.invoke(data) },
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = ZlColors.C_W1,
                        backgroundColor = ZlColors.C_P1
                    ),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(84.dp)
                        .height(36.dp)
                ) {
                    Text(
                        text = state.btnText,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}