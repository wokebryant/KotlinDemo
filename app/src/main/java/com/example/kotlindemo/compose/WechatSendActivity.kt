package com.example.kotlindemo.compose

import androidx.activity.viewModels
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.viewmodel.WechatSendState
import com.example.kotlindemo.compose.viewmodel.WechatSendViewModel
import com.example.kotlindemo.compose.widget.CenterTopAppBar
import com.example.kotlindemo.compose.widget.SimpleImage
import com.example.kotlindemo.compose.widget.WechatSendDialog
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.toast.showToast
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/18
 */
@OptIn(ExperimentalMaterialApi::class)
class WechatSendActivity : ComposeActivity() {

    private val viewModel by viewModels<WechatSendViewModel>()

    companion object {
        private const val SEND_WECHAT = "投递时，同时发送微信号"
        private const val DO_NOT_SEND_WECHAT = "投递时，不发送微信号"
        private const val TIP_CONTENT = "温馨提示：\n" +
                "为了保护您的隐私安全，带有“代招”标签的岗位当前不支持投递时默认发送微信，需要您在消息页面自主发送"
    }

    @Preview
    @Composable
    override fun MainPage() {
        val scope = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmStateChange = {
                it == ModalBottomSheetValue.Expanded
            },
            skipHalfExpanded = true
        )

        Scaffold(
            modifier = Modifier
                .background(ZlColors.C_W1)
                .statusBarsPadding(),
            topBar = { WechatAppBar() }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ZlColors.C_S2)
                    .padding(it)
                    .padding(top = 10.dp, start = 20.dp, end = 20.dp)
            ) {
                SendWechatCard()
                Spacer(modifier = Modifier.size(10.dp))
                DoNotSendWechatCard()
                Spacer(modifier = Modifier.size(10.dp))
                Tip()
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))
                SaveButton {
                    scope.launch {
                        bottomSheetState.show()
                    }
                }
                Spacer(modifier = Modifier.size(20.dp))
            }
        }

        WechatSendDialog(
            onSendClick = { currentActivity()?.showToast("发送") },
            onNotSendClick = { currentActivity()?.showToast("不发送") },
            onNotNotice = {currentActivity()?.showToast("不再提醒") },
            scope = scope,
            state = bottomSheetState
        )
    }

    @Composable
    fun WechatAppBar() {
        CenterTopAppBar(
            title = {
                Text(text = "发送微信设置")
            },
            navigationIcon = {
                SimpleImage(
                    id = R.drawable.ic_common_title_back,
                    modifier = Modifier.clickable {
                        finish()
                    }
                )
            }
        )
    }

    /**
     * 发送微信卡片
     */
    @Composable
    fun SendWechatCard() {
        val state = viewModel.firstWechatSendState
        WechatCard(isFirstCard = true, state = state)
    }

    /**
     * 不发送微信卡片
     */
    @Composable
    fun DoNotSendWechatCard() {
        val state = viewModel.secondWechatSendState
        WechatCard(isFirstCard = false, state = state)
    }

    /**
     * 通用卡片
     */
    @Composable
    fun WechatCard(
        isFirstCard: Boolean,
        state: WechatSendState
    ) {
        val (isSelected, haveWechat, showTag) = state

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(112.dp),
            shape = RoundedCornerShape(10.dp),
            border = if (isSelected) BorderStroke(1.dp, ZlColors.C_P1) else null,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                ) {
                // 按钮及文本
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SimpleImage(
                        id = if (isSelected) R.drawable.ic_wechat_checked else R.drawable.ic_wechat_un_check,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                viewModel.updateSelected(isFirstCard)
                            }
                    )

                    Spacer(modifier = Modifier.size(12.dp))

                    Column(modifier = Modifier.wrapContentSize()) {
                        Text(
                            text = if (isFirstCard) SEND_WECHAT else DO_NOT_SEND_WECHAT,
                            color = ZlColors.C_B1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.size(6.dp))

                        if (haveWechat) {
                            Row(
                                modifier = Modifier
                                    .wrapContentSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "微信号：cid8765432",
                                    color = ZlColors.C_B3,
                                    fontSize = 14.sp
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(start = 6.dp)
                                        .clickable {
                                            currentActivity()?.showToast("修改")
                                        },
                                    text = "修改",
                                    color = ZlColors.C_P1,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                // 推荐标签
                if (showTag) {
                    Box(
                        modifier = Modifier
                            .size(42.dp, 22.dp)
                            .align(Alignment.TopEnd)
                            .clip(RoundedCornerShape(bottomStart = 10.dp))
                            .background(ZlColors.C_P7),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "推荐",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ZlColors.C_P1
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Tip() {
        Text(
            text = TIP_CONTENT,
            fontSize = 13.sp,
            color = ZlColors.C_B3
        )
    }

    @Composable
    fun SaveButton(onClick: () -> Unit) {
        Button(
            onClick = { onClick.invoke() },
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .padding(horizontal = 44.dp),
            elevation = null,
            shape = RoundedCornerShape(size = 22.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ZlColors.C_P1
            )
        ) {
            Text(
                text = "保存",
                color = ZlColors.C_W1,
                fontSize = 16.sp,
            )
        }
    }

}