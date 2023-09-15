package com.example.kotlindemo.task.microenterprises.delegate

import com.example.kotlindemo.databinding.BMainMircoReusmeListOnlineJobBinding
import com.example.kotlindemo.task.microenterprises.bean.MicroResumeOnlineJobBean
import com.zhaopin.list.multitype.binder.BindingViewDelegate

/**
 * @Description 小微企业简历列表上线职位卡片
 * @Author LuoJia
 * @Date 2023/8/29
 */
class MicroOnlineJobDelegate : BindingViewDelegate<MicroResumeOnlineJobBean, BMainMircoReusmeListOnlineJobBinding>() {

    override fun onBindViewHolder(
        binding: BMainMircoReusmeListOnlineJobBinding,
        item: MicroResumeOnlineJobBean,
        position: Int
    ) {
        TODO("Not yet implemented")
    }
}