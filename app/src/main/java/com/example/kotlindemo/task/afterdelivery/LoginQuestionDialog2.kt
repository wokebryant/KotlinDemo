package com.example.kotlindemo.task.afterdelivery

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutLoginQuestionDialogBinding
import com.example.kotlindemo.utils.getColor
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/06/25
 */
class LoginQuestionDialog2(context: Context) : Dialog(context) {

    val binding: LayoutLoginQuestionDialogBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login_question_dialog)
        window?.let {
            val params = it.attributes
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.gravity = Gravity.BOTTOM
            it.attributes = params
            it.setDimAmount(0.4f)
            it.setBackgroundDrawable(ColorDrawable(0))
            it.setWindowAnimations(R.style.ZLDialog_bottom)
        }

        binding.flRoot.run {
            background = Bovb.with()
                .color(getColor(R.color.C_S2))
                .topLeftRadius(10.dp.toFloat())
                .topRightRadius(10.dp.toFloat())
                .build()
        }
        binding.inChangeBind.run {
            ivLogo.setImageResource(R.drawable.ic_login_change_bind)
            tvTitle.text = "手机号无法登录，换绑手机号"
            tvContent.text = "手机号停机或者不用了，通过身份验证后换绑一个新手机号码"
        }
        binding.inForget.run {
            ivLogo.setImageResource(R.drawable.ic_login_lock)
            tvTitle.text = "忘记密码"
            tvContent.text = "通过手机验证码设置新的登录密码"
        }

        binding.tvCallService.onClick {

        }

        binding.ivClose.onClick {
            dismiss()
        }
    }


}