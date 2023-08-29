package com.example.kotlindemo.task.negavition.item

import android.content.Context
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.MainThread
import com.example.kotlindemo.databinding.PositionNegativeFeedbackEditItemBinding
import com.example.kotlindemo.task.negavition.EditLabel
import com.example.kotlindemo.task.negavition.MaxLengthFilter
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setVisible
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast
import java.util.logging.Handler
import kotlin.math.min


/**
 * @Description
 * @Author LuoJia
 * @Date 2023/8/11
 */
class PositionNegativeEditDelegate(
    private val onEditClick: ((Boolean) -> Unit)? = null,
    private val onTextChangeCallback: ((String) -> Unit)? = null
) : BindingViewDelegate<EditLabel, PositionNegativeFeedbackEditItemBinding>() {

    private var editView: AppNpsEditView? = null

    override fun onBindViewHolder(
        binding: PositionNegativeFeedbackEditItemBinding,
        item: EditLabel,
        position: Int
    ) {
        with(binding) {
            tvTitle.text = item.title
            ivEdit.onClick {
                it.setGone()
                showEdit(flEditContent)
            }
            tvReport.onClick {
                report()
            }
        }
    }

    var maxContent = ""

    private val handler = android.os.Handler(Looper.getMainLooper())


    /**
     * 展示编辑框
     */
    private fun showEdit(parent: FrameLayout) {
        onEditClick?.invoke(false)
        editView = AppNpsEditView(currentActivity())
        parent.addView(editView)
        parent.requestLayout()
        editView?.run {
            setVisible()
            setData("请输入", 1000)
            editText?.filters = arrayOf(MaxLengthFilter(1000))
            // 焦点监听
            editText?.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    postDelayed({
                        editText?.callOnClick()
                    }, 200)
                }
            }
            // 文本输入监听
            editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    editView?.onFilterSpaceTextChanged(s?.trim(), min(s?.trim()?.length ?: 0, maxLength))
                    onTextChangeCallback?.invoke(editView?.getSubContent() ?: "")
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
            editText?.setOnClickListener {
                currentActivity()?.showToast("点击输入框")
                onEditClick?.invoke(true)
            }
            editText?.viewTreeObserver?.addOnScrollChangedListener {
                handler.removeCallbacks(mRunnable);
                handler.postDelayed(mRunnable, 100);
            }
        }
    }

    private val mRunnable = Runnable {
        editView?.editText?.let {
            val scrollY: Int = it.getScrollY()
            val paddingTop = Math.max(0, 10.dp - scrollY)
            it.setPadding(
                it.getPaddingLeft(),
                paddingTop,
                it.getPaddingRight(),
                it.getPaddingBottom()
            )
        }
    }

    fun hideSoft() {
        (currentActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(editView?.editText?.windowToken, 0)
    }

    /**
     * 举报
     */
    private fun report() {

    }

}