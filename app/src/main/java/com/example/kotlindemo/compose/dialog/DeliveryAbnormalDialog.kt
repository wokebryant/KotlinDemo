package com.example.kotlindemo.compose.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ext.noRipple
import com.example.kotlindemo.compose.noRippleClickable
import com.example.kotlindemo.compose.widget.SimpleImage
import com.example.kotlindemo.task.login.P1StyleButton
import com.zhaopin.social.compose.ui.ZlColor
import com.zhaopin.social.compose.ui.ZlTypography
import kotlinx.coroutines.delay

/**
 * @Description 投递异常弹窗
 * @Author LuoJia
 * @Date 2024/8/21
 */

@Preview
@Composable
fun DeliveryAbnormalDialog(show: Boolean, state: DeliveryAbnormalState) {
    var showDialog by remember {
        mutableStateOf(show)
    }
    if (showDialog) {
        val colorStop = arrayOf(
            0.0f to Color(0x33FA5555),
            0.2f to Color(0x00FA5555),
            1.0f to ZlColor.C_W1
        )
        val bg = Brush.verticalGradient(
            colorStops = colorStop
        )

        Dialog(onDismissRequest = {  }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(bg),
                ) {
                    SimpleImage(
                        id = R.drawable.ic_delivery_abnormal_close,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 16.dp, end = 16.dp)
                            .size(20.dp)
                            .noRippleClickable {
                                showDialog = false
                            }
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 24.dp, start = 24.dp, end = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SimpleImage(
                            id = R.drawable.ic_delivery_abnormal_warning,
                            modifier = Modifier.size(36.dp)
                        )
                        Text(
                            text = state.title,
                            style = ZlTypography.W600_18,
                            color = ZlColor.C_B1,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = state.content,
                            style = ZlTypography.W400_14,
                            color = ZlColor.C_B2,
                            modifier = Modifier.padding(top = 10.dp)
                        )

                        if (state.showCountDown) {
                            UnbanCountDownLayout(
                                millis = state.unLockTime,
                                onTimeTickerFinish = {
                                    showDialog = false
                                }
                            )
                        }

                        DeliveryAbnormalButton(
                            modifier = Modifier.padding(vertical = 24.dp),
                            showCallService = state.isCallService,
                            onClick = {
                                if (state.isCallService) {

                                } else {
                                    showDialog = false
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

/**
 * 解禁倒计时布局
 */
@Composable
fun UnbanCountDownLayout(
    millis: Long,
    onTimeTickerFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleImage(id = R.drawable.ic_delivery_abnormal_line_1)
            Text(
                text = "距离解除",
                style = ZlTypography.W400_13,
                color = ZlColor.C_B3,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            SimpleImage(id = R.drawable.ic_delivery_abnormal_line_2)
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            var countdownMills by remember { mutableStateOf(millis) }

            // 开始倒计时
            LaunchedEffect(key1 = millis) {
                for (i in millis / 1000 downTo 1) {
                    countdownMills = i * 1000
                    delay(1000)
                    if (i.toInt() == 1) {
                        onTimeTickerFinish.invoke()
                    }
                }
            }

            // 获取倒计时时间
            val hours = (countdownMills / (1000 * 60 * 60)).toString().padStart(2, '0')
            val minutes = ((countdownMills % (1000 * 60 * 60)) / (1000 * 60)).toString().padStart(2, '0')
            val seconds = ((countdownMills % (1000 * 60)) / 1000).toString().padStart(2, '0')

            UnbanCountDownItem(hours)
            UnbanCountDownDivide()

            UnbanCountDownItem(minutes)
            UnbanCountDownDivide()

            UnbanCountDownItem(seconds)
        }
    }
}

/**
 * 解禁倒计时Item
 */
@Composable
fun UnbanCountDownItem(time: String) {
    Surface(
        modifier = Modifier.size(width = 36.dp, height = 40.dp),
        shape = RoundedCornerShape(10.dp),
        color = ZlColor.C_B10,
        border = BorderStroke(1.dp, ZlColor.C_B8)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = time,
                color = ZlColor.C_B2,
                style = ZlTypography.W600_20
            )
        }
    }
}

/**
 * 倒计时分割符号
 */
@Composable
fun UnbanCountDownDivide() {
    SimpleImage(
        id = R.drawable.ic_delivery_abnormal_divide,
        modifier = Modifier.size(width = 20.dp, height = 40.dp)
    )
}

@Composable
fun DeliveryAbnormalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    paddingHorizontal: Dp = 24.dp,
    shapeSize: Dp = 22.dp,
    enable: Boolean = true,
    showCallService: Boolean
) {
    Button(
        onClick = { if (enable) onClick.invoke() },
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = paddingHorizontal),
        elevation = null,
        shape = RoundedCornerShape(size = shapeSize),
        colors = ButtonDefaults.buttonColors(
            backgroundColor =if (enable) ZlColor.C_P1 else ZlColor.C_P5
        ),
        interactionSource = noRipple
    ) {
        if (showCallService) {
            SimpleImage(
                id = R.drawable.ic_delivery_abnormal_call,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(20.dp)
            )
        }
        Text(
            text = if (showCallService) "联系客服" else "知道了",
            color = ZlColor.C_W1,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}