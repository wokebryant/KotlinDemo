package com.example.kotlindemo.task.appbar2

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutCampusAppBar2Binding
import com.zhaopin.common.widget.indicator.enums.IndicatorSlideMode
import com.zhaopin.common.widget.indicator.enums.IndicatorStyle
import com.zhaopin.social.appbase.util.curLifecycle
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.BindingLifecycleOwner
import com.zhaopin.social.module_common_util.ext.binding
import com.zhaopin.social.module_common_util.ext.dp
import com.zhpan.bannerview.BannerViewPager
import java.util.Timer
import java.util.TimerTask

/**
 * @Description 校园首页AppBar（第二版）
 * @Author LuoJia
 * @Date 2024/06/26
 */
class CampusAppBar2 @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayout(context, attr, defStyleAttr) {

    /** 自动轮播 */
    private var curPos = 0
    private var timer = Timer()
    private var createLoopTask = object : TimerTask() {
        override fun run() {
            val viewPager = binding.inFamousEnterprise.vpCard
            viewPager.currentItem = viewPager.currentItem + 1
        }
    }

    private val binding: LayoutCampusAppBar2Binding by binding()

    fun setViewPager() {
        // TODO 测试数据
        val testData = testFamousEnterpriseData
        // 初始化ViewPager2
        val viewPagerAdapter = FamousEnterpriseAdapter(testData)
        binding.inFamousEnterprise.vpCard.run {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == ViewPager2.SCROLL_STATE_IDLE && curPos == viewPagerAdapter.itemCount - 1) {
                        setCurrentItem(0 ,false)
                    }
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    curPos = position
                }
            })
        }
        // 设置指示器
        binding.inFamousEnterprise.viewIndicator.visibility =
            if (viewPagerAdapter.itemCount > 1) View.VISIBLE else View.GONE
        binding.inFamousEnterprise.viewIndicator.apply {
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setSliderGap(4.dp.toFloat())
            setSlideMode(IndicatorSlideMode.SCALE)
            setSliderHeight(6.dp.toFloat())
            setSliderColor(getColor(R.color.C_P4), getColor(R.color.C_P1))
            setSliderWidth(6.dp.toFloat(), 15.dp.toFloat())
            setupWithViewPager(binding.inFamousEnterprise.vpCard)
            setPageSize(testData.list.size - 1)
        }
        // 开始轮播
        timer.schedule(createLoopTask, 3000, 3000)
    }


}