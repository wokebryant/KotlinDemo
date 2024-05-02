package com.example.kotlindemo.task.blueedit.inter

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/04/28
 */
interface IBlueResumeCallback {

    fun onThirdLevelTagClick(
        itemPosition: Int,
        tagPosition: Int,
        selectedList: MutableSet<Int>,
        fromFoldItem: Boolean,
        isAdd: Boolean
    )

    fun onThirdLevelExpandClick(position: Int, selectedList: MutableSet<Int>)
}

