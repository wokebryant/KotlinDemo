package com.example.kotlindemo.task.login.dialog

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhaopin.social.compose.ui.ZlColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/05/27
 */

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetDialog(
    sheetState: ModalBottomSheetState,
    sheetShape: RoundedCornerShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    sheetBackgroundColor: Color = ZlColor.C_W1,
    scope: CoroutineScope = rememberCoroutineScope(),
    enableBackClose: Boolean = true,
    sheetContent: @Composable ColumnScope.() -> Unit,
) {
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxWidth(),
        sheetState = sheetState,
        sheetShape = sheetShape,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetGesturesEnabled = false,
        sheetContent = sheetContent
    ) {
        if (enableBackClose) {
            //返回键监听
            BackHandler (
                enabled = (sheetState.currentValue == ModalBottomSheetValue.HalfExpanded
                        || sheetState.currentValue == ModalBottomSheetValue.Expanded),
                onBack = {
                    scope.launch { sheetState.hide() }
                }
            )
        }
    }
}