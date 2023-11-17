package com.example.kotlindemo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlindemo.compose.ui.ZlTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


/**
 * @Description
 * @Author LuoJia
 * @Date 2023/9/12
 */
abstract class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainPage()
        }
    }

    @Preview
    @Composable
    abstract fun MainPage()

    @Composable
    fun StatusBar() {
        rememberSystemUiController().setStatusBarColor(
            color = Color.White,
            darkIcons = true
        )
    }
}