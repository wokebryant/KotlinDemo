package com.example.kotlindemo.task.appbar

/**
 * @Description 校园首页直播Banner工具类
 * @Author LuoJia
 * @Date 2022-10-13
 */
object CampusBannerUtil {

    /**
     * 最大页面数
     */
    const val MAX_VALUE = 1000

    /**
     * @param position  当前position
     * @param pageSize  轮播图页面数
     * @return 真实的position
     */
    fun getRealPosition(position: Int, pageSize: Int): Int {
        return if (pageSize == 0) {
            0
        } else {
            (position + pageSize) % pageSize
        }
    }

    /**
     * @param pageSize 轮播图页面数
     * @return 轮播图初始位置
     */
    fun getOriginalPosition(pageSize: Int): Int {
        return MAX_VALUE / 2 - MAX_VALUE / 2 % pageSize
    }
}