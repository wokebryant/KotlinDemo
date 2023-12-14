package com.example.kotlindemo.task.jobdetail.delegate

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.JobDetailItemDescBinding
import com.example.kotlindemo.databinding.LayoutJobDetailTipBinding
import com.example.kotlindemo.task.jobdetail.DescState
import com.example.kotlindemo.task.jobdetail.onJClick
import com.zhaopin.common.widget.flowLayout.origin.FlowLayoutOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagAdapterOrigin
import com.zhaopin.common.widget.flowLayout.origin.TagFlowLayoutOrigin
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.appbase.util.currentActivity

/**
 * @Description 职位详情页职位描述卡
 * @Author LuoJia
 * @Date 2023/11/22
 */
class JobDetailDescDelegate : BindingViewDelegate<DescState, JobDetailItemDescBinding>() {

    override fun onBindViewHolder(
        binding: JobDetailItemDescBinding,
        item: DescState,
        position: Int
    ) {
        setView(binding, item)
    }

    private fun setView(binding: JobDetailItemDescBinding, item: DescState) {
        Log.i("LuoJia-RE", "JobDetailDescDelegate")

        with(binding) {
            // 技能标签
            setTagFlowLayout(layoutFlowSkill, item.skillTagList)
            // 职位描述
            tvDesc.setText(item.descContent)
            // 奖金绩效
            tvBonusPerformance.text = item.bonusPerformance
            // 工作/休息时间
            tvWorkingTime.text = item.workTime
            // 福利标签
            setTagFlowLayout(layoutFlowBless, item.blessTagList)
            ivWarning.onJClick {
                showPopWindow(ivWarning)
            }
        }
    }

    /**
     * 设置标签(技能/福利)
     */
    private fun setTagFlowLayout(flowLayout: TagFlowLayoutOrigin, tagList: List<String>) {
        val tagAdapter = object : TagAdapterOrigin<String>(tagList) {
            override fun getView(parent: FlowLayoutOrigin?, position: Int, str: String?): View {
                val textView =  LayoutInflater.from(curContext)
                    .inflate(R.layout.job_detail_item_desc_tag, parent, false) as TextView
                textView.text = str
                return textView
            }
        }
        flowLayout.adapter = tagAdapter
    }

    fun showPopWindow(anchorView: View) {
        currentActivity()?.let {
            val contentView = LayoutJobDetailTipBinding.inflate(LayoutInflater.from(it), null, false).root
            val popupWindow = PopupWindow(
                contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                isTouchable = true
                isOutsideTouchable = true
            }
            popupWindow.showAsDropDown(anchorView)
        }
    }

}