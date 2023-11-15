package com.example.kotlindemo.compose

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/15
 */
class CollectActivity : ComposeActivity() {

    @Preview
    @Composable
    override fun MainPage() {
        Text(
            text = "",
        )
    }
}