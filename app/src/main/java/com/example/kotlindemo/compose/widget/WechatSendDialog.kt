package com.example.kotlindemo.compose.widget

import android.view.MotionEvent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ui.ZlColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Description 发送微信弹窗
 * @Author LuoJia
 * @Date 2024/04/19
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WechatSendDialog(
    onSendClick: () -> Unit,
    onNotSendClick: () -> Unit,
    onNotNotice: () -> Unit,
    scope: CoroutineScope,
    state: ModalBottomSheetState,
) {
    NonDraggableModalBottomSheetLayout(
        modifier = Modifier.fillMaxWidth(),
        sheetState = state,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            WechatSendTopBox(
                onNotNotice = onNotNotice
            )
            WechatSendBottomBox(
                isSelected = false,
                onNotSendClick = onNotSendClick,
                onSendClick = onSendClick
            )
        }
    ) {
        //返回键监听
        BackHandler (
            enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded
                    || state.currentValue == ModalBottomSheetValue.Expanded),
            onBack = {
                scope.launch {
                    state.hide()
                }
            }
        )
    }
}

@Composable
fun WechatSendTopBox(
    onNotNotice: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
    ) {
        // 背景
        SimpleImage(
            id = R.drawable.wechat_send_bg,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillBounds
        )
        // 不再提醒按钮
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 14.dp, end = 16.dp)
                .clickable { onNotNotice.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleImage(
                id = R.drawable.ic_rejection,
                modifier = Modifier.padding(top = 1.dp).size(18.dp)
            )
            Text(
                text = "不再提醒",
                color = ZlColors.C_B2,
                fontSize = 13.sp
            )
        }
        // 微信图标
        Row(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopCenter)
                .padding(top = 54.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleImage(
                id = R.drawable.ic_wechat,
                modifier = Modifier.size(62.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            SimpleImage(
                id = R.drawable.ic_link,
                modifier = Modifier.size(24.dp, 10.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            SimpleImage(
                id = R.drawable.c_common_icon_hr_new_default,
                modifier = Modifier
                    .size(62.dp)
                    .align(Alignment.Top)
                    .clip(shape = CircleShape)
                    .border(width = 3.dp, color = ZlColors.C_W1, shape = CircleShape)
                    .padding(13.dp),
            )
        }
    }
}

@Composable
fun WechatSendBottomBox(
    isSelected: Boolean,
    onSendClick: () -> Unit,
    onNotSendClick: () -> Unit,
) {
    var checkState by remember {
        mutableStateOf(isSelected)
    }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 标题
            Text(
                text = "是否将微信发送给对方",
                color = ZlColors.C_B1,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            // 按钮
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 40.dp)
            ) {
                Button(
                    onClick = { onNotSendClick.invoke() },
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ZlColors.C_P7,
                        contentColor = ZlColors.C_P1
                    ),
                    elevation = null,
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "不发送",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = { onSendClick.invoke() },
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = ZlColors.C_P1,
                        contentColor = ZlColors.C_W1
                    ),
                    elevation = null,
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "发送",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            // 复选框
            Row(
                modifier = Modifier.padding(top = 20.dp, bottom = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SimpleImage(
                    id = if (checkState) R.drawable.ic_wechat_checked else R.drawable.ic_wechat_un_check,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable {
                            checkState = !checkState
                        }
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = "每次投递，均沿用此设置",
                    color = ZlColors.C_B2,
                    fontSize = 13.sp,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun NonDraggableModalBottomSheetLayout(
    modifier: Modifier = Modifier,
    sheetState: ModalBottomSheetState,
    sheetShape: Shape = MaterialTheme.shapes.medium,
    sheetContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    // 创建一个Modifier来拦截触摸事件
    val nonDraggableModifier = Modifier.pointerInteropFilter {
        when (it.action) {
            MotionEvent.ACTION_MOVE -> {
                false
            }

            else -> {
                false
            }
        }
    }

    // 使用自定义的Modifier包装sheetContent
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = sheetState,
        sheetShape = sheetShape,
        sheetContent = {
            Box(modifier = nonDraggableModifier) {
                sheetContent()
            }
        }
    ) {
        content()
    }
}