package com.example.kotlindemo.task.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ComposeActivity
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.widget.Screen
import com.example.kotlindemo.compose.widget.SimpleImage
import com.example.kotlindemo.compose.widget.stateLayout.PageState
import com.example.kotlindemo.compose.widget.stateLayout.rememberPageState

/**
 * @Description 登录验证失败页面
 * @Author LuoJia
 * @Date 2024/05/28
 */
class LoginAuthFailActivity : ComposeActivity() {

    companion object {
        const val CONTENT = "你提供的验证信息与简历信息不一致，\n" +
                "请核实后重新验证"
    }

    @Preview
    @Composable
    override fun MainPage() {
        Screen(
            pageState = rememberPageState(PageState.Content())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimpleImage(
                        id = R.drawable.ic_auth_fail,
                        modifier = Modifier.size(32.dp)
                    )
                    Text(
                        text = "验证失败",
                        style = TextStyle(
                            color = ZlColors.C_B1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                val annotatedString = buildAnnotatedString {
                    append("剩余")
                    withStyle(
                        style = SpanStyle(
                            color = ZlColors.C_P1,
                            fontSize = 16.sp
                        )
                    ) {
                        append(" 2 ")
                    }
                    append("次验证次数")
                }
                Text(
                    text = annotatedString,
                    style = TextStyle(
                        color = ZlColors.C_B2,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(top = 10.dp)
                )

                Text(
                    text = CONTENT,
                    style = TextStyle(
                        color = ZlColors.C_B3,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.padding(top = 28.dp),
                    textAlign = TextAlign.Center
                )

                P1StyleButton(
                    text = "重新验证",
                    paddingHorizontal = 60.dp,
                    onClick = {  },
                    modifier = Modifier.padding(top = 40.dp)
                )
            }
        }
    }
}