package com.example.kotlindemo.task.ai.delegate

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.view.updateLayoutParams
import coil.load
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemAiRecommendJobBinding
import com.example.kotlindemo.databinding.LayoutAiRecommendJobDetailLabelItemBinding
import com.example.kotlindemo.databinding.LayoutAiRecommendJobDetailSpanItemBinding
import com.example.kotlindemo.databinding.LayoutAiRecommendJobDetailTextItemBinding
import com.example.kotlindemo.databinding.LayoutAiRecommendTipBinding
import com.example.kotlindemo.databinding.LayoutCurInterviewPopBinding
import com.example.kotlindemo.task.ai.AiJobState
import com.example.kotlindemo.task.ai.IAiRecommendCallback
import com.example.kotlindemo.task.ai.JobInterpretState
import com.example.kotlindemo.task.ai.util.AiRecommendManager
import com.example.kotlindemo.utils.boldSpan
import com.example.kotlindemo.utils.colorSpan
import com.example.kotlindemo.utils.getColor
import com.zhaopin.common.widget.flowLayout.FlowLayout
import com.zhaopin.common.widget.flowLayout.NoActionTagLy
import com.zhaopin.common.widget.flowLayout.TagAdapter
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.appcommon.c.OsUtils
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast

/**
 * @Description AI推荐职位卡
 * @Author LuoJia
 * @Date 2024/1/12
 */
