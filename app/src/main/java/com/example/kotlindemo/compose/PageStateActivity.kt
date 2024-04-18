package com.example.kotlindemo.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlindemo.compose.widget.stateLayout.CommonStateLayout
import com.example.kotlindemo.compose.widget.stateLayout.rememberPageState

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
        CommonStateLayout(
            modifier = Modifier.fillMaxSize(),
            pageState = pageState,
            retry = {

            },
            loading = {

            },

        )
    }

}