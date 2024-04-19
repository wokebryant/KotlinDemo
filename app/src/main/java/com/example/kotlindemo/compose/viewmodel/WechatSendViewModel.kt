package com.example.kotlindemo.compose.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/19
 */
class WechatSendViewModel : ViewModel() {

    var firstWechatSendState by mutableStateOf(WechatSendState())
        private set

    var secondWechatSendState by mutableStateOf(WechatSendState())
        private set

    init {
        firstWechatSendState = firstWechatSendState.copy(
            isSelected = true,
            haveWechat = true,
            showTag = true,
        )

        secondWechatSendState = secondWechatSendState.copy(
            isSelected = false,
            haveWechat = false,
            showTag = false,
        )
    }

    fun updateSelected(
        isFirstCard: Boolean
    ) {
        // 选择第一个卡片
        if (isFirstCard && !firstWechatSendState.isSelected) {
            firstWechatSendState = firstWechatSendState.copy(
                isSelected = true
            )
            if (secondWechatSendState.isSelected) {
                secondWechatSendState = secondWechatSendState.copy(
                    isSelected = false
                )
            }
        }
        // 选择第二个卡片
        if (!isFirstCard && !secondWechatSendState.isSelected) {
            secondWechatSendState = secondWechatSendState.copy(
                isSelected = true
            )
            if (firstWechatSendState.isSelected) {
                firstWechatSendState = firstWechatSendState.copy(
                    isSelected = false
                )
            }
        }
    }

}

data class WechatSendState(
    var isSelected: Boolean = false,
    val haveWechat: Boolean = false,
    val showTag: Boolean = false,
)