@SuppressLint("InflateParams, SetTextI18n")
class AiRecommendJobDelegate(
    private val callback: IAiRecommendCallback? = null
) : BindingViewDelegate<AiJobState, ItemAiRecommendJobBinding>() {

    /** 职位解读适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(JobInterpretState::class.java).to(
                JobInterpretLabelDelegate(),
                JobInterpretTextDelegate(),
                JobInterpretSpanDelegate()
            ).withKotlinClassLinker { _, item ->
                when (item.contentType) {
                    1 -> JobInterpretLabelDelegate::class
                    2 -> JobInterpretTextDelegate::class
                    3 -> JobInterpretSpanDelegate::class
                    else -> JobInterpretTextDelegate::class
                }
            }
        }
    }

    override fun onBindViewHolder(
        binding: ItemAiRecommendJobBinding,
        item: AiJobState,
        position: Int
    ) {
        setView(binding, item, position)
//        binding.flMask.onClick {
//            curContext.showToast("点击职位解读")
//        }
//        binding.inHrInfo.root.onClick {
//            curContext.showToast("点击Hr")
//        }
    }

    private fun setView(
        binding: ItemAiRecommendJobBinding,
        item: AiJobState,
        position: Int
    ) {
        setCardHeight(binding)
        // 基础信息
        setBaseInfo(binding, item, position)
        // 职位信息
        binding.root.postDelayed({
            setJobDetail(binding, item)
        }, 150)
        // HR信息
        setHrInfo(binding, item)
        // 负反馈点击
        binding.llFeedback.onClick {
            callback?.onFeedbackClick(position, item)
        }
    }

    /**
     * 设置卡片高度
     */
    private fun setCardHeight(
        binding: ItemAiRecommendJobBinding
    ) {
        val cardHeight = AiRecommendManager.getJobCardHeight()
        binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
            height = cardHeight
        }
    }

    /**
     * 设置基础信息
     */
    private fun setBaseInfo(
        binding: ItemAiRecommendJobBinding,
        item: AiJobState,
        position: Int
    ) {
        with(binding) {
            tvJobName.text = item.jobName
            inCurInterview.tvContent.text = "100%回复，当天约面试"
            tvSalary.text = item.salary
            tvCompanyName.text = item.companyName
            tvCompanySize.text = item.companySize
            tvAddress.text = item.address
            tvDistance.text = item.distance
            setSkillFlowLayout(layoutSkillFlow, item.skillLabelList)
            showCurInterviewTip(binding, item, position)
        }
    }

    private fun showCurInterviewTip(
        binding: ItemAiRecommendJobBinding,
        item: AiJobState,
        position: Int
    ) {
        with(binding) {
            if (position == 0) {
                inCurInterview.root.setVisible()
                tvSalary.minWidth = 85.dp
            } else {
                inCurInterview.root.setGone()
                tvSalary.minWidth = 0.dp
            }

            inCurInterview.tvContent.run {
                post {
                    val cardWidth = clContent.width

                    val titleMargin = 24.dp
                    val titleWidth = layoutTab.width
                    val jobNameWidth = tvJobName.width
                    val tagWidth = 48.dp
                    val tagMargin = 4.dp

                    // 卡片宽度 - 标题内容宽度
                    val resetCardWidth = cardWidth - titleMargin - jobNameWidth - tagWidth - tagMargin
                    // 标题宽度 - 标题内容宽度
                    val resetTitleWidth = titleWidth - jobNameWidth - tagWidth - tagMargin

                    val content = if (resetCardWidth <= inCurInterview.root.width) {
                        "100%回复，\n当天约面试"
                    } else {
                        "100%回复，当天约面试"
                    }
                    inCurInterview.tvContent.text = content
                    inCurInterview.tvContent.run {
                        post {
                            if (resetTitleWidth >= 0) {
                                inCurInterview.root.updateLayoutParams<MarginLayoutParams> {
                                    marginStart = -resetTitleWidth
                                }
                            } else {
                                inCurInterview.root.updateLayoutParams<MarginLayoutParams> {
                                    marginStart = 0
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showCurInterviewTip(anchor: View) {
        val binding = LayoutCurInterviewPopBinding.inflate(
            LayoutInflater.from(curContext), null, false
        )
        val popupWindow = PopupWindow(
            binding.root,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        ).apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            isTouchable = true
            isOutsideTouchable = true
        }
        popupWindow.showAsDropDown(anchor)
    }

    /**
     * 设置技能标签
     */
    private fun setSkillFlowLayout(flowLayout: NoActionTagLy, list: List<String>) {
        if (list.isNotEmpty()) {
            flowLayout.setVisible()
            flowLayout.setAdapter(object : TagAdapter<String>(list) {
                override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                    val textView = LayoutInflater.from(curContext)
                        .inflate(R.layout.ai_recommend_skill_tag, null, false) as TextView
                    textView.run {
                        text = t
                        background = Bovb.with().radius(4.dp.toFloat()).color(getColor(R.color.C_S2)).build()
                    }
                    return textView
                }
            }, 0, 0, 6, 0)
        } else {
            flowLayout.setGone()
        }
    }

    /**
     * 设置职位详情
     */
    private fun setJobDetail(
        binding: ItemAiRecommendJobBinding,
        item: AiJobState,
    ) {
        with(binding.inJobDetail) {
            // 加载Lottie动画

            // 设置标题
            ivTitle.load(item.jobInterpretTitle) {
                placeholder(R.drawable.ai_recommend_greet)
                error(R.drawable.ai_recommend_greet)
            }
            // 职位解读内容
            rvList.adapter = listAdapter
            rvList.itemAnimator = null
            rvList.setHasFixedSize(true)
            listAdapter.setList(item.jobInterpretList)
        }
    }

    /**
     * 设置Hr信息
     */
    private fun setHrInfo(
        binding: ItemAiRecommendJobBinding,
        item: AiJobState,
    ) {
        with(binding.inHrInfo) {
            ivAvatar.load(item.hrAvatar) {
                placeholder(R.drawable.c_common_icon_hr_new_default)
                error(R.drawable.c_common_icon_hr_new_default)
            }
            ivOnline.visibility = if (item.hrOnline) View.VISIBLE else View.GONE
            tvName.text= item.hrName
            tvHrJob.text = " · ${item.hrJob}"
            tvHrActive.run {
                text = item.hrActive
                background = Bovb.with()
                    .radius(4.dp.toFloat())
                    .borderWidth(1.dp)
                    .borderColor(getColor(R.color.green_border))
                    .build()
            }
            // 点击打招呼
            llGreet.onClick {
                callback?.onGreetClick()
            }
//            llName.updateLayoutParams<FrameLayout.LayoutParams> {
//                gravity = Gravity.CENTER_VERTICAL
//            }
        }
    }

    /**
     * 职位解读-标签
     */
    inner class JobInterpretLabelDelegate : BindingViewDelegate<JobInterpretState, LayoutAiRecommendJobDetailLabelItemBinding>() {

        override fun onBindViewHolder(
            binding: LayoutAiRecommendJobDetailLabelItemBinding,
            item: JobInterpretState,
            position: Int
        ) {
            binding.tvTitle.text = item.title
            setJobDescFlowLayout(binding.layoutJobDescFlow, item.labelList)
        }

        /**
         * 设置职位概括标签
         */
        private fun setJobDescFlowLayout(flowLayout: NoActionTagLy, list: List<String>) {
            if (list.isNotEmpty()) {
                flowLayout.setVisible()
                flowLayout.setAdapter(object : TagAdapter<String>(list) {
                    override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                        val textView = LayoutInflater.from(curContext)
                            .inflate(R.layout.ai_recommend_job_desc_tag, null, false) as TextView
                        textView.text = t
                        return textView
                    }
                }, 0, 0, 6, 10)
            } else {
                flowLayout.setGone()
            }
        }
    }

    /**
     * 职位解读-纯文本
     */
    inner class JobInterpretTextDelegate : BindingViewDelegate<JobInterpretState, LayoutAiRecommendJobDetailTextItemBinding>() {

        override fun onBindViewHolder(
            binding: LayoutAiRecommendJobDetailTextItemBinding,
            item: JobInterpretState,
            position: Int
        ) {
            binding.tvTitle.text = item.title
            binding.tvContent.text = item.textContent
        }

    }

    /**
     * 职位解读-高亮
     */
    inner class JobInterpretSpanDelegate : BindingViewDelegate<JobInterpretState, LayoutAiRecommendJobDetailSpanItemBinding>() {

        override fun onBindViewHolder(
            binding: LayoutAiRecommendJobDetailSpanItemBinding,
            item: JobInterpretState,
            position: Int
        ) {
            binding.tvTitle.text = item.title
            binding.layoutAnalysisFlow.setAdapter(object : TagAdapter<JobInterpretState.SpanContent>(item.spanList) {
                override fun getView(
                    parent: FlowLayout?,
                    position: Int,
                    content: JobInterpretState.SpanContent?
                ): View {
                    val textView = LayoutInflater.from(curContext)
                        .inflate(R.layout.ai_recommend_analysis_tag, null, false) as TextView
                    textView.run {
                        text = content?.text?.colorSpan(content.highLight, "#426EFF")?.boldSpan(content.highLight)
                    }
                    return textView
                }
            }, 0, 0, 16, 8)
        }
    }

}

fun main() {
    val time = 80 / 1000f
    println(time)
}