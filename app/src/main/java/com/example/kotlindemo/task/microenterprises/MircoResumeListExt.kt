package com.example.kotlindemo.task.microenterprises

import com.example.kotlindemo.task.microenterprises.delegate.MicroCandidateDelegate
import com.example.kotlindemo.task.microenterprises.delegate.MicroOnlineJobDelegate
import com.example.kotlindemo.task.microenterprises.delegate.MicroToBePaidDelegate
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter

/**
 * @Description 小微企业简历列表扩展方法
 * @Author LuoJia
 * @Date 2023/8/29
 */
internal fun MultiTypeAdapter.registerMicroResumeList() = this.apply {
    // 小微企业去支付卡片
    register(MicroToBePaidDelegate())
    // 小微企业职位上线卡片
    register(MicroOnlineJobDelegate())
    // 小微企业候选人卡片
    register(MicroCandidateDelegate())
}
