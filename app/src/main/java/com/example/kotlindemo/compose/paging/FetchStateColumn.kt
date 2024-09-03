package com.example.kotlindemo.compose.paging

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * @Description Compose分页库Column
 * @Author LuoJia
 * @Date 2024/06/22
 */
@Composable
fun FetchStateColumn(
    modifier: Modifier = Modifier,
    data: List<ComposeItem>,
    renderItem: @Composable (ComposeItem, Int) -> Unit
) {
    LazyColumn(modifier) {
        items(
            count = data.size,
            key = {
                data[it].hashCode()
            }
        ) {
            val itemData = data[it]
            if (itemData is ComposeStateItem) {
                LoadStateItem(itemData)
            } else {
                renderItem(itemData, it)
            }
        }
    }
}

