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
            tvMultiDelivered.visibility =
                if (itemData.checkState == CheckState.Gray) View.VISIBLE else View.GONE
            updateCheckView(ivMultiCheck, itemData.checkState)
            getRealWidth(holder)
        }
    }

    private fun getRealWidth(holder: MultiViewHolder) {
        val tvJob = holder.binding.tvMultiJob
        tvJob.post {
            val viewWith = tvJob.width

            val textPaint = tvJob.paint
            val paintWidth = textPaint.measureText(tvJob.text as String?)

            // 文字长度小于TextView长度, 需要重新计算TextView
            val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            if (viewWith > paintWidth) {
                lp.width = paintWidth.toInt()
                lp.weight = 0f
                tvJob.layoutParams = lp
            }

            Log.i("LuoJia: ", " viewWidth= $viewWith")
            Log.i("LuoJia: ", " paintWidth= $paintWidth")
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