package com.example.kotlindemo.task.jobdetail

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.FragmentJobDetailBinding
import com.example.kotlindemo.utils.binding
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.log.LogKitty

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/11/28
 */
class JobDetailActivity : BaseActivity() {

    private val binding: FragmentJobDetailBinding by binding()

    /** 列表适配器 */
    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            registerJobDetailList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        with(binding) {
            rvList.run {
                adapter = listAdapter
                itemAnimator = null
                setItemViewCacheSize(10)
            }
            listAdapter.setList(JobDetailMockData.getMockList())
            rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val scrollY = rvList.computeVerticalScrollOffset()
                    val maxY = 200.dp
                    if (scrollY > maxY) {
                        binding.layoutTopBar.showTitle("研究院所长")
                    } else {
                        binding.layoutTopBar.hideTitle()
                    }
                    val alpha = scrollY.toFloat() / maxY
                    val realAlpha = if (alpha > 1.0f) 1.0f else alpha
                    binding.ivTopBar.alpha = realAlpha
                    LogKitty.i("AppBarAlpha: ", alpha)
                }
            })
        }
    }

}