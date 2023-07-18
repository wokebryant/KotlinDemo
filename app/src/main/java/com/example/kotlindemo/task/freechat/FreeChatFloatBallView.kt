package com.example.kotlindemo.task.freechat

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.animation.addListener
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutFreeChatFloatBallNewBinding
import com.example.kotlindemo.utils.binding
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getDrawable
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/7/15
 */
class FreeChatFloatBallView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : RelativeLayout(context, attr, defStyleAttr) {

    private val binding: LayoutFreeChatFloatBallNewBinding by binding()

    /** 免费聊第一阶段资源 */
    private val expendStartColor1 = Color.parseColor("#E62F3047")
    private val expendEndColor1 = Color.parseColor("#E60E0E13")
    private val progressTextColor1 = Color.parseColor("#713F1C")
    private val ballProgressImage1 = getDrawable(R.drawable.reusme_recommend_chat_rights_progress_yellow)
    private val ballTag1 = getDrawable(R.drawable.reusme_recommend_chat_rights_win_free_chat_yellow)
    /** 免费聊第二阶段资源 */
    private val expendStartColor2 = Color.parseColor("#E647352F")
    private val expendEndColor2 = Color.parseColor("#E6130F0E")
    private val progressTextColor2 = Color.parseColor("#5F3518")
    private val ballProgressImage2 = getDrawable(R.drawable.reusme_recommend_chat_rights_progress_brow)
    private val ballProgressImageLast = getDrawable(R.drawable.reusme_recommend_chat_rights_end)
    private val ballTag2 = getDrawable(R.drawable.reusme_recommend_chat_rights_win_free_chat_brow)

    /** 小球动画 */
    private var curBallState: BallState = BallState.Expend
    private var expendWidth = 0
    private var isStartHide = false

    init {
        initClickListener()
    }

    private fun initClickListener() {
        binding.layoutBall.onClick {
            if (curBallState != BallState.Hide) {
                startTelescopicBallAnim()
            }
        }
        binding.tvFirstRule.onClick {  }
        binding.tvSecondRule.onClick {  }
        binding.ivClose.onClick {  }
    }

    /**
     * 第一阶段打招呼
     */
    fun setFirstLevelChat() {
        updateUiStyle(isFirstLevelChat = true)
        binding.tvProgress.text = "0/3"
    }

    /**
     * 第二阶段打招呼
     */
    @SuppressLint("SetTextI18n")
    fun setSecondLevelChat(curChatCount: Int, totalChatCount: Int) {
        if (curChatCount < totalChatCount) {
            updateUiStyle(isFirstLevelChat = false)
            binding.tvProgress.text = "1/3"
            binding.tvFreeChatContent.text = "已获得${curChatCount}次免费聊～\n" +
                    "再聊${totalChatCount}次，再得${totalChatCount - curChatCount}次免费聊"
        } else {
            updateUiStyle(isFirstLevelChat = false, isLastChat = true)
            binding.tvFreeChatContent.text = "今日共获得${totalChatCount}次免费聊\n" +
                    "记得明日再来哦～"
        }
    }

    private fun updateUiStyle(isFirstLevelChat: Boolean, isLastChat: Boolean = false) {
        // 展开条背景色
        val gradientColor = if (isFirstLevelChat) intArrayOf(expendStartColor1, expendEndColor1)
                            else intArrayOf(expendStartColor2, expendEndColor2)
        // 小球标签
        val ballTagImage = if (isFirstLevelChat) ballTag1 else ballTag2
        // 小球进度
        val ballProgressImage = if (isFirstLevelChat) {
            ballProgressImage1
        } else {
            if (isLastChat) ballProgressImageLast else ballProgressImage2
        }
        // 小球进度文案颜色
        val ballProgressTextColor = if (isFirstLevelChat) progressTextColor1 else progressTextColor2
        with(binding) {
            layoutFirstFreeChat.visibility = if (isFirstLevelChat) View.VISIBLE else View.GONE
            layoutSecondFreeChat.visibility = if (isFirstLevelChat) View.GONE else View.VISIBLE

            layoutExpend.run {
                val backGround = Bovb.with()
                    .topLeftRadius(21f.dp)
                    .bottomLeftRadius(21f.dp)
                    .gradientColor(gradientColor)
                    .gradientType(GradientDrawable.LINEAR_GRADIENT)
                    .gradientOrientation(GradientDrawable.Orientation.LEFT_RIGHT)
                    .build()
                background = backGround
            }

            ivTag.setImageDrawable(ballTagImage)
            ivProgress.setImageDrawable(ballProgressImage)
            tvProgress.run {
                visibility = if (isLastChat) View.GONE else View.VISIBLE
                setTextColor(ballProgressTextColor)
            }
            ivClose.visibility = if (isLastChat) View.VISIBLE else View.GONE
        }
    }

    /**
     * 小球伸缩动画
     */
    @SuppressLint("Recycle")
    private fun startTelescopicBallAnim(animEndCallback: (() -> Unit)? = null) {
        val expendView = binding.layoutExpend
        if (curBallState == BallState.Expend) {
            this.expendWidth = expendView.width
        }
        val startValue = if (curBallState == BallState.Collapse) 0 else expendWidth
        val endValue = if (curBallState == BallState.Collapse) expendWidth else 0
        ObjectAnimator.ofInt(startValue, endValue).run {
            duration = 200
            addUpdateListener {
                expendView.updateLayoutParams<ViewGroup.LayoutParams> {
                    width = it.animatedValue as Int
                }
            }
            addListener(onEnd = {
                curBallState = if (curBallState == BallState.Expend) {
                    BallState.Collapse
                } else {
                    BallState.Expend
                }
                animEndCallback?.invoke()
            })
            start()
        }
    }

    /**
     * 小球隐藏动画
     */
    fun startBallHideAnim() {
        if (isStartHide) {
            return
        }
        if (curBallState == BallState.Hide) {
            return
        }
        isStartHide = true
        if (curBallState == BallState.Expend) {
            startTelescopicBallAnim {
                hideBall()
            }
        } else {
            hideBall()
        }
    }

    @SuppressLint("Recycle")
    private fun hideBall() {
        val ball = binding.layoutBall
        val transAnim = ObjectAnimator.ofFloat(ball, "translationX", 0f, 36f.dp).apply {
            duration = 500
        }
        val alphaAnim = ObjectAnimator.ofFloat(ball, "alpha", 1f, 0.5f).apply {
            duration = 500
            addListener(onEnd = {
                curBallState = BallState.Hide
                isStartHide = false
            })
        }
        AnimatorSet().apply {
            playTogether(transAnim, alphaAnim)
            start()
        }
    }

    /**
     * 小球展示动画
     */
    fun startBallShowAnim() {
        val ball = binding.layoutBall
        val transAnim = ObjectAnimator.ofFloat(ball, "translationX", 36f.dp, 0f).apply {
            duration = 500
        }
        val alphaAnim = ObjectAnimator.ofFloat(ball, "alpha", 0.5f, 1f).apply {
            duration = 500
            addListener(onEnd = {
                curBallState = BallState.Collapse
            })
        }
        AnimatorSet().apply {
            playTogether(transAnim, alphaAnim)
            start()
        }
    }

    sealed class BallState {
        /** 展开 */
        object Expend: BallState()
        /** 收缩 */
        object Collapse: BallState()
        /** 隐藏 */
        object Hide: BallState()
    }

}