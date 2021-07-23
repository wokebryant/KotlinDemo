package com.example.kotlindemo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import kotlinx.android.synthetic.main.item_view_pager2.view.*

class Viewpager2Adapter(var dataList: ArrayList<Int>) : RecyclerView.Adapter<Viewpager2Adapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager2, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bindData(position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.item_text_view)
        private var colors = arrayOf("#CCFF99","#41F1E5","#8D41F1","#FF99CC")

        fun bindData(position: Int) {
            textView.text = position.toString()
            textView.setBackgroundColor(Color.parseColor(colors[position]))
        }

    }

}