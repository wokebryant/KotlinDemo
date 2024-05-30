package com.example.kotlindemo.compose.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.ui.ZlColors
import com.example.kotlindemo.compose.widget.stateLayout.PageData
import com.example.kotlindemo.compose.widget.stateLayout.PageState
import com.example.kotlindemo.compose.widget.stateLayout.StatePage
import com.example.kotlindemo.compose.widget.stateLayout.rememberPageState
import com.zhaopin.social.appbase.util.currentActivity

/**
 * @Description 页面模版
 * @Author LuoJia
 * @Date 2024/05/23
 */

/**
 * @param modifier
 * @param pageState 页面状态（加载/错误/空/骨架屏）
 * @param title 标题
 * @param isHideAppBar 是否隐藏AppBar
 * @param isCustomAppBar 是否使用自定义AppBar
 * @param customAppbar 自定义AppBar(如果设置自定义AppBar，一定要将[isCustomAppBar]至为true)
 * @param navigationIcon AppBar左边按钮
 * @param actionIcon AppBar右边按钮
 * @param onNavigationClick AppBar左边按钮点击
 * @param onActionClick AppBar右边按钮点击
 * @param onRetry 重试
 * @param content 内容
 */
@Composable
fun Screen(
    modifier: Modifier = Modifier,
    pageState: PageState = rememberPageState(),
    title: String = "",
    isHideAppBar: Boolean = false,
    isCustomAppBar: Boolean = false,
    customAppbar: @Composable () -> Unit = { },
    navigationIcon: Int = R.drawable.b_common_title_back_icon,
    actionIcon: Int? = null,
    onNavigationClick: ()-> Unit = { currentActivity()?.finish() },
    onActionClick: () -> Unit = { },
    onRetry: (PageData) -> Unit = { },
    content: @Composable (PaddingValues) -> Unit,
) {

    Scaffold(
        modifier = Modifier
            .background(ZlColors.C_W1)
            .statusBarsPadding()
            .navigationBarsPadding()
            .then(modifier),
        topBar = {
            if (!isHideAppBar) {
                if (isCustomAppBar) {
                    customAppbar()
                } else {
                    DefaultAppBar(
                        title = title,
                        navigationIcon = navigationIcon,
                        actionIcon = actionIcon,
                        onNavigationClick = onNavigationClick,
                        onActionClick = onActionClick
                    )
                }
            }
        },
    ) {
        StatePage(
            modifier = Modifier
                .fillMaxSize(),
            pageState = pageState,
            retry = {
                onRetry.invoke(it)
            }
        ) {
            content(it)
        }
    }

}

@Composable
fun DefaultAppBar(
    title: String,
    navigationIcon: Int,
    actionIcon: Int?,
    onActionClick: () -> Unit = { },
    onNavigationClick: ()-> Unit = { currentActivity()?.finish() }
) {
    CenterTopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = navigationIcon),
                contentDescription = null,
                modifier = Modifier.clickable {
                    onNavigationClick.invoke()
                }
            )
        },
        actions = {
            actionIcon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onActionClick.invoke()
                    }
                )
            }
        }
    )
}

