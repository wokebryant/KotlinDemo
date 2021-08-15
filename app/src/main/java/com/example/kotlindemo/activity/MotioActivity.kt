package com.example.kotlindemo.activity

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ActivityMotionBinding
import com.example.kotlindemo.databinding.ActivtyMotionTitleBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.dip2px
import com.example.kotlindemo.widget.FoldTextView
import com.example.kotlindemo.widget.FoldTitleView
import kotlinx.android.synthetic.main.fold_title_view.*
import kotlin.concurrent.thread

/**
 *  运动布局
 */
class MotionActivity : TransformActivity() {

    private val binding: ActivtyMotionTitleBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.titleTestClickBtn.setOnClickListener {
            Toast.makeText(this, " click space", Toast.LENGTH_SHORT).show()
        }
    }

//    private fun changeTitle(isStart: Boolean) {
//        var content = 0f
//        if (isStart) {
//            content = dip2px(11f).toFloat()
//        } else {
//            content = dip2px(17f).toFloat()
//        }
//        titleContainer.getConstraintSet(R.id.end)?.let { constraintSet ->
//            constraintSet.getConstraint(R.id.title_fixed_content_tv)?.let {
//                val attr = ConstraintAttribute("", ConstraintAttribute.AttributeType.FLOAT_TYPE, content)
//                it.mCustomConstraints["TextSize"] = attr
//                ConstraintAttribute.setAttributes(titleTextView, it.mCustomConstraints)
//            }
//        }
//    }


}