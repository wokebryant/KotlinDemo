package com.example.kotlindemo.task.appbar

import com.example.kotlindemo.R

/**
 * @Description
 * @Author
 * @Date
 */
/**
 * 测试数据
 */
val testIconList = arrayListOf(
    R.drawable.lf_combsend_laugh,
    R.drawable.lf_combsend_heart,
    R.drawable.lf_combsend_like
)

val testTextList = arrayListOf(
    "中国联通有限责任公司 在招职位607",
    "中国联通有限责任公司 在招职位608",
    "中国联通有限责任公司 在招职位609"
)

val textLiveCardUrlList = arrayListOf(
    "https://storage-public.zhaopin.cn/kongxuan/org/image/1665897744765696464/a2ae2a21ac039cd902ba04206.png",
    "https://ask-image.zhaopin.cn/liveCover/1665988570793_76Meup.jpg",
    "https://storage-public.zhaopin.cn/user/avatar/55c73eab83ec4d71b14af6c901e409ab/%E5%BE%AE%E4%BF%A1%E5%9B%BE%E7%89%87_20221010135630.jpg",
    "https://storage-public.zhaopin.cn/user/avatar/1649833296492788491/37f40760-0126-427a-b049-62842786b7f7.jpg",
    "https://storage-public.zhaopin.cn/bvideo/cover/1660295773920746109/804c7760-0ee5-43a1-b9e5-0a54016c3ee7.jpg",
)

val liveCardUrl = "https://storage-public.zhaopin.cn/kongxuan/org/image/1665897744765696464/a2ae2a21ac039cd902ba04206.png"

data class LiveCardModel(
    val url: String,
    var position: Int
)