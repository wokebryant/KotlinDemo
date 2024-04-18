package com.example.kotlindemo.compose.widget.stateLayout.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kotlindemo.compose.widget.stateLayout.PageData

/**
 * @Description 默认状态布局
 * @Author LuoJia
 * @Date 2024/04/12
 */

@Composable
fun DefaultLoadingLayout() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
            Text(
                text = "加载中" ?: "",
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun DefaultEmptyLayout(data: PageData) {
    if (data is PageData.Empty) {
        val state = data.state
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = state.emptyImage ?: 0),
                    modifier = Modifier.size(200.dp),
                    contentDescription = ""
                )
                Text(
                    text = state.emptyTip ?: "",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun DefaultErrorLayout(data: PageData) {
    if (data is PageData.Error) {
        val state = data.state
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = state.errorImage ?: 0),
                    modifier = Modifier.size(200.dp),
                    contentDescription = ""
                )
                Text(
                    text = state.errorTip ?: "",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .offset(0.dp, -(20.dp))
                )

                Text(
                    text = state.btnText ?: "",
                    color = Color.White,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(16.dp, 0.dp)
                        .width(100.dp)
                        .wrapContentHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Blue)
                        .padding(0.dp, 10.dp)
                        .clickable {
                            data.retry.invoke(data)
                        })
            }
        }
    }
}