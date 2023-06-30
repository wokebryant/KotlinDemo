package com.example.kotlindemo.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.example.kotlindemo.activity.flow.FlowViewModel
import com.example.kotlindemo.databinding.ActivityFlowBinding
import com.example.kotlindemo.databinding.ActivityLinagePageBinding
import com.example.kotlindemo.utils.binding

/**
 * @Description 二级联动页面
 * @Author LuoJia
 * @Date 2023/6/30
 */
class ZLLinkageActivity : BaseActivity() {

    private val binding: ActivityLinagePageBinding by binding()

    private val viewModel by viewModels<LinkageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {

        }
    }



}