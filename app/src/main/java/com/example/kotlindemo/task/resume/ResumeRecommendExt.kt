package com.example.kotlindemo.task.resume

import com.example.kotlindemo.R
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendBaseInfoDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendCardDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendEduExpDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendHighlightDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendJobExpectDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendLanguageDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendObtainedCertDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendProSkillDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendQCertDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendSelfIntroDelegate
import com.example.kotlindemo.task.resume.delegate.ResumeRecommendWorkExpDelegate
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.remove
import com.zhaopin.list.multitype.adapter.setItem

/**
 * @Description 简历点后推荐扩展
 * @Author LuoJia
 * @Date 2024/03/19
 */
internal fun MultiTypeAdapter.registerResumeRecommendList(callback: IResumeRecommendCallback) = this.apply {
    register(ResumeRecommendCardDelegate(callback))
}

/**
 * 注册简历推荐卡片子模块
 */
internal fun MultiTypeAdapter.registerResumeRecommendCard() = this.apply {
    // 基础信息
    register(ResumeRecommendBaseInfoDelegate())
    // 求职期望
    register(ResumeRecommendJobExpectDelegate())
    // 工作经历
    register(ResumeRecommendWorkExpDelegate())
    // 资格证书
    register(ResumeRecommendQCertDelegate())
    // 教育经历
    register(ResumeRecommendEduExpDelegate())
    // 语言能力
    register(ResumeRecommendLanguageDelegate())
    // 所获证书
    register(ResumeRecommendObtainedCertDelegate())
    // 专业技能
    register(ResumeRecommendProSkillDelegate())
    // 履历亮点
    register(ResumeRecommendHighlightDelegate())
    // 自我介绍
    register(ResumeRecommendSelfIntroDelegate())
}

/**
 * 删除简历推荐Item
 */
internal fun MultiTypeAdapter.removeResumeRecommendItem(index: Int, item: Any) {
    if (index < 0) {
        return
    }
    remove(item)
}

/**
 * 更新简历推荐Item
 */
internal fun MultiTypeAdapter.updateResumeRecommendItem(index: Int, item: Any) {
    if (index < 0) {
        return
    }
    setItem(index, item)
}

/**
 * 将单个简历详情接口数据转化成UI State
 */
internal fun ResumeRecommendBean.toMockUiList(): List<Any> {
    val resumeList = this.list?.mapIndexed { index, _ ->
        ResumeRecommendCardState(
            list = composeResumeCardList(index),
            isShowInappropriate = index == 0,
            isCollect = index == 1,
            isGreet = index == 2,
            showError = false
        )
    }
    return resumeList ?: emptyList()
}

