package com.example.kotlindemo.compose

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat


/**
 * @Description
 * @Author LuoJia
 * @Date 2023/9/12
 */
abstract class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainPage()
        }
    }

    @Preview
    @Composable
    abstract fun MainPage()
}