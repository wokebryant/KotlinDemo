package com.example.kotlindemo.task.appbar2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ItemFamousEnterpriseSelectionBinding
import com.example.kotlindemo.utils.BindingViewHolder
//import com.example.kotlindemo.utils.BindingViewHolder
import com.zhaopin.common.widget.flowLayout.FlowLayout
import com.zhaopin.common.widget.flowLayout.NoActionTagLy
import com.zhaopin.common.widget.flowLayout.TagAdapter
import com.zhaopin.social.appbase.util.curContext
import com.zhaopin.social.background.util.Bovb
import com.zhaopin.social.common.extension.getColor
import com.zhaopin.social.common.extension.setGone
import com.zhaopin.social.common.extension.setVisible
import com.zhaopin.social.module_common_util.ext.dp

/**
 * @Description 名企精选适配器
 * @Author LuoJia
 * @Date 2024/06/26
 */
class FamousEnterpriseAdapter(
    private val data: FamousEnterpriseState
) : RecyclerView.Adapter<BindingViewHolder<ItemFamousEnterpriseSelectionBinding>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<ItemFamousEnterpriseSelectionBinding> = BindingViewHolder(parent)

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemFamousEnterpriseSelectionBinding>,
        position: Int
    ) {
        val itemData = data.list.getOrNull(position) ?: return

        with(holder.binding) {
            ivLogo.load(itemData.logo) {
                placeholder(R.drawable.ic_resume_template_placeholder)
                error(R.drawable.ic_resume_template_placeholder)
            }
            tvCompany.text = itemData.companyName
            tvCompanyAddress.text = itemData.companyAddress
            tvCompanySize.text = itemData.companySize
            tvCompanyNature.text= itemData.companyNature
            setFlowLayout(layoutLabel, itemData.labelList)
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

    override fun getItemCount(): Int = data.list.size

}