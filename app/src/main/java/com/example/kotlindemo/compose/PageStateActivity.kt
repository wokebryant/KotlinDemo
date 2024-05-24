package com.example.kotlindemo.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.compose.widget.stateLayout.StatePage
import com.example.kotlindemo.compose.widget.stateLayout.rememberPageState
import com.example.kotlindemo.compose.widget.stateLayout.state.EmptyState
import com.example.kotlindemo.compose.widget.stateLayout.state.defaultEmptyState
import kotlinx.coroutines.delay

/**
 * @Description Compose状态页
 * @Author LuoJia
 * @Date 2024/04/12
 */
class PageStateActivity : ComposeActivity() {

    @Preview
    @Composable
    override fun MainPage() {
        val pageState = rememberPageState()

        // 测试代码，模拟网络请求
        LaunchedEffect(key1 = Unit) {
            delay(2000)
            pageState.showContent()
        }

        ConstraintLayout {
            val (control, _) = createRefs()

            // 状态页
            StatePage(
                modifier = Modifier.fillMaxSize(),
                pageState = pageState,
                retry = {
                    // 重试
                    pageState.showLoading()
                },
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "加载成功")
                }
            }

            // 控制栏
            Row(
                modifier = Modifier
                    .constrainAs(control) {
                        centerHorizontallyTo(parent)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(bottom = 10.dp)
            ) {
                val modifier = Modifier.padding(start = 10.dp)
                Button(
                    modifier = modifier,
                    onClick = {
                        pageState.showContent()
                    }
                ) {
                    Text(text = "成功页")
                }
                Button(
                    modifier = modifier,
                    onClick = {
                        pageState.showError()
                    }
                ) {
                    Text(text = "错误页")
                }
                val emptyState = EmptyState(
                    emptyImage = R.drawable.core_common_error,
                    emptyTip = "我是自定义文案"
                )
                Button(
                    modifier = modifier,
                    onClick = { pageState.showEmpty(state = emptyState) }
                ) {
                    Text(text = "空页面")
                }
                Button(
                    modifier = modifier,
                    onClick = { pageState.showLoading() }
                ) {
                    Text(text = "加载页")
                }
            }
        }
    }

}