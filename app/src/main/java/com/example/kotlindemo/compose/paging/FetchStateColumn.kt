package com.example.kotlindemo.compose.paging

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LinearList(
    modifier: Modifier = Modifier,
    data: List<ComposeItem>,
    renderItem: @Composable (ComposeItem) -> Unit
) {
    LazyColumn(modifier) {
        items(data) {
            if (it is ComposeStateItem) {
                LoadStateItem(it)
            } else {
                renderItem(it)
            }
        }
    }
}

