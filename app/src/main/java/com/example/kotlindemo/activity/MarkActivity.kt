package com.example.kotlindemo.activity

import android.os.Bundle
import android.view.View
import com.example.kotlindemo.databinding.ActivityMarkBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.widget.CarDivideMarkView
import com.example.kotlindemo.widget.WheelPointMarkView

class MarkActivity : BaseActivity() {

    private val binding: ActivityMarkBinding by binding()

    enum class MarkType {
        CAR_DIVIDE,
        WHEEL_POINT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            selectWheelMarkBtn.setOnClickListener {
                addMarkView(MarkType.WHEEL_POINT)
            }

            selectCarDivideBtn.setOnClickListener {
                addMarkView(MarkType.CAR_DIVIDE)
            }
        }
    }

    private fun addMarkView(type: MarkType) {
        val markView: View = when (type) {
            MarkType.WHEEL_POINT -> {
                WheelPointMarkView(this@MarkActivity)
            }

            MarkType.CAR_DIVIDE -> {
                CarDivideMarkView(this@MarkActivity)
            }
        }

        binding.markContainerView.removeAllViews()
        binding.markContainerView.addView(markView)
    }

}