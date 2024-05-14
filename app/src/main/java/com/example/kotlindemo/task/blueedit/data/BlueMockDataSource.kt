package com.example.kotlindemo.task.blueedit.data

import com.example.kotlindemo.task.blueedit.bean.BlueResumeEditQuestionBean
import com.example.kotlindemo.task.blueedit.bean.DurationInMonthBean
import com.example.kotlindemo.task.blueedit.bean.WorkPreferenceBean

/**
 * @Description 蓝领编辑问题测试数据
 * @Author LuoJia
 * @Date 2024/04/29
 */

object BlueMockDataSource {
    fun getMockQABean() = BlueResumeEditQuestionBean(
        durationInMonthsQuestion = mockDurationInMonth,
        workPreferenceQuestionList = listOf(
            addressQA,
            advantagesQA,
            customQA
        )
    )
}

val mockDurationInMonth = DurationInMonthBean(
    title = "传菜员干多久了",
    userSaveMonths = DurationInMonthBean.TagState(
        durationInMonthsValue = "3",
        name = "6个月"
    ),
    hasThisQuestion = true,
    durationInMonthsList = listOf(
        DurationInMonthBean.TagState(
            durationInMonthsValue = "1",
            name = "1个月"
        ),
        DurationInMonthBean.TagState(
            durationInMonthsValue = "3",
            name = "3个月"
        ),
        DurationInMonthBean.TagState(
            durationInMonthsValue = "6",
            name = "6个月"
        ),
        DurationInMonthBean.TagState(
            durationInMonthsValue = "12",
            name = "1年"
        ),
        DurationInMonthBean.TagState(
            durationInMonthsValue = "6",
            name = "2年"
        ),
        DurationInMonthBean.TagState(
            durationInMonthsValue = "6",
            name = "3年"
        ),
        DurationInMonthBean.TagState(
            durationInMonthsValue = "6",
            name = "4年"
        ),
        DurationInMonthBean.TagState(
            durationInMonthsValue = "6",
            name = "5年"
        ),
    )
)

val addressQA: WorkPreferenceBean = WorkPreferenceBean(
    title = "你在哪上过班",
    questionId = "111",
    type = 1,
    questionType = "work_address",
    level = "2",
    must = true,
    max = 3,
    answerList = mutableListOf(
        WorkPreferenceBean.Answer(
            name = "地点1",
            id = "location1",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "地点2",
            id = "location2",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "地点3",
            id = "location3",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "地点4",
            id = "location4",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "地点5",
            id = "location5",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "地点6",
            id = "location6",
            selected = false,
            subQuestionList = emptyList()
        ),
    )
)

val advantagesQA = WorkPreferenceBean(
    title = "你有哪些优势",
    questionId = "222",
    type = 2,
    questionType = "advantages",
    level = "2",
    must = true,
    max = 3,
    answerList = mutableListOf(
        WorkPreferenceBean.Answer(
            name = "",
            id = "",
            selected = false,
            subQuestionList = mutableListOf(
                WorkPreferenceBean.SubQuestion(
                    title = "性格优点",
                    questionId = "2221",
                    level = "3",
                    max = 3,
                    must = true,
                    parentQuestionId = "pp1",
                    parentAnswerId = "pp1",
                    questionType = "advantages",
                    subAnswerList = mutableListOf(
                        WorkPreferenceBean.SubAnswer(
                            name = "性格优点1",
                            id = "ad1",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "性格优点2",
                            id = "ad2",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "性格优点3",
                            id = "ad3",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "性格优点4",
                            id = "ad4",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "性格优点5",
                            id = "ad5",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "性格优点6",
                            id = "ad6",
                            selected = false
                        ),
                    )
                )
            )
        ),
        WorkPreferenceBean.Answer(
            name = "",
            id = "",
            selected = false,
            subQuestionList = mutableListOf(
                WorkPreferenceBean.SubQuestion(
                    title = "工作优点",
                    questionId = "2222",
                    level = "3",
                    max = 3,
                    must = true,
                    parentQuestionId = "pp2",
                    parentAnswerId = "pp2",
                    questionType = "advantages",
                    subAnswerList = mutableListOf(
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点1",
                            id = "xx1",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点2",
                            id = "xx2",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点3",
                            id = "xx3",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点4",
                            id = "xx4",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点5",
                            id = "xx5",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点6",
                            id = "xx6",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点7",
                            id = "xx7",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点8",
                            id = "xx8",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点9",
                            id = "xx9",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点10",
                            id = "xx10",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点11",
                            id = "xx12",
                            selected = false
                        ),
                        WorkPreferenceBean.SubAnswer(
                            name = "工作优点12",
                            id = "xx13",
                            selected = false
                        ),
                    )
                )
            )
        )
    )
)

val customQA = WorkPreferenceBean(
    title = "已添加自定义标签",
    questionId = "333",
    type = 3,
    questionType = "other",
    level = "2",
    must = true,
    max = 3,
    answerList = mutableListOf(
        WorkPreferenceBean.Answer(
            name = "哈哈",
            id = "cu1",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "桃子",
            id = "cu2",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "苹果",
            id = "cu3",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "例子例子例子",
            id = "cu4",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "香蕉",
            id = "cu5",
            selected = false,
            subQuestionList = emptyList()
        ),
        WorkPreferenceBean.Answer(
            name = "西瓜",
            id = "cu6",
            selected = false,
            subQuestionList = emptyList()
        ),
    )
)