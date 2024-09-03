package com.example.kotlindemo.study.span

import android.os.Bundle
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivitySpanBinding
import com.zhaopin.social.module_common_util.ext.binding

/**
 * @Description 富文本
 * @Author LuoJia
 * @Date 2024/2/29
 */
class SpanActivity : BaseActivity() {

    companion object {
        private const val TEST_TEXT_1 = "Compose Multiplatform 项目其实就是在 KMM 项目中加入一些 Compose 的内容，还是有不少模板代码的，我们可以直接使用 JetBrains 提供的项目模板生成项目" +
                "Compose Multiplatform 项目其实就是在 KMM 项目中加入一些 Compose 的内容，还是有不少模板代码的，我们可以直接使用 JetBrains 提供的项目模板生成项目" +
                "Compose Multiplatform 项目其实就是在 KMM 项目中加入一些 Compose 的内容，还是有不少模板代码的，我们可以直接使用 JetBrains 提供的项目模板生成项目" +
                "Compose Multiplatform 项目其实就是在 KMM 项目中加入一些 Compose 的内容，还是有不少模板代码的，我们可以直接使用 JetBrains 提供的项目模板生成项目"
    }

    private val binding: ActivitySpanBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. 测试折叠文本
        binding.tvSpan.text = TEST_TEXT_1
    }
}