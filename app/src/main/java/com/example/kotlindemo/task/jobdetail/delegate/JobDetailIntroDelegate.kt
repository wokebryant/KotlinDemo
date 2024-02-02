package com.example.kotlindemo.task.jobdetail.delegate

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.JobDetailItemIntroBinding
import com.example.kotlindemo.task.jobdetail.CurInterviewContentState
import com.example.kotlindemo.task.jobdetail.CurInterviewPanelData
import com.example.kotlindemo.task.jobdetail.IntroState
import com.example.kotlindemo.task.jobdetail.IntroTagData
import com.example.kotlindemo.task.jobdetail.SameDayInterviewPanel
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 职位详情页简介卡
 * @Author LuoJia
 * @Date 2023/11/22
 */
class JobDetailIntroDelegate : BindingViewDelegate<IntroState, JobDetailItemIntroBinding>() {

    override fun onBindViewHolder(
        binding: JobDetailItemIntroBinding,
        item: IntroState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(binding: JobDetailItemIntroBinding, item: IntroState) {
        Log.i("LuoJia-RE", "JobDetailIntroDelegate")
        with(binding) {
            tvJobName.text = item.jobName
            tvSalary.text = item.salary
            tvStreet.text = item.street
            tvRouteTime.text = item.routeTime
            // 设置标签
            setTagFlowLayout(binding.layoutFlow, item.tagList)

            val flipperList = mutableListOf("100%回复，当天约面试")
            layoutFlipper.startTextFlipper(flipperList)
            llCurInterview.onClick {
                val mockData = CurInterviewPanelData(
                    icon = "",
                    title = "什么是当天面职位？",
                    contentList = mutableListOf(
                        CurInterviewContentState(
                            content = "工作日00:00-16:00投递的简历，平台要求招聘方须在当天回复",
                            color = "#4E5366"
                        ),
                        CurInterviewContentState(
                            content = "工作日16:00-24:00、双休日及法定节假日投递的简历，平台要求招聘方最晚须在下个工作日回复",
                            color = "#426EFF"
                        )
                    )
                )
                (currentActivity() as? FragmentActivity)?.let {
                    SameDayInterviewPanel.newInstance(mockData).show(it.supportFragmentManager)
                }
            }
        }
    }

    private fun setTagFlowLayout(flowLayout: TagFlowLayoutOrigin, tagList: List<IntroTagData>) {
        val tagAdapter = object : TagAdapterOrigin<IntroTagData>(tagList) {
            override fun getView(parent: FlowLayoutOrigin?, position: Int, tag: IntroTagData): View {
                val layout =  LayoutInflater.from(curContext)
                    .inflate(R.layout.job_detail_item_intro_tag, parent, false) as LinearLayout
                val iconView = layout.findViewById<ImageView>(R.id.iv_icon)
                val contentTextView = layout.findViewById<TextView>(R.id.tv_tag)
                iconView.setImageResource(tag.icon)
                contentTextView.text = tag.tagName
                return layout
            }
        }
        flowLayout.adapter = tagAdapter
    }

}