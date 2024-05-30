package com.example.kotlindemo.task.login

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.view.updateLayoutParams
import androidx.core.widget.doAfterTextChanged
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityLoginCheckBindPhoneActivityBinding
import com.example.kotlindemo.study.mvi.core.collectState
import com.example.kotlindemo.utils.binding
import com.zhaopin.social.appcommon.c.OsUtils
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 登录检查绑定手机号
 * @Author LuoJia
 * @Date 2024/05/29
 */
class LoginCheckBindPhoneActivity : BaseActivity() {

    private val binding: ActivityLoginCheckBindPhoneActivityBinding by binding()

    private val viewModel: LoginCheckBindPhoneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        collect()
    }

    private fun initView() {
        with(binding) {
            flTopBar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = OsUtils.getStatusBarHeight()
            }
            binding.ivBack.onClick { finish() }

            flNext.isEnabled = false
            tvInputNumber.doAfterTextChanged {
                flNext.isEnabled = tvInputNumber.text.length > 10
            }

            flNext.onClick {
                viewModel.checkPhoneNumber()
            }
        }
    }

    private fun collect() {
        viewModel.stateFlow.collectState(this) {

        }
    }

}