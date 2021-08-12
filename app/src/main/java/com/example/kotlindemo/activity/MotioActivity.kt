package com.example.kotlindemo.activity

import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.utils.dip2px
import com.example.kotlindemo.widget.FoldTextView
import com.example.kotlindemo.widget.FoldTitleView
import kotlinx.android.synthetic.main.fold_title_view.*

/**
 *  运动布局
 */
class MotionActivity : TransformActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_motion)
        setContentView(R.layout.fold_title_view)

        val titleContainer = findViewById<MotionLayout>(R.id.title_container)
        val titleTextView =findViewById<FoldTextView>(R.id.title_fixed_content_tv)

        titleTextView.apply {
            isTipWithTag = true
            isTipShow = true
            mFoldText = "7"
            text = (resources.getText(R.string.title_test))
        }

        titleContainer.getConstraintSet(R.id.start)?.let {
            it.getConstraint(R.id.title_layout)?.let {
                it.layout.mHeight = dip2px(44f)
            }
        }


        title_fixed_submit_tv.setOnClickListener {
            title_progress_bar.setProgress(currentProgress = 40, segmentProgress = 70)
        }

        title_fixed_skip_tv.setOnClickListener {
            title_progress_bar.setProgress(currentProgress = 20, segmentProgress = 50)
        }
    }


}