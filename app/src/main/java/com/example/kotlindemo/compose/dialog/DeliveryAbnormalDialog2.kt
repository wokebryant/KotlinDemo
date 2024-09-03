package com.example.kotlindemo.compose.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutDeliveryAbnormalDialogBinding
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.onClick
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/8/27
 */
class DeliveryAbnormalDialog2(
    context: Context,
    private val state: DeliveryAbnormalState
) : Dialog(context) {

    val binding: LayoutDeliveryAbnormalDialogBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_delivery_abnormal_dialog)
        window?.let {
            val params = it.attributes
            params.width = ViewGroup.LayoutParams.MATCH_PARENT
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
            params.gravity = Gravity.CENTER
            it.attributes = params
            it.setDimAmount(0.4f)
            it.setBackgroundDrawable(ColorDrawable(0))
            setCanceledOnTouchOutside(false)
        }

        binding.tvTitle.text = state.title
        binding.tvContent.text = state.content

        if (state.isCallService) {
            binding.ivCall.setVisible()
            binding.tvBtn.text = "联系客服"
        } else {
            binding.ivCall.setGone()
            binding.tvBtn.text = "知道了"
        }

        if (state.showCountDown) {
            binding.llTimer.setVisible()
            showTimer(state.unLockTime)
        } else {
            binding.llTimer.setGone()
        }

        // 点击事件
        binding.llBtn.onClick {
            if (!state.isCallService) {
                dismiss()
            }
        }
        binding.ivClose.onClick {
            dismiss()
        }
    }

    /**
     * 展示倒计时
     */
    private fun showTimer(millis: Long) {
        var countdownMills = millis

        setTimerUi(countdownMills)

        MainScope().launch {
            for (i in millis / 1000 downTo 1) {
                countdownMills = (i - 1) * 1000
                delay(1000)

                setTimerUi(countdownMills)
                if (i.toInt() == 1) {
                    dismiss()
                }
            }
        }
    }

    private fun setTimerUi(countdownMills: Long) {
        // 获取倒计时时间
        val hours = (countdownMills / (1000 * 60 * 60)).toString().padStart(2, '0')
        val minutes = ((countdownMills % (1000 * 60 * 60)) / (1000 * 60)).toString().padStart(2, '0')
        val seconds = ((countdownMills % (1000 * 60)) / 1000).toString().padStart(2, '0')

        binding.inHour.tvTime.text = hours
        binding.inMinute.tvTime.text = minutes
        binding.inSecond.tvTime.text = seconds
    }

}