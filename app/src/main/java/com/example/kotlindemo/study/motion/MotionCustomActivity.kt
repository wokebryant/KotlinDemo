package com.example.kotlindemo.study.motion

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintAttribute
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityMotionCustomBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.dip2px

/**
 *  MotionLayout示例：动态属性
*/

class MotionCustomActivity : BaseActivity() {

    companion object {
        private val FixedTitleHeight = dip2px(44f)
        private val FixedTitleWithProgressHeight = dip2px(48f)
    }

    private val binding: ActivityMotionCustomBinding by binding()

    private lateinit var titleContainer: MotionLayout
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding){
            titleContainer = binding.titleView.titleMotionLayout
            titleTextView = binding.titleView.tvTaskDes
            titleView.titleExpand = {   isTitleExpoand ->
                changeTitleContent(isTitleExpoand)
            }
        }
    }

    /**
     *  动态修改文本内容，字体大小
     */
    private fun changeTitleContent(isTitleExpand: Boolean) {
        var contentSize = 0f
        var contentString = ""
        var constraintSetId = 0
        var contentColor = ""
        if (isTitleExpand) {
            constraintSetId = R.id.end
            contentSize = dip2px(14f).toFloat()
            contentString = "哈哈哈哈哈哈哈哈哈哈哈哈哈"
            contentColor = "#d42f65"

        } else {
            constraintSetId = R.id.start
            contentSize = dip2px(9f).toFloat()
            contentString = "等到黑夜翻面之后会是新的白昼"
            contentColor = "#1c75ff"
        }

        titleContainer.getConstraintSet(constraintSetId)?.let { constraintSet ->
            constraintSet.getConstraint(R.id.title_fixed_content_tv)?.let {
                val attrSize = ConstraintAttribute(ConstraintAttribute("", ConstraintAttribute.AttributeType.FLOAT_TYPE), contentSize)
                val attrString = ConstraintAttribute(ConstraintAttribute("", ConstraintAttribute.AttributeType.STRING_TYPE), contentString)
                val attrColor = ConstraintAttribute(ConstraintAttribute("", ConstraintAttribute.AttributeType.COLOR_TYPE), Color.parseColor(contentColor))

//                    it.mCustomConstraints["TextSize"] = attrSize
                it.mCustomConstraints["Text"] = attrString
                it.mCustomConstraints["TextColor"] = attrColor
                ConstraintAttribute.setAttributes(titleTextView, it.mCustomConstraints)
            }
            // Android MotionLayout动态设置结束状态的TopMargin
        }


    }

    /**
     *  动态修改title高度
     */
    private fun changeTitleHeight(showProgress: Boolean) {
        titleContainer.getConstraintSet(R.id.end)?.let { set ->
            set.getConstraint(R.id.title_layout)?.let {
                it.layout.mHeight =
                    if (showProgress)
                        FixedTitleWithProgressHeight
                    else
                        FixedTitleHeight
            }
        }
    }

}