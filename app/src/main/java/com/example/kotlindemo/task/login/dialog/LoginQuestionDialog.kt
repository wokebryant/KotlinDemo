package com.example.kotlindemo.task.login.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.noRippleClickable
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.widget.SimpleImage
import com.example.kotlindemo.task.login.LoginCallCustomServiceText
import kotlinx.coroutines.launch

/**
 * @Description 登陆遇到问题弹窗
 * @Author LuoJia
 * @Date 2024/05/27
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginQuestionDialog(
    sheetState: ModalBottomSheetState,
    dialogState: LoginQuestionDialogState
) {
    val scope = rememberCoroutineScope()

    BottomSheetDialog(
        sheetState = sheetState,
        scope = scope,
        sheetBackgroundColor = ZlColors.C_S2
    ) {
        NoVerifyCodeDialogTop(
            title = dialogState.title,
            onClose = {
                scope.launch { sheetState.hide() }
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            content = {
                items(dialogState.list) {
                    LoginQuestionCard(it)
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        )
        LoginCallCustomServiceText {
            dialogState.onTipClick.invoke()
        }
    }
}

@Composable
private fun LoginQuestionCard(
    item: LoginQuestionDialogState.Item
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(ZlColors.C_W1)
            .padding(horizontal = 12.dp, vertical = 20.dp)
            .noRippleClickable { item.onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SimpleImage(
                id = item.pic,
                modifier = Modifier.size(32.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = item.title,
                    style = TextStyle(
                        color = ZlColors.C_B1,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = item.content,
                    style = TextStyle(
                        color = ZlColors.C_B3,
                        fontSize = 13.sp
                    )
                )
            }
            SimpleImage(
                id = R.drawable.ic_arrow_back,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}


data class LoginQuestionDialogState(
    val title: String,
    val list: List<Item>,
    val onTipClick: () -> Unit
) {
    data class Item(
        val pic: Int,
        val title: String,
        val content: String,
        val onClick: () -> Unit
    )
}