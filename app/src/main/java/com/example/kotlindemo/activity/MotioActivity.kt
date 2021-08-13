package com.example.kotlindemo.activity

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.utils.dip2px
import com.example.kotlindemo.widget.FoldTextView
import com.example.kotlindemo.widget.FoldTitleView
import kotlinx.android.synthetic.main.fold_title_view.*

/**
 *  运动布局
 */
class MotionActivity : TransformActivity() {

    lateinit var titleContainer: MotionLayout
    lateinit var titleLayout: LinearLayout
    lateinit var titleTextView: FoldTextView
    lateinit var titleExpandTv: FoldTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_motion)
        setContentView(R.layout.fold_title_view)

        titleContainer = findViewById(R.id.title_container)
        titleLayout = findViewById(R.id.title_layout)
        titleTextView =findViewById(R.id.title_fixed_content_tv)
        titleExpandTv = findViewById(R.id.title_expand_content_tv)

        titleTextView.apply {
            isShowImage = true
            isShowTip = true
            isShowTag = true
            isShowLightText = true
            mFoldText = "157"
            text = (resources.getText(R.string.title_content))
        }

        titleExpandTv.apply {
            isShowImage = true
            isShowTag = true
            isShowLightText = true
            isFlash = true
            text = (resources.getText(R.string.title_content))
        }

        titleLayout.setOnClickListener {
            if (titleContainer.progress == 0f) {
                titleContainer.transitionToEnd()
            } else if (titleContainer.progress == 1f) {
                titleContainer.transitionToStart()
            }
        }

        titleContainer.setTransitionListener(transListener)

        titleContainer.getConstraintSet(R.id.start)?.let { set ->
            set.getConstraint(R.id.title_layout)?.let {
//                it.layout.mHeight = dip2px(44f)
            }
        }


        title_fixed_submit_tv.setOnClickListener {
            title_progress_bar.setProgress(currentProgress = 40, segmentProgress = 70)
        }

        title_fixed_skip_tv.setOnClickListener {
            title_progress_bar.setProgress(currentProgress = 20, segmentProgress = 50)
        }
    }

    private val transListener = object : MotionLayout.TransitionListener{

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            Log.i("Motion_translation ", " Started")
        }

        override fun onTransitionChange(
            p0: MotionLayout?,
            p1: Int,
            p2: Int,
            p3: Float
        ) {
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            Log.i("Motion_translation ", " Completed")
            if (titleContainer.progress == 1.0f) {
                titleExpandTv.apply {
//                    text = (resources.getText(R.string.title_content))
                }
            }
        }

        override fun onTransitionTrigger(
            p0: MotionLayout?,
            p1: Int,
            p2: Boolean,
            p3: Float
        ) {
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