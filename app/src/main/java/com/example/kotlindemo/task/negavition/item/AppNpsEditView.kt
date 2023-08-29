package com.example.kotlindemo.task.negavition.item

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import com.example.kotlindemo.R

class AppNpsEditView : LinearLayout {
    @JvmField
    var editText: AppNPSEditText? = null
    var textView: TextView? = null
    var maxLength = 0
        private set

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    fun init() {
        val view = inflate(context, R.layout.app_dialog_nps_item_edit, this)
        editText = view.findViewById(R.id.et_think)
        textView = view.findViewById(R.id.tv_num)
    }

    fun setData(placeHolder: String?, maxLength: Int) {
        this.maxLength = maxLength
        textView?.text = "0/$maxLength"
        editText?.hint = placeHolder
        editText?.requestFocus()
    }

    fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (s == null || s.length == 0) {
            textView!!.text = "0/$maxLength"
        } else if (s.length <= maxLength) {
            textView!!.text = s.length.toString() + "/" + maxLength
        } else {
            val buffer = StringBuffer()
            buffer.append("<font color=\"#FF5F57\">")
            buffer.append(s.length)
            buffer.append("</font>")
            buffer.append("<font color=\"#aab0b7\">")
            buffer.append("/$maxLength")
            buffer.append("</font>")
            textView!!.text = Html.fromHtml(buffer.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    fun onFilterSpaceTextChanged(s: CharSequence?, length: Int) {
        if (s.isNullOrEmpty()) {
            textView!!.text = "0/$maxLength"
        } else {
            textView!!.text = "$length/$maxLength"
        }
    }


    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == VISIBLE) {
            editText?.requestFocus()
            editText?.isFocusableInTouchMode = true
            val inputManager =
                editText!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.showSoftInput(editText, 0)
        } else {
            editText?.clearFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(editText?.windowToken, 0)
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        editText?.requestFocus()
        editText?.isFocusableInTouchMode = true
        val inputManager =
            editText!!.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(editText, 0)
    }

    fun getEditNums(): Int {
        return editText?.text.toString().trim { it <= ' ' }.length
    }

    fun getSubContent(): String {
        return editText?.text.toString().trim { it <= ' ' }
    }
}