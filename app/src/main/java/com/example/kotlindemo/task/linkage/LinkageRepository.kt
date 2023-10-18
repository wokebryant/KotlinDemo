package com.example.kotlindemo.task.linkage


/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/4
 */
class LinkageRepository {

    /** 二级联动页数据 */
    private var linkGroupItemList: MutableList<LinkageGroupItem> = mutableListOf()
    /** 原始行业筛选数据 */
    private var originIndustryItemList: MutableList<LinkageChildItem> = mutableListOf()
    /** 当前使用的行业筛选数据 */
    private var curIndustryItemList: MutableList<LinkageChildItem> = mutableListOf()
    /** 行业数据下标 */
    var industryItemIndex = 0
    /** 所有选中的列表 */
    var selectedList = mutableSetOf<LinkageChildItem>()

    /**
     * 请求筛选数据
     */
    fun requestFilterData(): MutableList<LinkageGroupItem>{
        val serverData = LinkageConstant.requestLinkageData()
        return transformServerDataToLocalData(serverData)
    }

    /**
     * 将服务端数据组装成本地数据
     */
    private fun transformServerDataToLocalData(serverData: List<LinkageItem>): MutableList<LinkageGroupItem> {
        serverData.forEachIndexed {index, item ->
            if (item.list.isEmpty()) {
                return@forEachIndexed
            }
            // 头部
            val header = LinkageGroupItem(isHeader = true, header = item.title)
            linkGroupItemList.add(header)
            // 内容
            item.list.map { it.apply { parentType = item.type } }
            val body = LinkageGroupItem(item = LinkageGroupItem.ItemInfo(item.title, item))
            linkGroupItemList.add(body)
            // 获取原始/当前使用的行业筛选数据
            if (item.type == "industry") {
                originIndustryItemList.addAll(item.list)
                curIndustryItemList = item.list
                industryItemIndex = 2 * index + 1
            }
        }
        return linkGroupItemList
    }

    /**
     * 获取从全部行业基础数据返回的筛选数据,并组装到本地数据
     */
    fun addBaseFilterDataToLocalData(curSelectedItems: Set<LinkageChildItem>) {
        val preSelectedItem = selectedList.filter { it.parentType == "industry" }
        // 把返回的新的数据先添加到行业列表
        val curNewSelectedItems = curSelectedItems.subtract(curIndustryItemList.toSet())
        curIndustryItemList.addAll(1, curNewSelectedItems)
        // 然后再删除没有被选中的非原始行业筛选数据
        val allNewSelectedItems = curIndustryItemList.subtract(originIndustryItemList.toSet())
        val removeSelectedItems = allNewSelectedItems.subtract(curSelectedItems)
        curIndustryItemList.removeAll(removeSelectedItems)
        // 更新顶部选择的tag
        val removeSelectedTagItems = preSelectedItem.subtract(curSelectedItems)
        val addSelectedTagItems = curSelectedItems.subtract(preSelectedItem.toSet())
        selectedList.removeAll(removeSelectedTagItems)
        selectedList.addAll(addSelectedTagItems)
    }

    /**
     * 获取行业Item选中的tag坐标
     */
    fun getIndustrySelectedIndexList(curSelectedItems: Set<LinkageChildItem>): List<Int> {
        val indexList = mutableListOf<Int>()
        curSelectedItems.forEach {
            val index = curIndustryItemList.indexOf(it)
            indexList.add(index)
        }
        return indexList
    }

    /**
     * 获取被删除的LinkItem下标和对应的Tag下标
     */
    fun getDeleteItemIndexData(childItem: LinkageChildItem): Pair<Int, Int> {
        val parentType = childItem.parentType
        val code = childItem.code

        var itemIndex = 0
        var tagIndex = 0
        // 框架会在列表尾部加入Footer,需要过滤掉
        linkGroupItemList.filterIsInstance<LinkageGroupItem>().forEachIndexed { index, linkageGroupItem ->
            if (parentType == linkageGroupItem.info?.linkageItem?.type) {
                itemIndex = index
                linkageGroupItem.info?.linkageItem?.list?.forEachIndexed { childIndex, linkageChildItem ->
                    if (code == linkageChildItem.code) {
                        tagIndex = childIndex
                    }
                }
            }
        }
        return Pair(itemIndex, tagIndex)
    }

    /**
     * 获取筛选数据长度
     */
    fun getFilterListSize() = linkGroupItemList.size

}