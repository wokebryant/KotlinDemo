package com.example.kotlindemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FoldTitleViewBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.dip2px

/**
 *  可折叠标题栏
 */
@SuppressLint("ClickableViewAccessibility")
class FoldTitleView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), View.OnClickListener {

    companion object {
        private const val TAG = "FoldTitleView"
        private val FixedTitleHeight = dip2px(44f)
        private val FixedTitleWithProgressHeight = dip2px(48f)
    }

    private val binding: FoldTitleViewBinding by binding()

    lateinit var titleMotionLayout: MotionLayout
    lateinit var tvTaskDes: TextView

    var titleExpand: ((Boolean) -> Unit?)? = null

    init {
        initView()
    }

    private fun initView() {
        with(binding) {
            titleLayout.setOnClickListener(this@FoldTitleView)
            titleFixedCloseIv.setOnClickListener(this@FoldTitleView)
            titleFixedSkipTv.setOnClickListener(this@FoldTitleView)
            titleFixedSubmitTv.setOnClickListener(this@FoldTitleView)

            tvTaskDes = binding.titleFixedContentTv
            titleMotionLayout = binding.titleContainer
        }
    }

    /**
     * 设置固定标题栏高度
     */
    private fun setFixedTitleHeight(showProgress: Boolean) {
        binding.titleContainer.getConstraintSet(R.id.start)?.let { set ->
            set.getConstraint(R.id.title_layout)?.let {
                it.layout.mHeight =
                    if (showProgress)
                        FixedTitleWithProgressHeight
                    else
                        FixedTitleHeight
            }
        }
    }

    override fun onClick(v: View?) {
        with(binding) {
            when (v?.id) {

                titleLayout.id -> {
                    if (titleContainer.progress == 1.0f) {
                        collapseTitle()
                    } else if (titleContainer.progress == 0.0f) {
                        expandTitle()
                    }
                }

                titleFixedSkipTv.id -> {
                    titleProgressBar.setProgress(currentProgress = 40, segmentProgress = 70)
                }

                titleFixedSubmitTv.id -> {
                    titleProgressBar.setProgress(currentProgress = 20, segmentProgress = 50, progressSize = 10)
                }

                titleFixedCloseIv.id -> {
                    collapseTitle()
                }
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding.titleContainer.setTransitionListener(transListener)
    }

    /**
     *  MotionLayout动画监听
     */
    private val transListener = object : MotionLayout.TransitionListener{

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {

        }


        override fun onTransitionChange(
            p0: MotionLayout?,
            p1: Int,
            p2: Int,
            p3: Float
        ) {}

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            if (binding.titleContainer.progress == 1.0f) {
                titleExpand?.invoke(true)
            } else if (binding.titleContainer.progress == 0.0f) {
                titleExpand?.invoke(false)
            }
        }

        override fun onTransitionTrigger(
            p0: MotionLayout?,
            p1: Int,
            p2: Boolean,
            p3: Float
        ) {}
    }

    /**
     * 折叠标题栏
     */
    private fun collapseTitle() {
        binding.titleContainer.transitionToStart()
    }

    /**
     *  展开标题栏
     */
    private fun expandTitle() {
        binding.titleContainer.transitionToEnd()
    }

}