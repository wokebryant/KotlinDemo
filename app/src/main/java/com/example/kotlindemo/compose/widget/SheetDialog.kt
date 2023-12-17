package com.example.kotlindemo.compose.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlindemo.compose.ui.ZlColors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CollectDialog(
    onClick: () -> Unit,
    scope: CoroutineScope,
    state: ModalBottomSheetState,
) {
    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxWidth(),
        sheetState = state,
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "确认要取消收藏吗？",
                        style = TextStyle(
                            color = ZlColors.C_AAB0B7,
                            fontSize = 14.sp
                        )
                    )
                }

                Divider(color = ZlColors.C_S2, thickness = 1.dp)

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "取消收藏",
                        style = TextStyle(
                            color = ZlColors.C_B1,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    onClick.invoke()
                                    state.hide()
                                }
                            }
                    )
                }

                Divider(color = ZlColors.C_S2, thickness = 10.dp)

                Box(
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "暂不取消",
                        style = TextStyle(
                            color = ZlColors.C_B1,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier
                            .clickable {
                                scope.launch {
                                    onClick.invoke()
                                    state.hide()
                                }
                            }
                    )
                }

            }
        },
    ) { }
}