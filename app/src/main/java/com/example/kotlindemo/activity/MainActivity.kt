package com.example.kotlindemo.activity

import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.kotlindemo.activity.flow.FlowActivity
import com.example.kotlindemo.databinding.ActivityMainBinding
import com.example.kotlindemo.jetpack.paging3.PagingActivity
import com.example.kotlindemo.study.kotlinshare.KotlinShare
import com.example.kotlindemo.task.appbar.AppBarActivity
import com.example.kotlindemo.task.mutildelivery.DeliveryActivity
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.StatusBarUtil
import com.example.kotlindemo.utils.binding
import com.example.wallet.WalletActivity
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

class MainActivity : BaseActivity(), View.OnClickListener {

    companion object {
        private const val SHARE_NAME_MATERIAL = "MATERIAL"
        private const val SHARE_NAME_MOTION = "MOTION"
        private const val SHARE_NAME_CONSTRAINT = "CONSTRAINT"
        private const val SHARE_NAME_PAGING = "PAGING"
        private const val SHARE_NAME_VIEW_PAGER = "VIEW_PAGER"
    }

    private val binding: ActivityMainBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        doContainerTransform()
        super.onCreate(savedInstanceState)
        initView()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)

        val kotlinShare = KotlinShare()
    }

    private fun doContainerTransform() {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementsUseOverlay = false
    }

    private fun initView() {
        with(binding) {
            testMaterialBtn.setOnClickListener(this@MainActivity)
            testMotionLayoutBtn.setOnClickListener(this@MainActivity)
            testConstraintLayoutBtn.setOnClickListener(this@MainActivity)
            testPagingBtn.setOnClickListener(this@MainActivity)
            testViewPager2Btn.setOnClickListener(this@MainActivity)
            testMarkBtn.setOnClickListener(this@MainActivity)
            testWalletBtn.setOnClickListener(this@MainActivity)
            testJobRankBtn.setOnClickListener(this@MainActivity)
            testAppBarBtn.setOnClickListener(this@MainActivity)
            testFlowBtn.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            binding.testMaterialBtn.id -> {
                AppUtil.startActivity<MaterialDesignActivity>(this, v, SHARE_NAME_MATERIAL) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_MATERIAL)
                }
            }

            binding.testMotionLayoutBtn.id -> {
                AppUtil.startActivity<MotionActivity>(this) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_MOTION)
                }
            }

            binding.testConstraintLayoutBtn.id -> {
                AppUtil.startActivity<ConstraintActivity>(this, v, SHARE_NAME_CONSTRAINT) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_CONSTRAINT)
                }
            }

            binding.testPagingBtn.id -> {
                AppUtil.startActivity<PagingActivity>(this, v, SHARE_NAME_PAGING) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_PAGING)
                }
            }

            binding.testViewPager2Btn.id -> {
                AppUtil.startActivity<ViewPager2Activity>(this, v, SHARE_NAME_VIEW_PAGER) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_VIEW_PAGER)
                }
            }

            binding.testMarkBtn.id -> {
                AppUtil.startActivity<MarkActivity>(this) {

                }
            }

            binding.testWalletBtn.id -> {
                AppUtil.startActivity<WalletActivity>(this){}
            }

            binding.testJobRankBtn.id -> {
                AppUtil.startActivity<DeliveryActivity>(this) {}
            }

            binding.testAppBarBtn.id -> {
                AppUtil.startActivity<AppBarActivity>(this) {}
            }

            binding.testFlowBtn.id -> {
                AppUtil.startActivity<FlowActivity>(this) {}
            }
        }
    }
}