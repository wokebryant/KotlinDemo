package com.example.kotlindemo.task.nps

import com.example.kotlindemo.R
import com.example.kotlindemo.study.mvi.core.IUiEvent
import com.example.kotlindemo.study.mvi.core.IUiState
import com.zhaopin.common.widget.mvx.flowLayout.MVXTagUIState
import java.io.Serializable

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/07/05
 */

sealed class NPSEvent : IUiEvent {

}

data class NPSState(
    val title: String = "",
    val selectedIndex: Int = 1,
    val buttonEnable: Boolean = false,
    val tagTitle: String = "",
    val tagList: List<NPSTagState> = emptyList()
) : IUiState

data class PositionNPSState(
    val selectedIndex: Int = 1,
    val faceList: List<Face> = emptyList()
) : Serializable, IUiState {

    data class Face(
        val lottieUrl: String,
        val tagList: List<NPSTagState>
    ) : Serializable

}

data class NPSTagState(
    override val name: String,
    override val selected: Boolean
) : MVXTagUIState, Serializable

val testNPSState = PositionNPSState(
    selectedIndex = 2,
    faceList = listOf(
        PositionNPSState.Face(
            lottieUrl = "",
            tagList = listOf(
                NPSTagState(
                    name = "大多和搜索词不相关",
                    selected = false
                ),
                NPSTagState(
                    name = "很多不是求职意向职位",
                    selected = false
                ),
                NPSTagState(
                    name = "想找的职位排序靠后",
                    selected = false
                ),
                NPSTagState(
                    name = "招聘者都不活跃",
                    selected = false
                )
            )
        ),
        PositionNPSState.Face(
            lottieUrl = "",
            tagList = listOf(
                NPSTagState(
                    name = "大多和搜索词不相关2",
                    selected = false
                ),
                NPSTagState(
                    name = "很多不是求职意向职位2",
                    selected = false
                ),
                NPSTagState(
                    name = "想找的职位排序靠后2",
                    selected = false
                ),
                NPSTagState(
                    name = "招聘者都不活跃2",
                    selected = false
                ),
            ),
        ),
        PositionNPSState.Face(
            lottieUrl = "",
            tagList = listOf(

            ),
        ),
        PositionNPSState.Face(
            lottieUrl = "",
            tagList = listOf(

            ),
        ),
        PositionNPSState.Face(
            lottieUrl = "",
            tagList = listOf(

            ),
        ),
    )
)