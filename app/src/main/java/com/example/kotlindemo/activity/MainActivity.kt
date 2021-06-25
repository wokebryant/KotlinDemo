package com.example.kotlindemo.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.R
import com.example.kotlindemo.jetpack.paging3.PagingActivity
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
    }

    private fun initView() {
        testMaterialBtn.setOnClickListener(this)
        testMotionLayoutBtn.setOnClickListener(this)
        testConstraintLayoutBtn.setOnClickListener(this)
        testPagingBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            testMaterialBtn.id -> {
                AppUtil.startActivity<MaterialDesignActivity>(this) {}
            }

            testMotionLayoutBtn.id -> {
                AppUtil.startActivity<MotionActivity>(this) {}
            }

            testConstraintLayoutBtn.id -> {
                AppUtil.startActivity<ConstraintActivity>(this) {}
            }

            testPagingBtn.id -> {
                AppUtil.startActivity<PagingActivity>(this) {}
            }
        }
    }
}