package com.example.kotlindemo.task.login.mail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlindemo.compose.ComposeActivity
import com.example.kotlindemo.compose.widget.Screen
import com.example.kotlindemo.compose.widget.stateLayout.PageData
import com.example.kotlindemo.compose.widget.stateLayout.PageState
import com.example.kotlindemo.compose.widget.stateLayout.rememberPageState
import com.example.kotlindemo.compose.widget.stateLayout.state.EmptyState
import com.example.kotlindemo.study.mvi.core.collectAsStateWithLifecycle
import com.example.kotlindemo.study.mvi.core.collectSideEffect
import com.example.kotlindemo.task.login.LoginCheckBindPhoneViewModel
import com.example.kotlindemo.task.login.P1StyleButton
import com.zhaopin.social.compose.ui.ZlColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Description 登录邮箱验证页面
 * @Author LuoJia
 * @Date 2024/05/28
 */
class LoginMailAuthActivity : ComposeActivity() {

    @Preview
    @Composable
    override fun MainPage() {
        val viewModel : LoginMailAuthViewModel = viewModel()
        val state by viewModel.collectAsStateWithLifecycle()

        viewModel.collectSideEffect { effect ->
            when (effect) {
                is LoginMailAuthEffect.JumpChangePhoneNumberPage -> {}
                is LoginMailAuthEffect.JumpErrorPage -> {}
            }
        }

        Screen {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            ) {
                Top(state = state)
                Center(state = state, inputTextChange = { viewModel.updateInput(it) })
                Bottom(enable = state.submitEnable, onConfirm = { viewModel.submit() })
            }
        }
    }

    @Composable
    fun Top(state: LoginMailAuthState) {
        Column {
            Text(
                text = state.title,
                style = TextStyle(
                    color = ZlColor.C_B1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp
                )
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = state.content,
                style = TextStyle(
                    color = ZlColor.C_B2,
                    fontSize = 14.sp
                )
            )
        }
    }

    @Composable
    fun Center(
        state: LoginMailAuthState,
        inputTextChange: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .padding(top = 49.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = state.email,
                style = TextStyle(
                    color = ZlColor.C_B1,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            // 输入框
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 33.dp, bottom = 36.dp)
                    .height(54.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(ZlColor.C_B10)
                    .padding(end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 焦点
                val focusRequester = remember { FocusRequester() }
                // 软键盘
                val softKeyboard = LocalSoftwareKeyboardController.current

                LaunchedEffect(key1 = Unit) {
                    delay(100)
                    focusRequester.requestFocus()
                    softKeyboard?.show()
                }
                TextField(
                    value = state.code,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = ZlColor.C_P1
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    placeholder = {
                        Text(
                            text = "请输入邮箱验证码",
                            style = TextStyle(
                                color = ZlColor.C_B5,
                                fontSize = 16.sp
                            )
                        )
                    },
                    onValueChange = {
                        inputTextChange.invoke(it)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .focusRequester(focusRequester)
                )

                CountdownText()
            }
        }
    }

    /**
     * 倒计时文本
     */
    @Composable
    fun CountdownText() {
        var countdownSeconds by remember { mutableStateOf(60) }
        var isCountdownRunning by remember { mutableStateOf(false) }

        val coroutineScope = rememberCoroutineScope()

        Text(
            text = if (isCountdownRunning) "重新发送（${countdownSeconds}s）" else "获取验证码",
            style = TextStyle(
                color = ZlColor.C_P1,
                fontSize = 14.sp
            ),
            modifier = Modifier.clickable(enabled = !isCountdownRunning) {
                if (!isCountdownRunning) {
                    isCountdownRunning = true
                    // 开始倒计时
                    coroutineScope.launch {
                        for (i in 20 downTo 1) {
                            countdownSeconds = i
                            delay(1000)
                            if (countdownSeconds == 1) {
                                isCountdownRunning = false
                            }
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun Bottom(
        enable: Boolean,
        onConfirm: () -> Unit
    ) {
        P1StyleButton(
            text = "提交",
            paddingHorizontal = 40.dp,
            enable = enable,
            onClick = { onConfirm.invoke() }
        )
    }

}