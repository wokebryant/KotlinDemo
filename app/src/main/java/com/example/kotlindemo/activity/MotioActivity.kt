package com.example.kotlindemo.activity

import android.os.Bundle
import android.view.View
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityMotionBinding
import com.example.kotlindemo.study.motion.*
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.binding

/**
 *  运动布局
 */
class MotionActivity : BaseActivity(),View.OnClickListener {

    private val binding: ActivityMotionBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            with(binding) {
                singleTranslationBtn.setOnClickListener(this@MotionActivity)
                multiTranslationBtn.setOnClickListener(this@MotionActivity)
                keyPositionBtn.setOnClickListener(this@MotionActivity)
                keyAttributeBtn.setOnClickListener(this@MotionActivity)
                keyCycleBtn.setOnClickListener(this@MotionActivity)
                customBtn.setOnClickListener(this@MotionActivity)
                withAppBarBtn.setOnClickListener(this@MotionActivity)
            }
        }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.singleTranslationBtn.id ->
                AppUtil.startActivity<MotionSingleTranslationActivity>(this){}

            binding.multiTranslationBtn.id ->
                AppUtil.startActivity<MotionMultiTranslationActivity>(this){}

            binding.keyPositionBtn.id ->
                AppUtil.startActivity<MotionKeyPositionActivity>(this){}

            binding.keyAttributeBtn.id ->
                AppUtil.startActivity<MotionKeyAttributeActivity>(this){}

            binding.keyCycleBtn.id ->
                AppUtil.startActivity<MotionKeyCycleActivity>(this){}

            binding.customBtn.id ->
                AppUtil.startActivity<MotionCustomActivity>(this){}

            binding.withAppBarBtn.id ->
                AppUtil.startActivity<MotionWithAppBarActivity>(this){}

        }
    }
}

