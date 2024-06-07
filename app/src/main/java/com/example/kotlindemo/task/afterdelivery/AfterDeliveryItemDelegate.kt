package com.example.kotlindemo.task.afterdelivery

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import coil.load
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemAfterDeliveryBinding
import com.zhaopin.common.widget.flowLayout.FlowLayout
import com.zhaopin.common.widget.flowLayout.NoActionTagLy
import com.zhaopin.common.widget.flowLayout.TagAdapter
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/06/06
 */
class AfterDeliveryItemDelegate : BindingViewDelegate<AfterDeliveryCardState, ItemAfterDeliveryBinding>() {

    override fun onBindViewHolder(
        binding: ItemAfterDeliveryBinding,
        item: AfterDeliveryCardState,
        position: Int
    ) {
        setView(binding, item, position)
    }

    private fun setView(
        binding: ItemAfterDeliveryBinding,
        item: AfterDeliveryCardState,
        position: Int
    ) {
        with(binding) {
            ivCheck.isSelected = item.selected
            // 职位 - 工资
            tvJobName.text = item.jobName
            tvSalary.text = item.salary
            // 公司信息
            tvCompanyName.text = item.companyName
            tvCompanyType.text = item.companyType
            tvCompanySize.text = item.companySize
            // 标签
            setSkillFlowLayout(layoutSkillFlow, item.skillList)
            // HR信息
            ivAvatar.load(item.avatar) {
                placeholder(R.drawable.c_common_icon_hr_new_default)
                error(R.drawable.c_common_icon_hr_new_default)
            }
            tvHrName.text = item.hrName
            tvHrJob.text = item.hrJob

            // 选择框点击
            binding.ivCheck.onClick {
                item.onSelectedClick.invoke(position, item.copy(selected = !item.selected))
            }
            // 卡片点击
            binding.root.onClick {
                item.onItemClick.invoke(position, item)
            }
        }
    }

    /**
     * 设置技能标签
     */
    private fun setSkillFlowLayout(flowLayout: NoActionTagLy, list: List<String>) {
        if (list.isNotEmpty()) {
            flowLayout.setVisible()
            flowLayout.setAdapter(object : TagAdapter<String>(list) {
                override fun getView(parent: FlowLayout?, position: Int, t: String): View {
                    val textView = LayoutInflater.from(curContext)
                        .inflate(R.layout.resume_recommend_skill_tag, null, false) as TextView
                    textView.run {
                        text = t
                        background = Bovb.with().radius(6.dp.toFloat()).color(getColor(R.color.C_S2)).build()
                        setPadding(9.dp, 4.dp, 9.dp, 4.dp)
                    }
                    return textView
                }
            }, 0, 0, 6, 0)
        } else {
            flowLayout.setGone()
        }
    }

}