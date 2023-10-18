package com.example.kotlindemo.task.linkage

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.kotlindemo.R
import com.zhaopin.common.widget.linkage.adapter.viewholder.LinkagePrimaryViewHolder
import com.zhaopin.common.widget.linkage.contract.ILinkagePrimaryAdapterConfig
import com.zhaopin.social.common.extension.getColor

/**
 * @Description 职位二级筛选列表适配器配置（左侧）
 * @Author LuoJia
 * @Date 2023/7/3
 */
class PrimaryAdapterConfig : ILinkagePrimaryAdapterConfig {

    private var context: Context? = null

    override fun setContext(context: Context?) {
        this.context = context
    }

    override fun getLayoutId() = R.layout.item_linkage_primary

    override fun getGroupTitleViewId() = R.id.tvPrimaryTitle

    override fun getRootViewId() = R.id.llPrimaryRoot

    override fun onBindViewHolder(
        holder: LinkagePrimaryViewHolder?,
        selected: Boolean,
        title: String?
    ) {
        (holder?.groupTitle as? TextView)?.let {
            it.text = title
            it.setTextColor(
                if (selected) it.getColor(R.color.C_5B7BE9) else it.getColor(R.color.C_222222)
            )
            it.typeface = if (selected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }
        (holder?.layout as? ViewGroup)?.let {
            it.isFocusable = selected
            it.isFocusableInTouchMode = selected
            it.setBackgroundColor(
                if (selected) it.getColor(R.color.C_FFFFFF) else it.getColor(R.color.C_F7F8FA)
            )
            it.findViewById<View>(R.id.tvPrimaryDivide)?.visibility = if (selected) View.VISIBLE else View.INVISIBLE
        }
    }
}