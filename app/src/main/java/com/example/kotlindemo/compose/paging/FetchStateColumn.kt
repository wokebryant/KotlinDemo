package com.example.kotlindemo.compose.paging

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import com.example.kotlindemo.utils.copyOf

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
        val tempData = data.copyOf() as List<ComposeItem>

        items(
            count = data.size,
            key = { index ->
                val defaultKey = "$index-${tempData[index].hashCode()}"
                val customKey = tempData[index].key
                val finalKey = customKey.ifEmpty { defaultKey }
                Log.i("WokeBryant: ", " key = $finalKey")
                defaultKey
            }

        ) {index ->
            val itemData = data[index]
            if (itemData is ComposeStateItem) {
                LoadStateItem(itemData)
            } else {
                renderItem(itemData, index)
            }
        }
    }

//    LazyColumn(modifier) {
//        itemsIndexed(
//            items = data,
//            key = { index, item ->
//                index
//            }
//        ) { index, item ->
//            if (item is ComposeStateItem) {
//                LoadStateItem(item)
//            } else {
//                renderItem(item, index)
//            }
//        }
//    }
}

