package com.example.kotlindemo.compose.widget

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ext.noRipple
import com.example.kotlindemo.compose.noRippleClickable
import com.zhaopin.social.compose.ui.ZlColor
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
    onChecked: (Boolean) -> Unit,
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    hrAvatar: String = ""
) {
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxWidth(),
        sheetState = state,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            WechatSendTopBox(
                onNotNotice = onNotNotice,
                hrAvatar = hrAvatar
            )
            WechatSendBottomBox(
                isSelected = false,
                onNotSendClick = onNotSendClick,
                onSendClick = onSendClick,
                onChecked = onChecked
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
    hrAvatar: String
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
                .noRippleClickable { onNotNotice.invoke() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleImage(
                id = R.drawable.ic_rejection,
                modifier = Modifier.padding(top = 1.dp).size(18.dp)
            )
            Text(
                text = "不再提醒",
                color = ZlColor.C_B2,
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
            AsyncImage(
                modifier = Modifier
                    .size(62.dp)
                    .align(Alignment.Top)
                    .clip(shape = CircleShape)
                    .border(width = 3.dp, color = ZlColor.C_W1, shape = CircleShape)
                    .padding(3.dp),
                model = ImageRequest.Builder(LocalContext.current).data(hrAvatar).build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                placeholder = painterResource(id = R.drawable.c_common_icon_hr_new_default),
                error = painterResource(id = R.drawable.c_common_icon_hr_new_default)
            )
        }
    }
}

@Composable
fun WechatSendBottomBox(
    isSelected: Boolean,
    onSendClick: () -> Unit,
    onNotSendClick: () -> Unit,
    onChecked: (Boolean) -> Unit
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
                color = ZlColor.C_B1,
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
                        backgroundColor = ZlColor.C_P7,
                        contentColor = ZlColor.C_P1
                    ),
                    elevation = null,
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f),
                    interactionSource = noRipple
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
                        backgroundColor = ZlColor.C_P1,
                        contentColor = ZlColor.C_W1
                    ),
                    elevation = null,
                    modifier = Modifier
                        .height(44.dp)
                        .weight(1f),
                    interactionSource = noRipple
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
                        .noRippleClickable {
                            checkState = !checkState
                            onChecked.invoke(checkState)
                        }
                )
                Spacer(modifier = Modifier.size(2.dp))
                Text(
                    text = "每次投递默认发送微信",
                    color = ZlColor.C_B2,
                    fontSize = 13.sp,
                )
            }
        }
    }
}