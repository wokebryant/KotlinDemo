package com.example.kotlindemo.task.appbar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.R
import com.example.kotlindemo.application.MyApplication.Companion.context
import com.example.kotlindemo.databinding.CampusItemLiveCardBinding

class LiveCardViewpagerAdapter(
    var dataList: ArrayList<String>,
    val onItemClickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<LiveCardViewpagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.campus_item_live_card, parent, false
        )
        val viewHolder = PagerViewHolder(view).apply {
            binding.root.setOnClickListener {
                val realPosition = CampusBannerUtil.getRealPosition(bindingAdapterPosition, dataList.size)
                onItemClickListener(realPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        with(holder.binding) {
            val realPosition = CampusBannerUtil.getRealPosition(position, dataList.size)
            // 显示直播状态
            Glide.with(context)
                .asGif()
                .load(R.drawable.position_home_gif_living_white)
                .into(ivCampusLiveState)
            tvCampusLiveState.text = "测试时间开播"
            // 加载封面图
            val testUrl = dataList[realPosition]
            Glide.with(context)
                .asBitmap()
                .load(testUrl)
                .into(ivCampusLiveCover)
            // 显示直播类型
            tvCampusLiveType.text = "中国联通校园"
        }
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 1) {
            CampusBannerUtil.MAX_VALUE
        } else {
            dataList.size
        }
    }

    inner class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: CampusItemLiveCardBinding
        init {
            binding = DataBindingUtil.bind(itemView)!!
        }
    }

}