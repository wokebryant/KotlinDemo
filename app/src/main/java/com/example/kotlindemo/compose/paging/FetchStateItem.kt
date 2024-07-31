package com.example.kotlindemo.compose.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import com.example.kotlindemo.compose.noRippleClickable
import com.example.kotlindemo.compose.paging.datasource.FetchState
import com.example.kotlindemo.compose.widget.refresh.footer.LoadFooter
import com.zhaopin.social.compose.ui.CustomColor

@Composable
fun LoadStateItem(composeStateItem: ComposeStateItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        when (composeStateItem.state) {
            FetchState.Fetching -> {
                LoadFooter(loadState = LoadState.Loading)
            }
            FetchState.DoneFetching -> {
                LoadEndItem()
            }
            FetchState.FetchingError -> {
                LoadFailItem {
                    composeStateItem.retry.invoke()
                }
            }
            FetchState.ReadyToFetch -> {

            }
        }
    }
}

@Composable
fun LoadFailItem(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "加载失败，点击重试",
            style = TextStyle(
                fontSize = 13.sp,
                color = CustomColor.C_B1B7BE
            ),
            modifier = Modifier.noRippleClickable {
                onClick.invoke()
            }
        )
    }
}

@Composable
fun LoadEndItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "已经到底咯～",
            style = TextStyle(
                fontSize =  13.sp,
                color = CustomColor.C_B1B7BE
            )
        )
    }
}

class ComposeStateItem(val state: FetchState, val retry: () -> Unit) : ComposeItem

