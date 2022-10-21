package com.example.kotlindemo.task.appbar

/**
 * @Description
 * @Author
 * @Date
 */
interface ICampusHomeEvent {

    /** 简历提示跳转点击 */
    fun onResumeTipJumpClick()
    /** 简历提示取消点击 */
    fun onResumeCancelClick()
    /** 直播招聘更多点击 */
    fun onMoreLiveClick()
    /** 直播轮播Item点击 */
    fun onLiveItemClick(position: Int)
    /** 校招活动更多点击 */
    fun onMoreActivityClick()
    /** 校招活动顶部item点击 */
    fun onActivityTopItemClick()
    /** 校招活动底部Item点击 */
    fun onActivityBottomItemClick()
    /** 底部金刚区左边按钮点击 */
    fun onBottomLeftBottomClick()
    /** 底部金刚区中间按钮点击 */
    fun onBottomLeftCenterClick()
    /** 底部金刚区右边按钮点击 */
    fun onBottomRightCenterClick()

}