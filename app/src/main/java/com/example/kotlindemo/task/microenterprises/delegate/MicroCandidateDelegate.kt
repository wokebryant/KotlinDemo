package com.example.kotlindemo.task.microenterprises.delegate

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.example.kotlindemo.databinding.BMainMircoResumeListCandidateBinding
import com.example.kotlindemo.task.microenterprises.bean.MicroCandidateBean
import com.example.kotlindemo.utils.dp
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 小微企业候选人卡片
 * @Author LuoJia
 * @Date 2023/8/29
 */
class MicroCandidateDelegate : BindingViewDelegate<MicroCandidateBean, BMainMircoResumeListCandidateBinding>() {

    override fun onBindViewHolder(
        binding: BMainMircoResumeListCandidateBinding,
        item: MicroCandidateBean,
        position: Int
    ) {
        with(binding) {
            tvName.text = item.name
            tvActiveTag.background = tvActiveTagBg
            tvDistanceTag.background = tvDistanceTagBg
            tvTip.background = tvTipBg
        }
    }

    private val tvActiveTagBg = Bovb.with()
        .radius(4.dp.toFloat())
        .borderColor(Color.parseColor("#4D426EFF"))
        .borderWidth(1.dp)
        .color(Color.parseColor("#0D426EFF"))
        .build()

    private val tvDistanceTagBg = Bovb.with()
        .radius(4.dp.toFloat())
        .borderColor(Color.parseColor("#4D5C6A99"))
        .borderWidth(1.dp)
        .color(Color.parseColor("#0D5C6A99"))
        .build()

    private val tvTipBg = Bovb.with()
        .topLeftRadius(2.dp.toFloat())
        .topRightRadius(20.dp.toFloat())
        .bottomLeftRadius(12.dp.toFloat())
        .bottomRightRadius(20.dp.toFloat())
        .color(Color.parseColor("#EBF8FF"))
        .build()
}