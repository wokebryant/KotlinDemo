package com.example.kotlindemo.task.mutildelivery

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.databinding.ItemMultiDeliveryBinding
import com.example.kotlindemo.study.content
import com.example.kotlindemo.task.mutildelivery.rankmulti.PositionRankMultiView
import com.example.kotlindemo.utils.BindingViewHolder

/**
 * @Description
 * @Author LuoJia
 * @Date 2022/8/29
 */
class DeliveryAdapter(
    private val context: Context,
    private val list: List<DeliveryItemBean>,
    private val onItemClick: (Int) -> Unit
): RecyclerView.Adapter<BindingViewHolder<ItemMultiDeliveryBinding>>() {


    private var clickPosition = -1

    override fun onViewAttachedToWindow(holder: BindingViewHolder<ItemMultiDeliveryBinding>) {
        super.onViewAttachedToWindow(holder)
        val position = holder.layoutPosition
        Log.i("LuoJia", position.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<ItemMultiDeliveryBinding> {
        val viewHolder = BindingViewHolder<ItemMultiDeliveryBinding>(parent)
        viewHolder.binding.clickBtn.setOnClickListener {
            clickPosition = viewHolder.layoutPosition
            onItemClick.invoke(clickPosition)
            Log.i("DeliveryAdapter", " position: ${viewHolder.layoutPosition}")
        }
        return viewHolder
    }


    override fun onBindViewHolder(holder: BindingViewHolder<ItemMultiDeliveryBinding>, position: Int) {
        val itemData = list[position]
        holder.binding.run {
            itemName.text = itemData.name
            itemJob.text = itemData.job
        }
        Log.i("DeliveryAdapter", " onBindViewHolder")

    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<ItemMultiDeliveryBinding>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            when (payloads[0]) {
                "tou" -> {
                    addNewView(holder)
                }
            }
        }
    }

    override fun getItemCount() = list.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun addNewView(holder: BindingViewHolder<ItemMultiDeliveryBinding>) {
        holder.binding.root.removeAllViews()
        holder.binding.root.addView(PositionRankMultiView(context), ViewGroup.LayoutParams(-1, -1))
        Log.i("DeliveryAdapter", "add_new_position: $clickPosition")
    }


}