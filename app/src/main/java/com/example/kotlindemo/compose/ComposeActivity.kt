package com.example.kotlindemo.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
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
        // 设置Decor使得view层级获取到Insets
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ZlTheme {
                MainPage()
            }
        }
    }

    @Preview
    @Composable
    abstract fun MainPage()

}