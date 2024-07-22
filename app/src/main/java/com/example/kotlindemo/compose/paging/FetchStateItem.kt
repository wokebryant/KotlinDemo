package com.example.kotlindemo.compose.paging

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.example.kotlindemo.compose.paging.datasource.FetchState
import com.example.kotlindemo.compose.widget.refresh.footer.LoadFooter

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
                Button(
                    modifier = Modifier,
                    onClick = { composeStateItem.retry.invoke() }
                ) {
                    Text(text = "加载完成")
                }
            }
            FetchState.FetchingError -> {
                Button(
                    modifier = Modifier,
                    onClick = { composeStateItem.retry.invoke() }
                ) {
                    Text(text = "重试")
                }
            }
            FetchState.ReadyToFetch -> {

            }
        }
    }
}

class ComposeStateItem(val state: FetchState, val retry: () -> Unit) : ComposeItem

