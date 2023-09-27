package com.example.kotlindemo.task.jobtag.card

import android.os.Bundle
import com.example.kotlindemo.activity.BaseActivity
import com.example.kotlindemo.databinding.ActivityRecommendJobBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast

/**
 * @Description 测试首页
 * @Author LuoJia
 * @Date 2023/9/18
 */
class RecommendJobActivity : BaseActivity() {

    private val binding: ActivityRecommendJobBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jobData = intent?.extras?.getSerializable("jobData")
        (jobData as? LocalJobIntentData)?.let {
            intData(it)
        }

    }

    private fun intData(jobData: LocalJobIntentData) {
        val (initItemIndex, initTagIndex, itemList) = jobData
        with(binding) {
            // 设置横向列表数据
            rvJobList.initData(itemList?.get(initItemIndex), initItemIndex, initTagIndex)
            rvJobList.onExpandClick = {
                layoutExpandPanel.expand(itemList)
            }
            rvJobList.refreshCallback = { clickInfo ->
                progressBar.setVisible()
                // 模拟刷新，请求为空
                layoutExpandPanel.postDelayed({
                    progressBar.setGone()
                    layoutEmpty.show(itemList, clickInfo)
                }, 2000)
            }

            // 设置展开面板数据
            layoutExpandPanel.refreshCallback = { clickInfo ->
                // 更新横向列表数据
                rvJobList.updateData(itemList?.get(clickInfo.itemIndex), clickInfo)
                progressBar.setVisible()
                // 模拟刷新，请求为空
                layoutExpandPanel.postDelayed({
                    progressBar.setGone()
                    layoutEmpty.show(itemList, clickInfo)
                }, 2000)
            }

            // 设置空页面回调
            layoutEmpty.refreshCallback = { clickInfo ->
                // 更新横向列表数据
                rvJobList.updateData(itemList?.get(clickInfo.itemIndex), clickInfo)
                progressBar.setVisible()
                // 模拟刷新，请求不为空
                layoutEmpty.postDelayed({
                   progressBar.setGone()
                   layoutEmpty.hide()
                }, 2000)
            }

            inLayout.root.onClick {
                showToast("点击卡片")
            }
        }
    }

}