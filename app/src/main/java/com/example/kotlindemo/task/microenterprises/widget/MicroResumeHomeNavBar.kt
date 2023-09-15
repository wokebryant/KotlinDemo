package com.example.kotlindemo.task.microenterprises.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.BMainMircoResumeListTopNavBinding
import com.example.kotlindemo.task.microenterprises.view.home.MircoResumeHomeCallback
import com.google.android.material.tabs.TabLayout
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description 小微企业简历列表顶部导航栏
 * @Author LuoJia
 * @Date 2023/8/29
 */
class MicroResumeHomeNavBar @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attr, defStyleAttr) {

    /**
     * TabLayout选中监听
     */
    private val onTabLayoutSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            tab?.customView?.let {
                it.findViewById<TextView>(R.id.tv_tab_item).run {
                    textSize = 20f
                    setTextColor(getColor(R.color.C_B1))
                }
                callback?.onTabClick(tab.position)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
            tab?.customView?.let {
                it.findViewById<TextView>(R.id.tv_tab_item).run {
                    textSize = 16f
                    setTextColor(getColor(R.color.C_B2))
                }
            }
        }

        override fun onTabReselected(tab: TabLayout.Tab?) { }
    }

    private val binding: BMainMircoResumeListTopNavBinding by binding()
    var tabLayout: TabLayout
    var callback: MircoResumeHomeCallback? = null

    init {
        binding.ivAddJob.onClick {
            callback?.onAddJobClick()
        }
        tabLayout = binding.tableLayout.apply {
            addOnTabSelectedListener(onTabLayoutSelectedListener)
        }
    }

}