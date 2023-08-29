package com.example.kotlindemo.task.negavition

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.updateLayoutParams
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.PositionNegativeFeedbackPanelBinding
import com.example.kotlindemo.task.negavition.item.PositionNegativeEditDelegate
import com.example.kotlindemo.task.negavition.item.PositionNegativeTagDelegate
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.ItemViewDelegate
import com.zhaopin.social.appbase.util.currentActivity
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import com.zhaopin.toast.showToast
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener


/**
 * @Description 职位负反馈弹窗
 * @Author LuoJia
 * @Date 2023/8/10
 */
class PositionNegativeFeedbackPanel : com.example.kotlindemo.widget.BaseBottomSheetDialogFragment<PositionNegativeFeedbackPanelBinding>() {

    companion object {

        @JvmStatic
        fun newInstance(data: NegativeFeedbackBean) = PositionNegativeFeedbackPanel().apply {
            arguments = Bundle().apply {
                putSerializable("data", data)
            }
        }
    }

    private var data: NegativeFeedbackBean? = null
    /** RecyclerView首次加载的高度，用于关闭键盘时还原 */
    private var originRvContentHeight = 0

    /** 提交按钮是否可以点击 */
    private var isSubmitEnable = false
        set(value) {
            field = value
            val backgroundColor = if (value) R.color.C_P1 else R.color.C_P4
            binding.tvSubmit.background = Bovb.with().color(getColor(backgroundColor)).radius(22f.dp).build()
        }

    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(PositionNegativeTagDelegate(onTagClick))
            register(PositionNegativeEditDelegate(onEditClick, onTextChange))
        }
    }

    /** 选中的Tag Code */
    private var selectedItemCode: String? = null
    /** 编辑的文本 */
    private var editContent: String = ""

    var onSubmitClickListener: ((MutableMap<String, Any>) -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        outsideClickClose = true
        disableBackClose = false
        data = arguments?.getSerializable("data") as? NegativeFeedbackBean
        setView()
//        binding.space.setOnClickListener {
//            currentActivity()?.showToast("空白点击")
//            val itemViewType = listAdapter.getItemViewType(3)
//            val delegate = listAdapter.types.getType<Any>(itemViewType).delegate as ItemViewDelegate<*, *>
//            if (delegate is PositionNegativeEditDelegate) {
//                delegate.hideSoft()
//            }
//            close()
//        }
//        binding.root.setOnTouchListener { v, event ->
////            currentActivity()?.showToast("点击空白")
//            false
//        }
        KeyboardVisibilityEvent.setEventListener(currentActivity()!!, viewLifecycleOwner
        ) { isOpen ->
            if (isOpen) {
                binding.rvContent.run {
//                    updateLayoutParams<ViewGroup.LayoutParams> {
//                        height = 300.dp
//                        post { scrollBy(0, 30.dp) }
//                    }
                }
            } else {
                currentActivity()?.showToast("收起键盘")
                ValueAnimator.ofInt(300.dp, originRvContentHeight + 90.dp).apply {
                    duration = 200
                    addUpdateListener {
                        binding.rvContent.updateLayoutParams<ViewGroup.LayoutParams> {
                            height = it.animatedValue as Int
                        }
                    }
                }.start()
            }
        }

        activity?.window?.decorView?.setOnTouchListener { view, motionEvent ->
            currentActivity()?.showToast("点击空白")
            true
        }
    }

    override fun onOutSideClick() {

    }
    override fun onDismiss(dialog: DialogInterface) {
        val view = activity?.window?.currentFocus
        view?.let { (currentActivity()?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(it.windowToken, 0) }
        super.onDismiss(dialog)
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
//        currentActivity()?.showToast("点击空白")
    }

    private fun setView() {
        with(binding) {
            tvTitle.background =
                Bovb.with().color(getColor(R.color.C_FFFFFF)).topLeftRadius(16f.dp).topRightRadius(16f.dp).build()
            ivClose.onClick {
                close()
            }
            tvSubmit.run {
                isSubmitEnable = false
                onClick {
                    submit()
                }
            }
            rvContent.run {
                adapter = listAdapter
                listAdapter.setList(data?.labelList)
                post {
                    originRvContentHeight = this.height
                }
            }
        }
    }

    /**
     * 关闭
     */
    private fun close() {
        dismissAllowingStateLoss()
    }

    /**
     * 提交数据
     * 1019
     * 1262
     */
    private fun submit() {
        if (!isSubmitEnable) {
            return
        }
        val result = mutableMapOf<String, Any>()
        result[""] = ""
        onSubmitClickListener?.invoke(result)

        val itemViewType = listAdapter.getItemViewType(3)
        val delegate = listAdapter.types.getType<Any>(itemViewType).delegate as ItemViewDelegate<*, *>
        if (delegate is PositionNegativeEditDelegate) {
            delegate.hideSoft()
        }
    }

    /**
     * 筛选回调
     */
    private val onTagClick: (String, String?) -> Unit = { title, code ->
        selectedItemCode = code
        isSubmitEnable = selectedItemCode != null || editContent.isNotEmpty()
        when(title) {
            "reduce" -> listAdapter.notifyItemChanged(1, "clear")
            "no" -> listAdapter.notifyItemChanged(0, "clear")
        }
    }

    /**
     * 编辑框点击回调
     */
    private val onEditClick: (Boolean) -> Unit = { showEdit ->
        val realHeight = if (showEdit) originRvContentHeight + 90.dp else originRvContentHeight
        binding.rvContent.run {
            ValueAnimator.ofInt(realHeight, 300.dp).apply {
                duration = 200
                addUpdateListener {
                    binding.rvContent.updateLayoutParams<ViewGroup.LayoutParams> {
                        height = it.animatedValue as Int
                    }
                }
                doOnStart {
                    if (!showEdit) {
                        post { smoothScrollBy(0, 30.dp) }
                    } else {
                        postDelayed({
                            smoothScrollBy(0, (-60).dp)
                        }, 50)
                    }
                }
            }.start()

//            updateLayoutParams<ViewGroup.LayoutParams> {
//                height = 300.dp
//                if (!showEdit) {
//                    post { scrollBy(0, 30.dp) }
//                } else {
//                    postDelayed({
//                        scrollBy(0, (-60).dp)
//                    }, 50)
//                }
//            }
        }
    }

    /**
     * 编辑框文本变化
     */
    private val onTextChange: (String) -> Unit = {
        editContent = it
        isSubmitEnable = selectedItemCode != null || editContent.isNotEmpty()
    }



}