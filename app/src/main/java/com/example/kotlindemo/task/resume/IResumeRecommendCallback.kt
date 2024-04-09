package com.example.kotlindemo.task.resume

/**
 * @Description 简历点后推回调
 * @Author LuoJia
 * @Date 2024/03/19
 */
interface IResumeRecommendCallback {

    /** 举报 */
    fun onReportClick()

    /** 分享 */
    fun onShareClick()

    /** 不合适点击 */
    fun onInappropriateClick()

    /** 收藏点击 */
    fun onCollectClick()

    /** 打电话点击 */
    fun onCallClick()

    /** 打招呼/继续沟通点击 */
    fun onGreetOrChatClick()

    /** 撤销不合适点击 */
    fun onCancelInappropriateClick()

}