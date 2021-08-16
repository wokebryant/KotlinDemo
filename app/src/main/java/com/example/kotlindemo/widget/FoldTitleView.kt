package com.example.kotlindemo.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.FoldTitleViewBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.dip2px
import kotlinx.android.synthetic.main.activty_motion_title.view.*

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

    enum class TitleType {
        /**
         *  普通任务类型， 单次提交
         */
        NORMAL,

        /**
         *  组题，多次提交
         */
        MULTI_TASK,

        /**
         * 确定数量子任务，单次提交
         */
        DETERMINE_CHILD,

        /**
         *  不确定数量子任务，单次提交
         */
        UNCERTAIN_CHILD,
    }

    private val binding: FoldTitleViewBinding by binding()

    lateinit var tvTaskDes: FoldTextView

    private var fixedTitleContent = resources.getText(R.string.title_content)
    private var expandTitleContent = resources.getText(R.string.title_content)

    private var isShowTaskProgress = false
    private var unMarkedNum = 0

    private var titleStateListener: TitleStateListener? = null

    init {
        initView()
        setData()
    }

    private fun initView() {
        with(binding) {
            titleLayout.setOnClickListener(this@FoldTitleView)
            titleFixedCloseIv.setOnClickListener(this@FoldTitleView)
            titleFixedSkipTv.setOnClickListener(this@FoldTitleView)
            titleFixedSubmitTv.setOnClickListener(this@FoldTitleView)
            spaceView.setOnClickListener(this@FoldTitleView)

            tvTaskDes = binding.titleFixedContentTv
        }
    }

    fun setData() {
        binding.titleFixedContentTv.apply {
            isShowTip = true
            isShowTag = true
            isShowLightText = true
            mFoldText = "6"
//            text = fixedTitleContent
        }

        binding.titleExpandContentTv.apply {
            isShowImage = true
            isShowTag = true
            isShowLightText = true
//            text = expandTitleContent
        }
    }

    /**
     *  根据任务类型设置title样式
     */
    fun setStyleWithType(type: TitleType) {
        when (type) {
            TitleType.NORMAL -> {
                setFixedTitleHeight(false)
            }

            TitleType.MULTI_TASK -> {
                setFixedTitleHeight(false)

            }

            TitleType.DETERMINE_CHILD -> {
                setFixedTitleHeight(true)
            }

            TitleType.UNCERTAIN_CHILD -> {
                setFixedTitleHeight(true)
            }
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
                    if (titleContainer.progress == 0f) {
                        expandTitle()
                    }
                }

                spaceView.id -> {
                    if (titleContainer.progress == 1f) {
                        collapseTitle()
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
        binding.titleExpandContentTv.setOnTouchListener(onQuestionIconClickListener)
    }

    /**
     *  MotionLayout动画监听
     */
    private val transListener = object : MotionLayout.TransitionListener{

        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

        override fun onTransitionChange(
            p0: MotionLayout?,
            p1: Int,
            p2: Int,
            p3: Float
        ) {
            binding.titleContainer.post {
                binding.titleFixedContentTv.alpha = 1.0f - p3
                binding.titleExpandContentTv.alpha = p3
            }
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            if (binding.titleContainer.progress == 1.0f) {
                binding.titleExpandContentTv.startTextFlashTask()
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
     *  问号Icon点击
     */
    private val onQuestionIconClickListener = OnTouchListener { v, event ->
        val widget = v as TextView
        val text = widget.text
        val spannableText = Spannable.Factory.getInstance().newSpannable(text)

        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_DOWN) {
            var x = event.x
            var y = event.y

            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop

            x += widget.scrollX
            y += widget.scrollY

            val layout = widget.layout
            val line = layout.getLineForVertical(y.toInt())
            val off = layout.getOffsetForHorizontal(line, x)

            val imageSpans = spannableText.getSpans(off, off, CenterImageSpan::class.java)
            if (imageSpans.isNotEmpty()) {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    Toast.makeText(context, "问号点击", Toast.LENGTH_SHORT).show()
                }
                true
            }
        }
        false
    }

    /**
     * 折叠标题栏
     */
    fun collapseTitle() {
        binding.titleContainer.transitionToStart()
    }

    /**
     *  展开标题栏
     */
    fun expandTitle() {
        binding.titleContainer.transitionToEnd()
    }

    /**
     *  显示任务进度条
     */
    fun showTaskProgress() {

    }

    fun setTitleStateListener(titleStateListener: TitleStateListener) {
        this.titleStateListener = titleStateListener
    }

    interface TitleStateListener {
        fun onTitleExpand()

        fun onTitleCollapse()
    }

}