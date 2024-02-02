package com.example.kotlindemo.task.jobdetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.LayoutSameDayInterviewBinding
import com.example.kotlindemo.databinding.LayoutSameDayInterviewItemBinding
import com.zhaopin.common.widget.BaseBottomSheetDialogFragment
import com.zhaopin.list.multitype.adapter.MultiTypeAdapter
import com.zhaopin.list.multitype.adapter.setList
import com.zhaopin.list.multitype.binder.BindingViewDelegate
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.module_common_util.ext.dp
import com.zhaopin.social.module_common_util.ext.onClick
import java.io.Serializable

/**
 * @Description 当天面弹窗
 * @Author LuoJia
 * @Date 2024/1/22
 */
class SameDayInterviewPanel : BaseBottomSheetDialogFragment<LayoutSameDayInterviewBinding>() {

    companion object {
        fun newInstance(data: CurInterviewPanelData) =
            SameDayInterviewPanel().apply {
                arguments = Bundle().apply {
                    putSerializable("data", data)
                }
            }
    }

    private val listAdapter by lazy {
        MultiTypeAdapter().apply {
            register(ContentDelegate())
        }
    }

    private var data: CurInterviewPanelData? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        outsideClickClose = true
        disableBackClose = false
        data = arguments?.getSerializable("data") as? CurInterviewPanelData
        setView()
    }

    private fun setView() {
        with(binding) {
            root.background =
                Bovb.with()
                    .color(getColor(R.color.C_W1))
                    .topLeftRadius(16f.dp).topRightRadius(16f.dp)
                    .build()
            tvContentTip.text = data?.title ?: "什么是当天面职位？"
            rvContent.run {
                adapter = listAdapter
                listAdapter.setList(data?.contentList)
            }
            tvGetIt.run {
                onClick {
                    dismissAllowingStateLoss()
                }
                background =
                    Bovb.with().color(getColor(R.color.C_P1)).radius(22f.dp).build()
            }
            ivClose.onClick {
                dismissAllowingStateLoss()
            }
        }
    }

    inner class ContentDelegate : BindingViewDelegate<CurInterviewContentState, LayoutSameDayInterviewItemBinding>() {
        override fun onBindViewHolder(
            binding: LayoutSameDayInterviewItemBinding,
            item: CurInterviewContentState,
            position: Int
        ) {
            binding.tvContent.text = item.content
            binding.tvContent.setTextColor(Color.parseColor(item.color))
        }
    }

}

data class CurInterviewContentState(
    val content: String,
    val color: String
) : Serializable

data class CurInterviewPanelData(
    val icon: String,
    val title: String,
    val contentList: List<CurInterviewContentState>
) : Serializable