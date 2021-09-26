package com.example.kotlindemo.activity

import android.os.Bundle
import android.view.View
import android.view.Window
import com.example.kotlindemo.R
import com.example.kotlindemo.jetpack.paging3.PagingActivity
import com.example.kotlindemo.study.kotlinshare.KotlinShare
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.StatusBarUtil
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    companion object {
        private const val SHARE_NAME_MATERIAL = "MATERIAL"
        private const val SHARE_NAME_MOTION = "MOTION"
        private const val SHARE_NAME_CONSTRAINT = "CONSTRAINT"
        private const val SHARE_NAME_PAGING = "PAGING"
        private const val SHARE_NAME_VIEW_PAGER = "VIEW_PAGER"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        doContainerTransform()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        testMaterialBtn.setOnClickListener(this)
        testMotionLayoutBtn.setOnClickListener(this)
        testConstraintLayoutBtn.setOnClickListener(this)
        testPagingBtn.setOnClickListener(this)
        testViewPager2Btn.setOnClickListener(this)
        testMarkBtn.setOnClickListener(this)
        testMagnifierBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            testMaterialBtn.id -> {
                AppUtil.startActivity<MaterialDesignActivity>(this, v, SHARE_NAME_MATERIAL) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_MATERIAL)
                }
            }

            testMotionLayoutBtn.id -> {
                AppUtil.startActivity<MotionActivity>(this) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_MOTION)
                }
            }

            testConstraintLayoutBtn.id -> {
                AppUtil.startActivity<ConstraintActivity>(this, v, SHARE_NAME_CONSTRAINT) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_CONSTRAINT)
                }
            }

            testPagingBtn.id -> {
                AppUtil.startActivity<PagingActivity>(this, v, SHARE_NAME_PAGING) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_PAGING)
                }
            }

            testViewPager2Btn.id -> {
                AppUtil.startActivity<ViewPager2Activity>(this, v, SHARE_NAME_VIEW_PAGER) {
                    putExtra(EXTRA_TRANSITION_NAME, SHARE_NAME_VIEW_PAGER)
                }
            }

            testMarkBtn.id -> {
                AppUtil.startActivity<MarkActivity>(this) {

                }
            }

            testMagnifierBtn.id -> {
            }
        }
    }
}