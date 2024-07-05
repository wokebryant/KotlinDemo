package com.example.kotlindemo.task.appbar2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import coil.load
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemFamousEnterpriseSelectionBinding
import com.zhaopin.common.widget.flowLayout.FlowLayout
import com.zhaopin.common.widget.flowLayout.NoActionTagLy
import com.zhaopin.common.widget.flowLayout.TagAdapter
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * @Description
 * @Author LuoJia
 * @Date 2024/07/02
 */
class FamousEnterpriseAdapter2 : BaseBannerAdapter<FamousEnterpriseState.Item>() {

    override fun createViewHolder(
        parent: ViewGroup,
        itemView: View,
        viewType: Int
    ): BaseViewHolder<FamousEnterpriseState.Item> {
        return ViewBindingViewHolder(ItemFamousEnterpriseSelectionBinding.bind(itemView))
    }

    override fun bindData(
        holder: BaseViewHolder<FamousEnterpriseState.Item>,
        data: FamousEnterpriseState.Item,
        position: Int,
        pageSize: Int
    ) {
        if (holder is ViewBindingViewHolder) {
            with(holder.binding) {
                ivLogo.load(data.logo) {
                    placeholder(R.drawable.ic_resume_template_placeholder)
                    error(R.drawable.ic_resume_template_placeholder)
                }
                tvCompany.text = data.companyName
                tvCompanyAddress.text = data.companyAddress
                tvCompanySize.text = data.companySize
                tvCompanyNature.text= data.companyNature
                setFlowLayout(layoutLabel, data.labelList)
            }
        }
    }

    /**
     * 设置标签
     */
    private fun setFlowLayout(flowLayout: NoActionTagLy, list: List<String>) {
        if (list.isNotEmpty()) {
            flowLayout.setVisible()
            flowLayout.setAdapter(object : TagAdapter<String>(list) {
                override fun getView(parent: FlowLayout?, position: Int, t: String): View {
                    val textView = LayoutInflater.from(curContext)
                        .inflate(R.layout.resume_recommend_skill_tag, null, false) as TextView
                    textView.run {
                        text = t
                        background = Bovb.with().radius(6.dp.toFloat()).color(getColor(R.color.C_S2)).build()
                        setPadding(6.dp, 5.dp, 6.dp, 5.dp)
                    }
                    return textView
                }
            }, 0, 0, 6, 0)
        } else {
            flowLayout.setGone()
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_famous_enterprise_selection
    }
}

internal class ViewBindingViewHolder(var binding: ItemFamousEnterpriseSelectionBinding) :
    BaseViewHolder<FamousEnterpriseState.Item>(binding.root)