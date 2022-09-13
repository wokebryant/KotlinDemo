package com.example.kotlindemo.task.mutildelivery.rankmulti

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.util.Util
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.AdapterPositionRankMultiChildBinding
import com.example.kotlindemo.utils.AppUtil
import com.example.kotlindemo.utils.dip2px

/**
 * @Description 职位榜单批投适配器
 * @Author LuoJia
 * @Date 2022/8/30
 */
class PositionRankMultiAdapter(
    private val jobList: ArrayList<PositionRankMultiModel>,
    private val onItemCheckCallback: () -> Unit,
    private val onItemJobDetailCallback: (Int) -> Unit
) : RecyclerView.Adapter<PositionRankMultiAdapter.MultiViewHolder>() {

    /** 是否已投递 */
    var isDelivered = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_position_rank_multi_child, parent, false
        )
        val holder = MultiViewHolder(view).apply {
            binding.ivMultiCheck.setOnClickListener { onItemCheck(this) }
            binding.llJobDetail.setOnClickListener { onItemJobDetailCallback.invoke(layoutPosition) }
        }
        return holder
    }

    override fun onBindViewHolder(holder: MultiViewHolder, position: Int) {
        val itemData = jobList[position]
        holder.binding.run {
//            val width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//            val height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
//            tvMultiJob.measure(width, height)
            tvMultiJob.text = itemData.job
            tvMultiSalary.text = itemData.salary
            tvMultiCompany.text = itemData.company
            tvMultiWorkYear.text = itemData.workYear
            tvMultiEducation.text = itemData.education
            tvMultiSkill.text = itemData.skill
            viewDivideLine.visibility = if (position == jobList.size - 1) View.GONE else View.VISIBLE
            updateCheckView(ivMultiCheck, itemData.checkState)
            getRealWidth(holder)
        }
    }

    private fun getRealWidth(holder: MultiViewHolder) {
        if (!isDelivered) {
            return
        }
        holder.binding.run {
            val tagPaddingHorizontal = dip2px(8f)
            val tagMargin = dip2px(3f)
            tvMultiJob.post {
                // 职位文本长度
                val textPaintWidth = tvMultiJob.paint.measureText(tvMultiJob.text as String?)
                // "已投递"标签长度
                val tagPaintWidth = tvMultiDelivered.paint.measureText(tvMultiDelivered.text as String?) + tagPaddingHorizontal
                // 职位TextView长度
                val textViewWidth = llMultiJob.width - tagPaintWidth - tagMargin

                // 文字长度小于TextView长度, 需要重新计算TextView
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                if (textViewWidth > textPaintWidth) {
                    tvMultiJob.layoutParams = lp.apply {
                        weight = 0f
                        width = textPaintWidth.toInt()
                    }
                }
                // 当前职位是否为已投递状态
                val currentPositionDelivered = jobList[holder.layoutPosition].checkState == CheckState.Gray
                tvMultiDelivered.visibility = if (currentPositionDelivered) View.VISIBLE else View.GONE
            }
        }
    }

    override fun getItemCount() = jobList.size

    private fun onItemCheck(viewHolder: MultiViewHolder) {
        if (isDelivered) {
            return
        }
        val checkView = viewHolder.binding.ivMultiCheck
        val checkState = if (checkView.isSelected) CheckState.UnCheck else CheckState.Check
        val position = viewHolder.layoutPosition
        updateJobList(position, checkState)
        updateCheckView(checkView, checkState)
        onItemCheckCallback.invoke()
    }

    private fun updateCheckView(checkView: ImageView, checkState: CheckState) {
        when (checkState) {
            CheckState.Check -> checkView.isSelected = true
            CheckState.UnCheck -> checkView.isSelected = false
            CheckState.Gray -> checkView.isEnabled = false
        }
    }

    private fun updateJobList(position: Int, checkState: CheckState) {
        if (position >= jobList.size) return
        jobList[position].apply {
            this.checkState = checkState
        }
    }

    inner class MultiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding: AdapterPositionRankMultiChildBinding

        init {
            binding = DataBindingUtil.bind(view)!!
        }
    }

}