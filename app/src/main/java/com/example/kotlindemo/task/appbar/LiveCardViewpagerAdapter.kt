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
import com.example.kotlindemo.utils.setGone
import com.example.kotlindemo.utils.setInvisible
import com.example.kotlindemo.utils.setVisible

class LiveCardViewpagerAdapter(
    var dataList: List<LiveCardModel>,
) : RecyclerView.Adapter<LiveCardViewpagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.campus_item_live_card, parent, false
        )
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        with(holder.binding) {
            val realPosition = CampusBannerUtil.getRealPosition(position, dataList.size)
            // 显示直播状态
            Glide.with(context)
                .asGif()
                .load(R.drawable.position_home_gif_living_white)
                .into(ivCampusLiveState)
            tvCampusLiveState.text = dataList[realPosition].position.toString()
            // 加载封面图
            val testUrl = dataList[realPosition].url
            Glide.with(context)
                .asBitmap()
                .load(testUrl)
                .into(ivCampusLiveCover)
            // 显示直播类型
            tvCampusLiveType.text = "中国联通校园"
            // 选中态和非选中态UI
            val selectPosition = dataList[realPosition].position
            if (selectPosition == position) {
                viewLiveCardMask.setGone()
                tvCampusLiveType.setVisible()
                tvCampusLiveState.setVisible()
                ivCampusLiveState.setVisible()
            } else {
                viewLiveCardMask.setVisible()
                tvCampusLiveType.setGone()
                tvCampusLiveState.setGone()
                ivCampusLiveState.setGone()
            }

            when (dataList.size) {
                3, 4, 5 -> {
                    if (position == selectPosition + 2 || position == selectPosition - 2) {
                        root.setInvisible()
                    } else {
                        root.setVisible()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 2) {
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