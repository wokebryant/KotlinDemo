package com.example.kotlindemo.task.login

import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ComposeActivity
import com.example.kotlindemo.compose.noRippleClickable
import com.example.kotlindemo.compose.widget.Screen
import com.example.kotlindemo.compose.widget.SimpleImage
import com.example.kotlindemo.compose.widget.stateLayout.PageData
import com.example.kotlindemo.compose.widget.stateLayout.rememberPageState
import com.zhaopin.social.compose.ui.ZlColor

/**
 * @Description 登录身份验证页面
 * @Author LuoJia
 * @Date 2024/05/27
 */
class LoginAuthActivity : ComposeActivity() {

    @Preview
    @Composable
    override fun MainPage() {
        val state = testState
        Screen(
            pageState = rememberPageState(PageData.Content)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 20.dp, end = 20.dp)
            ) {
                Top(state = state)
                LazyColumn(
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .weight(1f)
                ) {
                    items(state.list) {
                        Card(item = it)
                    }
                }
                LoginCallCustomServiceText {

                }
            }
        }
    }

    @Composable
    fun Top(state: LoginAuthDataState) {
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
    fun Card(item: LoginAuthDataState.Card) {
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(85.dp)
                .background(ZlColor.C_B10)
                .padding(horizontal = 12.dp)
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.title,
                            style = TextStyle(
                                color = ZlColor.C_B1,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        if (item.showTag) {
                            Spacer(modifier = Modifier.size(4.dp))
                            SimpleImage(
                                id = R.drawable.ic_tag_recommend,
                                modifier = Modifier.padding(bottom = 1.dp).size(30.dp, 16.dp)
                            )
//                            Box(
//                                modifier = Modifier
//                                    .size(30.dp, 16.dp)
//                                    .clip(RoundedCornerShape(8.dp))
//                                    .background(ZlColor.C_R1),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                Text(
//                                    text = "推荐",
//                                    style = TextStyle(
//                                        color = ZlColor.C_W1,
//                                        fontSize = 11.sp,
//                                    )
//                                )
//                            }
                        }
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = item.content,
                        style = TextStyle(
                            color = ZlColor.C_B3,
                            fontSize = 12.sp
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

}

val testState = LoginAuthDataState(
    title = "身份验证",
    content = "请选择验证方式，验证通过后可更换绑定手机号",
    list = listOf(
        LoginAuthDataState.Card(
            pic = R.drawable.ic_login_phone,
            title = "通过邮箱验证",
            content = "通过邮箱 188*****@163.com 接收验证码",
            showTag = true,
            onClick = {
                
            }
        ),
        LoginAuthDataState.Card(
            pic = R.drawable.ic_login_wechat,
            title = "通过微信验证",
            content = "通过已绑定的微信号 123***456 验证信息",
            showTag = false,
            onClick = {

            }
        )
    )
)

data class LoginAuthDataState(
    val title: String,
    val content: String,
    val list: List<Card>
) {
    data class Card (
        val pic: Int,
        val title: String,
        val content: String,
        val showTag: Boolean,
        val onClick: () -> Unit
    )
}