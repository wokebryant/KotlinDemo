package com.example.kotlindemo.task.linkage

import com.zhaopin.common.widget.linkage.bean.BaseGroupedItem


/**
 * @Description 二级联动数据
 * @Author LuoJia
 * @Date 2023/6/30
 */
interface KeepProtocol

class LinkageGroupItem : BaseGroupedItem<LinkageGroupItem.ItemInfo?> {

    // 头部构造器
    constructor(isHeader: Boolean, header: String?) : super(isHeader, header)
    // 内容构造器
    constructor(item: ItemInfo?) : super(item)

    data class ItemInfo(
        // header为分组标题
        val header: String?,
        // 下列字段为自定义字段
        val linkageItem: LinkageItem? = null
    ) : BaseGroupedItem.ItemInfo("title", header)

}

/**
 * C端筛选数据
 * 薪资单选，行业最多选择3个，其它无限制
 */
data class LinkageBean(
    val items: List<LinkageItem>
) : KeepProtocol

data class LinkageItem(
    val key: String = "",
    val title: String = "",
    val type: String = "",
    val scene: String = "",
    val showMore: Boolean = false,
    val multiple: Boolean = true,
    val list: MutableList<LinkageChildItem> = mutableListOf(),
    val slider: List<LinkageSlider> = emptyList()
) : KeepProtocol

data class LinkageChildItem(
    val code: String = "",
    val name: String = "",
    val type: String = "",
    val enName: String = "",
    val parentCode: String = "",
    val serial: String = "",
    val parentSerial: String = "",
    val mainPath: Boolean = false,
    val attributeValue: Map<String, String> = emptyMap(),
    // 本地数据，所属分组的type
    var parentType: String = ""
) : KeepProtocol

data class LinkageSlider(
    val code: String = "",
    val name: String = ""
) : KeepProtocol