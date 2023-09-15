package com.example.kotlindemo.task.negavition

import android.os.Bundle
import android.view.View
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.MyContentRecommendDetailDialogBinding
import com.example.kotlindemo.utils.getColor
import com.zhaopin.common.widget.BaseBottomSheetDialogFragment
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 内容推荐算法说明
 * @Author LuoJia
 * @Date 2023/9/14
 */
class ContentRecommendDetailDialog : BaseBottomSheetDialogFragment<MyContentRecommendDetailDialogBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = ContentRecommendDetailDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        outsideClickClose = true
        with(binding) {
            tvTitle.background =
                Bovb.with().color(getColor(R.color.C_W1)).topLeftRadius(16f.dp).topRightRadius(16f.dp).build()
        }
    }

}