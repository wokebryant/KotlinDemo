package com.example.kotlindemo.study.mvi

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/19
 */
class MviSampleRepository(
    private val netApi: Any? = null
) {

    var itemList: MutableList<MviListItemState> = mutableListOf()

    private val mockStringList = mutableListOf(
        "Android Studio Giraffe 发布，快来看有什么更新吧",
        "这么好的Android开发辅助工具App不白嫖可惜了",
        "使用 AndroidX 增强 WebView 的能力",
        "我的又一个神奇的框架——Skins换肤框架",
        "2023年的现代安卓开发",
        "Android 即将进入大AI时代",
        "软硬兼施，让废旧的 Android 手机变身家庭监控",
        "工信部又出新规！爬坑指南",
        "终于搞明白了什么是同步屏障",
        "又想做屏保了，这次用Compose做个蜂窝墙",
        "像支付宝那样“致敬”第三方开源代码",
        "用Compose又做了三个挺吼看的loading动效",
        "Android 当你需要读一个 47M 的 json.gz 文件",
        "\uD83D\uDD25利用Camerax实现一个相机应用，爽歪歪～Android",
        "【世纪纠结】Jetpack Compose 和自定义 View，学哪个？",
        "ViewModel 通常负责处理特定用户事件的业务逻辑",
        "界面行为逻辑在实现方面可能有所不同",
        "网域和数据层通常负责处理此逻辑",
        "例如导航逻辑或如何向用户显示消息",
        "刷新屏幕上的数据",
    )

    suspend fun requestListData(): MutableList<MviListItemState> {
        val itemList = mutableListOf<MviListItemState>()
        val pageSize = (20..25).random()
        repeat(pageSize) {
            val string = mockStringList.random()
            val item = MviListItemState(content = "$string \n下标: $it", position = it)
            itemList.add(item)
        }

        itemList.random()
        return itemList
    }

    suspend fun requestLoadMoreListData(): MutableList<MviListItemState> {
        val loadMoreList = mutableListOf<MviListItemState>()
        for (i in 0 until 20) {
            val item = MviListItemState(content = "这是加载更多测试数据 $i", position = i)
            loadMoreList.add(item)
        }
        itemList.addAll(loadMoreList)
        return loadMoreList
    }

    suspend fun requestTopData(): String {
        return "当前时间: " + System.currentTimeMillis()
    }

    fun remove(item: MviListItemState) {
        itemList.remove(item)
    }

    fun update(item: MviListItemState) {
        val newItem = item.copy(content = "我更新了")
        val updateIndex = itemList.indexOf(item)
        itemList.removeAt(updateIndex)
        itemList.add(updateIndex, newItem)
    }

}