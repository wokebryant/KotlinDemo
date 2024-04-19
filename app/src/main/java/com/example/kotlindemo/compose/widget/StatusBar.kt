package com.example.kotlindemo.compose.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * @Description 状态栏
 * @Author LuoJia
 * @Date 2023/12/18
 */

@Composable
fun DarkStatusBar() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = true
    SideEffect {
        systemUiController.setStatusBarColor(Color.Transparent, useDarkIcons)
    }
}

@Composable
fun LightStatusBar() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false
    SideEffect {
        systemUiController.setStatusBarColor(Color.White, useDarkIcons)
    }
}