package com.example.kotlindemo.task.deliverytop

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ext.noRipple
import com.example.kotlindemo.compose.noRippleClickable
import com.example.kotlindemo.compose.widget.SimpleImage
import com.example.kotlindemo.compose.widget.WechatSendBottomBox
import com.example.kotlindemo.compose.widget.WechatSendTopBox
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.compose.ui.ZlColor
import com.zhaopin.toast.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Description 投递特权弹窗
 * @Author LuoJia
 * @Date 2024/05/15
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeliveryPrivilegeDialog(
    onConfirmClick: (Int) -> Unit,
    scope: CoroutineScope,
    sheetState: ModalBottomSheetState,
    dialogState: DialogState,
) {
    var stateList by remember {
        mutableStateOf(dialogState.list)
    }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = ZlColor.C_S2,
        sheetContent = {
            DeliveryTop(
                count = dialogState.count,
                // 点击关闭
                onClose = {
                    scope.launch {
                        sheetState.hide()
                    }
                }
            )
            DeliveryCenter(
                list = stateList,
                onCheck = {
                    // 更新列表选中态
                    stateList = stateList.mapIndexed { index, deliveryCardState ->
                        val selected = it == index
                        deliveryCardState.copy(
                            isSelected = selected
                        )
                    }
                }
            )
            DeliveryBottom(
                // 确定点击
                onConfirm = {
                    val curSelectedType = stateList.find { it.isSelected }?.type
                    curSelectedType?.let {
                        onConfirmClick.invoke(it)
                    }
                    scope.launch {
                        sheetState.hide()
                    }
                    currentActivity()?.showToast(curSelectedType.toString())
                }
            )
        }
    ) {
        //返回键监听
        BackHandler (
            enabled = (sheetState.currentValue == ModalBottomSheetValue.HalfExpanded
                    || sheetState.currentValue == ModalBottomSheetValue.Expanded),
            onBack = {
                scope.launch {
                    sheetState.hide()
                }
            }
        )
    }
}

@Composable
fun DeliveryTop(count: String, onClose: () -> Unit) {
    val title1 = if (count.isNotEmpty()) "使用投递特权" else "HR很久没来了"
    val title2 = if (count.isNotEmpty()) "" else "该职位无法使用投递特权"
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        SimpleImage(
            id = R.drawable.ic_close_dialog,
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .size(24.dp)
                .align(Alignment.TopEnd)
                .noRippleClickable {
                    onClose.invoke()
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 30.dp, bottom = 30.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title1,
                style = titleCommonStyle
            )
            if (title2.isNotEmpty()) {
                Text(
                    text = title2,
                    style = titleCommonStyle
                )
            } else {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = titleSpanBlackStyle
                        ) {
                            append("本次可超越")
                        }
                        withStyle(
                            style = titleSpanBlueStyle
                        ) {
                            append(" $count ")
                        }
                        withStyle(
                            style = titleSpanBlackStyle
                        ) {
                            append("位竞争者")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun DeliveryCenter(
    list: List<DeliveryCardState>,
    onCheck: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(list) {
            if (!it.isHide) {
                DeliveryChooseCard(
                    state = it,
                    onCheck = onCheck
                )
            }
        }
    }
}

@Composable
fun DeliveryChooseCard(
    state: DeliveryCardState,
    onCheck: (Int) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(84.dp),
        shape = RoundedCornerShape(10.dp),
        border = if (state.isSelected) BorderStroke(1.dp, ZlColor.C_P1) else null,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ZlColor.C_W1)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 选择图标
                val drawable = when  {
                    state.isGray -> R.drawable.ic_checkbox_gray
                    state.isSelected -> R.drawable.ic_wechat_checked
                    else -> R.drawable.ic_wechat_un_check
                }
                SimpleImage(
                    id = drawable,
                    modifier = Modifier
                        .size(20.dp)
                        .noRippleClickable {
                            if (!state.isGray && !state.isSelected) {
                                onCheck.invoke(state.position)
                            }
                        }
                )
                // 提示文案
                Column(
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(horizontal = 10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = state.mainTitle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (state.isGray) ZlColor.C_B3 else ZlColor.C_B1
                    )
                    if (state.subTitle.isNotEmpty()) {
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(
                            text = state.subTitle,
                            fontSize = 13.sp,
                            color = if (state.isGray) ZlColor.C_B5 else ZlColor.C_B3
                        )
                    }
                }
                // 剩余次数
                if (state.countString.isNotEmpty()) {
                    Text(
                        text = state.countString,
                        color = if (state.isGray) ZlColor.C_B3 else ZlColor.C_B1,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun DeliveryBottom(onConfirm: () -> Unit) {
    Button(
        onClick = { onConfirm.invoke() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp)
            .height(44.dp)
            .padding(horizontal = 44.dp),
        elevation = null,
        shape = RoundedCornerShape(size = 22.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ZlColor.C_P1
        ),
        interactionSource = noRipple
    ) {
        Text(
            text = "确定",
            color = ZlColor.C_W1,
            fontSize = 16.sp,
        )
    }
}

val titleCommonStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = ZlColor.C_B1
)

val titleSpanBlueStyle = SpanStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = ZlColor.C_P1
)

val titleSpanBlackStyle = SpanStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = ZlColor.C_B1
)

data class DeliveryCardState(
    // 卡片类型
    val type: Int,
    // 卡片位置
    val position: Int,
    // 是否选中
    var isSelected: Boolean = false,
    // 是否置为灰色
    val isGray: Boolean = false,
    // 是否隐藏
    val isHide: Boolean = false,
    // 主标题
    val mainTitle: String = "",
    // 副标题
    val subTitle: String = "",
    // 次数
    val countString: String = ""
)

data class DialogState(
    val list: List<DeliveryCardState>,
    val count: String,
)
