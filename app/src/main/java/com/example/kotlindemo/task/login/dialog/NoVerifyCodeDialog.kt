package com.example.kotlindemo.task.login.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ext.noRipple
import com.example.kotlindemo.compose.noRippleClickable
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.widget.SimpleImage
import com.example.kotlindemo.task.login.P1StyleButton
import kotlinx.coroutines.launch

/**
 * @Description 收不到验证码弹窗
 * @Author LuoJia
 * @Date 2024/05/27
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NoVerifyCodeDialog(
    sheetState: ModalBottomSheetState,
    dialogState: NoVerifyCodeDialogState
) {
    val scope = rememberCoroutineScope()

    BottomSheetDialog(sheetState = sheetState, scope = scope) {
        NoVerifyCodeDialogTop(title = dialogState.title, onClose = {
            scope.launch { sheetState.hide() }
        })
        NoVerifyCodeDialogCenter(dialogState)
        NoVerifyCodeDialogBottom {
            scope.launch { sheetState.hide() }
        }
    }
}

@Composable
internal fun NoVerifyCodeDialogTop(
    title: String,
    onClose: () -> Unit
) {
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
        
        Text(
            modifier = Modifier.padding(start = 20.dp, top = 28.dp),
            text = title,
            style = TextStyle(
                color = ZlColors.C_B1,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Composable
private fun NoVerifyCodeDialogCenter(
    dialogState: NoVerifyCodeDialogState
) {
    val spanStyle = SpanStyle(
        color = ZlColors.C_B1,
        fontSize = 15.sp,
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 20.dp, bottom = 54.dp),
        content = {
            items(dialogState.list) {
                if (it.endsWith("去换绑")) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val startText = it.substring(0, it.length - 3)
                        val endText = "去换绑"

                        val annotatedString = buildAnnotatedString {
                            withStyle(style = spanStyle) {
                                append(startText)
                            }
                            pushStringAnnotation(
                                tag = "tag1",
                                annotation = endText
                            )
                            withStyle(style = spanStyle.copy(color = ZlColors.C_P1)) {
                                append(endText)
                            }
                            pop()
                        }
                        ClickableText(
                            text = annotatedString,
                            onClick = { offset ->
                                annotatedString.getStringAnnotations(
                                    tag = "tag1",
                                    start = offset,
                                    end = offset
                                ).firstOrNull()?.let {
                                    dialogState.onTipClick.invoke()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(2.dp))
                        SimpleImage(id = R.drawable.ic_blue_arrow_end)
                    }
                } else {
                    Text(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = it,
                        style = TextStyle(
                            color = ZlColors.C_B1,
                            fontSize = 15.sp,
                        )
                    )
                }
            }
        }
    )
}

@Composable
fun NoVerifyCodeDialogBottom(onConfirm: () -> Unit) {
    P1StyleButton(
        text = "知道了",
        paddingHorizontal = 64.dp,
        onClick = { onConfirm.invoke() }
    )
    Spacer(modifier = Modifier.size(10.dp))
}

data class NoVerifyCodeDialogState(
    val title: String,
    val list: List<String>,
    val onTipClick: () -> Unit
)