package com.example.kotlindemo.task.microenterprises.delegate

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.example.kotlindemo.databinding.BMainMircoReusmeListToBePaidBinding
import com.example.kotlindemo.task.microenterprises.bean.MicroResumeListToBePaidBean
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 小微企业简历列表待支付卡片
 * @Author LuoJia
 * @Date 2023/8/29
 */
class MicroToBePaidDelegate : BindingViewDelegate<MicroResumeListToBePaidBean, BMainMircoReusmeListToBePaidBinding>() {

    override fun onBindViewHolder(
        binding: BMainMircoReusmeListToBePaidBinding,
        item: MicroResumeListToBePaidBean,
        position: Int
    ) {
        with(binding) {
            tvContent.text = item.content
            tvPay.text = "去支付"
            tvPay.background = Bovb.with()
                .radius(21.dp.toFloat())
                .gradientColor(intArrayOf(Color.parseColor("#4CC4FF"), Color.parseColor("#426EFF")))
                .gradientType(GradientDrawable.LINEAR_GRADIENT)
                .gradientOrientation(GradientDrawable.Orientation.LEFT_RIGHT)
                .build()
        }
    }
}