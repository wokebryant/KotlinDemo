package com.example.kotlindemo.task.afterdelivery

import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.appendItem
import com.zhaopin.list.multitype.adapter.setItem

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/06/07
 */

internal fun MultiTypeAdapter.addAfterDeliveryItem(item: Any) {
    appendItem(item)
}

internal fun MultiTypeAdapter.updateAfterDeliveryItem(index: Int, item: Any) {
    setItem(index, item)
}