fun composeResumeCardList(index: Int): List<Any> {
    val list = mutableListOf<Any>()
    // 1. 组装基础信息卡片
    val baseInfoState = BaseInfoState(
        name = "郭先生$index",
        isOnline = true,
        distance = "距你1千米",
        avatar = "",
        gender = 1,
        jobAndCompany = "产品经理·智联招聘",
        dutyTime = "离校-随时到岗",
        tagList = mutableListOf(
            BaseInfoState.TagState(R.drawable.ic_edu, "2年"),
            BaseInfoState.TagState(R.drawable.ic_edu, "24岁"),
            BaseInfoState.TagState(R.drawable.ic_edu, "硕士· 广西师范大学"),
            BaseInfoState.TagState(R.drawable.ic_edu, "现居 北京朝阳 · 户籍 安徽合肥"),
        ),
//        desc = "3年产品经理工作经验，熟练掌握BS和CS架构测试方法，参与日常需求评审提出合理化建议，制定测试方案，熟悉常用liunx命令部署环境3年产品经理工作经验，熟练掌握BS和CS架构测试方法，参与日常需求跟进..."
        desc = "3年产品经理工作经验, 3年产品经理工作经验, 3年产品经理工作经验, 3年产品经理工作经验, 3年产品经理工作经验, 3年产品经理工作经验3年产品经理工作经验, 3年产品经理工作经验, 3年产品经理工作经验3年产品经理工作经验, 3年产品经理工作经验, 3年产品经理工作经验"
    )
    list.add(baseInfoState)

    // 2. 组装求职期望
    val jobExpectState = JobExpectState(
        jobExpectList = mutableListOf(
            JobExpectState.ItemState(
                jobName = "内容运营·北京",
                salary = "10K-15K",
                desc = "全职/兼职    期望行业：房地产开发与经营/土地与公共设施管理/广播、电视、电影录音制作。"
            )
        )
    )
    list.add(jobExpectState)

    // 3. 组装工作经历
    val workExpState = WorkExpState(
        workExpList = mutableListOf(
            WorkExpState.ItemState(
                companyName = "北京新知百略科技有限公司",
                industryAndTime = "互联网  2015.03-2020.04(14年)",
                jobName = "自媒体·微信/知乎账户运营",
                jobDesc = "1.通过分析数据挖",
                tagList = mutableListOf("商务合作", "销售员", "动效设计"),
                itemSize = 3
            ),
            WorkExpState.ItemState(
                companyName = "北京网聘技术有限公司",
                industryAndTime = "互联网  1997.03-2024.04(14年)",
                jobName = "自媒体·微信/知乎账户运营",
                jobDesc = "1.通过通过分析数据挖掘用户行为及通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反反" +
                        "通过通过分析数据挖掘用户行为及通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反通过通过分析数据挖掘用户行为及反反",
                tagList = mutableListOf("招聘", "销售", "互联网"),
                itemSize = 3
            ),
            WorkExpState.ItemState(
                companyName = "阿里巴巴",
                industryAndTime = "互联网  1997.03-2024.04(14年)",
                jobName = "自媒体·微信/知乎账户运营",
                jobDesc = "1.通过通过分析数据挖掘用户行为及反",
                tagList = mutableListOf("招聘", "销售", "互联网"),
                itemSize = 3
            ),
        )
    )
//    list.add(workExpState)

    // 4. 组装资格证书
    val certQCertState = QualificationCertState(
        certList = mutableListOf("大学英语四级", "雅思6.5")
    )

    // 5. 组装教育经历
    val eduExpState = EducationExpState(
        eduList = mutableListOf(
            EducationExpState.ItemState(
                schoolName = "北京大学",
                schoolTag = "",
                schoolTime = "2019.10-2020.02",
                majorName = "本科·新闻传播",
                majorDesc = "在校担任新闻部部部长，负责学校公众号内容撰写以及运营。在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营"
            ),
            EducationExpState.ItemState(
                schoolName = "清华大学",
                schoolTag = "",
                schoolTime = "2019.10-2020.02",
                majorName = "硕士·新闻",
                majorDesc = "在校担任新闻部部部长，负责学校公众号内容撰写以及运营。在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营"
            ),
            EducationExpState.ItemState(
                schoolName = "人民大学",
                schoolTag = "",
                schoolTime = "2019.10-2020.02",
                majorName = "硕士·新闻",
                majorDesc = "在校担任新闻部部部长，负责学校公众号内容撰写以及运营。在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营"
            ),
            EducationExpState.ItemState(
                schoolName = "复旦大学",
                schoolTag = "",
                schoolTime = "2019.10-2020.02",
                majorName = "硕士·新闻",
                majorDesc = "在校担任新闻部部部长，负责学校公众号内容撰写以及运营。在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营在校担任新闻部部部长，负责学校公众号内容撰写以及运营"
            )
        )
    )
    list.add(workExpState)
    list.add(eduExpState)
    list.add(certQCertState)

    // 6. 组装语言能力
    val languageState = LanguageSkillState(
        languageList = mutableListOf(
            LanguageSkillState.ItemState(
                languageName = "英语",
                readSkill = "读写能力：标准",
                speakSkill = "听说能力：标准"
            ),
            LanguageSkillState.ItemState(
                languageName = "德语",
                readSkill = "读写能力：标准",
                speakSkill = "听说能力：标准"
            )
        )
    )
    list.add(languageState)

    // 7. 组装证书
    val obtainedCertState = ObtainedCertState(
        certList = mutableListOf(
            ObtainedCertState.ItemState(
                certName = "全国计算机应用技术证书",
                date = "2019.10"
            ),
            ObtainedCertState.ItemState(
                certName = "全球Adobe认证设计师",
                date = "2019.10"
            )
        )
    )
    list.add(obtainedCertState)

    // 8. 组装专业技能
    val proSkillState = ProSkillState(
        skillList = mutableListOf(
            ProSkillState.ItemState(
                skill = "Proe ug caita 三维模型设计",
                year = " | 10年以上"
            ),
            ProSkillState.ItemState(
                skill = "Saas销售模型",
                year = " | 3年以上"
            ),
            ProSkillState.ItemState(
                skill = "用户增长模型",
                year = " | 10年以上"
            )
        )
    )
    list.add(proSkillState)

    // 9. 组装履历亮点
    val highlightState = ResumeHighlightState(
        highlightList = mutableListOf(
            "·薪资、行业背景与岗位相符",
            "·6年互联网工作经验、5年JAVA工程师经验",
            "·3年百度经验、985学历"
        )
    )
    list.add(highlightState)

    // 10. 组装自我介绍
    val selfIntroState = SelfIntroState(
        skillList = mutableListOf("Web前端", "移动研发"),
        content = "专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。" +
                "专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。" +
                "专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。" +
                "专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。" +
                "专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。专业能力MAX，有领导力，善于分享，可以带领团队走的更远，自信有活力，善于组织。"
    )
    list.add(selfIntroState)

    return list
}


