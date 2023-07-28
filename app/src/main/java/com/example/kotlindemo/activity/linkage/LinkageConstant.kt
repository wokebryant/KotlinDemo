package com.example.kotlindemo.activity.linkage

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/4
 *
 * 老推荐：基础数据可获取：学历，工作经验，公司性质，公司规模
 *       接口获取：月薪，行业
 *       新增：职位类型 ---> 从基础数据获取
 *
 * 搜索：基础数据可获取：学历，发布时间，工作经验，职位类型，公司性质，公司规模
 *      接口获取：月薪，行业
 */

object LinkageConstant {
    const val HEADER_SALARY = "月薪"
    const val HEADER_EDU = "学历"
    const val HEADER_PUBLISH = "发布时间"
    const val HEADER_EXP = "经验"
    const val HEADER_JOB_TYPE = "职位类型"
    const val HEADER_INDUSTRY = "行业"
    const val HEADER_COMPANY_NATURE = "公司性质"
    const val HEADER_COMPANY_SCALE = "公司规模"

    /**
     * 模拟服务端返回的数据
     */
    fun requestLinkageData(): List<LinkageItem> {
        val allData = mutableListOf<LinkageItem>()
        // 1 月薪
        val salaryData = LinkageItem(
            title = "月薪",
            type = "salary_for_search",
            showMore = false,
            multiple = false,
            list = mutableListOf(
                LinkageChildItem(code = "-1", name = "不限"),
                LinkageChildItem(code = "000000004000", name = "4千以下"),
                LinkageChildItem(code = "004000006000", name = "4千-6千"),
                LinkageChildItem(code = "006000008000", name = "6千-8千"),
                LinkageChildItem(code = "008000010000", name = "8千-1万"),
                LinkageChildItem(code = "010000015000", name = "1万-1.5万"),
                LinkageChildItem(code = "015000025000", name = "1.5万-2.5万"),
                LinkageChildItem(code = "025000030000", name = "2.5万-3.5万"),
                LinkageChildItem(code = "035000050000", name = "3.5万-5万"),
                LinkageChildItem(code = "050000999999", name = "5万以上"),
            ),
            slider = mutableListOf(
                LinkageSlider(code = "000000", name = "0"),
                LinkageSlider(code = "001000", name = "1千"),
                LinkageSlider(code = "002000", name = "2千"),
                LinkageSlider(code = "003000", name = "3千"),
                LinkageSlider(code = "004000", name = "4千"),
                LinkageSlider(code = "005000", name = "5千"),
                LinkageSlider(code = "006000", name = "6千"),
                LinkageSlider(code = "007000", name = "7千"),
                LinkageSlider(code = "008000", name = "8千"),
                LinkageSlider(code = "009000", name = "9千"),
                LinkageSlider(code = "010000", name = "1万"),
                LinkageSlider(code = "015000", name = "1.5万"),
                LinkageSlider(code = "020000", name = "2万"),
                LinkageSlider(code = "025000", name = "2.5万"),
                LinkageSlider(code = "030000", name = "3万"),
                LinkageSlider(code = "035000", name = "3.5万"),
                LinkageSlider(code = "040000", name = "4万"),
                LinkageSlider(code = "050000", name = "5万"),
                LinkageSlider(code = "060000", name = "6万"),
                LinkageSlider(code = "070000", name = "7万"),
                LinkageSlider(code = "080000", name = "8万"),
                LinkageSlider(code = "090000", name = "9万"),
                LinkageSlider(code = "999999", name = "10万"),
            )
        )
        allData.add(salaryData)
        // 2 学历
        val eduData = LinkageItem(
            title = "学历",
            type = "education",
            showMore = false,
            multiple = true,
            list = mutableListOf(
                LinkageChildItem(code = "-1", name = "不限"),
                LinkageChildItem(code = "201", name = "初中及以下"),
                LinkageChildItem(code = "202", name = "中专/中技"),
                LinkageChildItem(code = "203", name = "高中"),
                LinkageChildItem(code = "204", name = "大专"),
                LinkageChildItem(code = "205", name = "本科"),
                LinkageChildItem(code = "206", name = "硕士"),
                LinkageChildItem(code = "207", name = "MBA/EMBA"),
                LinkageChildItem(code = "208", name = "博士"),
            )
        )
        allData.add(eduData)
        // 3 经验
        val expData = LinkageItem(
            title = "经验",
            type = "work_experience",
            showMore = false,
            multiple = true,
            list = mutableListOf(
                LinkageChildItem(code = "-1", name = "全部"),
                LinkageChildItem(code = "301", name = "无经验"),
                LinkageChildItem(code = "302", name = "1年以下"),
                LinkageChildItem(code = "303", name = "1-3年"),
                LinkageChildItem(code = "304", name = "3-5年"),
                LinkageChildItem(code = "305", name = "5-10年"),
                LinkageChildItem(code = "306", name = "10年以上"),
            )
        )
        allData.add(expData)
        // 4 职位类型
        val jobTypeData = LinkageItem(
            title = "职位类型",
            type = "employment_type",
            showMore = false,
            multiple = true,
            list = mutableListOf(
                LinkageChildItem(code = "-1", name = "不限"),
                LinkageChildItem(code = "401", name = "全职"),
                LinkageChildItem(code = "402", name = "兼职/临时"),
                LinkageChildItem(code = "403", name = "实习"),
                LinkageChildItem(code = "404", name = "校园"),
                LinkageChildItem(code = "405", name = "5-10年"),
                LinkageChildItem(code = "406", name = "10年以上"),
            )
        )
        allData.add(jobTypeData)
        // 5 行业
        val industryData = LinkageItem(
            title = "行业",
            type = "industry",
            showMore = true,
            multiple = true,
            list = mutableListOf(
                LinkageChildItem(code = "-1", name = "不限"),
                LinkageChildItem(code = "501", name = "医药制造"),
                LinkageChildItem(code = "502", name = "化学原料/化学制品"),
                LinkageChildItem(code = "503", name = "化工"),
                LinkageChildItem(code = "504", name = "石化/石油"),
                LinkageChildItem(code = "505", name = "新能源"),
//                LinkageChildItem(code = "506", name = "生物工程"),
//                LinkageChildItem(code = "507", name = "环保"),
//                LinkageChildItem(code = "508", name = "学士/科研"),
//                LinkageChildItem(code = "509", name = "日化产品制造"),
            )
        )
        allData.add(industryData)
        // 6 公司性质
        val companyNatureData = LinkageItem(
            title = "公司性质",
            type = "company_type_for_search",
            showMore = false,
            multiple = true,
            list = mutableListOf(
                LinkageChildItem(code = "-1", name = "不限"),
                LinkageChildItem(code = "601", name = "国企"),
                LinkageChildItem(code = "602", name = "外企"),
                LinkageChildItem(code = "603", name = "合资"),
                LinkageChildItem(code = "604", name = "民营"),
                LinkageChildItem(code = "605", name = "上市公司"),
                LinkageChildItem(code = "606", name = "股份制企业"),
                LinkageChildItem(code = "607", name = "股份制企业"),
                LinkageChildItem(code = "608", name = "事业单位"),
                LinkageChildItem(code = "609", name = "其他"),
            )
        )
        allData.add(companyNatureData)
        // 7 公司规模
        val companyScaleData = LinkageItem(
            title = "公司规模",
            type = "company_size",
            showMore = false,
            multiple = true,
            list = mutableListOf(
                LinkageChildItem(code = "-1", name = "不限"),
                LinkageChildItem(code = "701", name = "20人以下"),
                LinkageChildItem(code = "702", name = "20-99人"),
                LinkageChildItem(code = "703", name = "100-299人"),
                LinkageChildItem(code = "704", name = "300-499人"),
                LinkageChildItem(code = "705", name = "500-999人"),
                LinkageChildItem(code = "706", name = "1000-9999人"),
                LinkageChildItem(code = "707", name = "10000人以上"),
            )
        )
        allData.add(companyScaleData)
        return allData
    }

    /**
     * 模拟从全部行业页面带过来的数据
     */
    fun getAllIndustryPageBackData() = mutableListOf(
        LinkageChildItem(code = "510", name = "Android开发", parentType = "industry"),
        LinkageChildItem(code = "501", name = "医药制造", parentType = "industry"),
        LinkageChildItem(code = "503", name = "化工", parentType = "industry"),
//        LinkageChildItem(code = "511", name = "iOS开发"),
//        LinkageChildItem(code = "512", name = "Java开发"),
    )


}
