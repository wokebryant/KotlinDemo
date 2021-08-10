package com.example.kotlindemo.jetpack.paging3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R

class RepoAdapter(val itemUpdate: (Int, Repo, RepoAdapter) -> Unit, 
                  val itemDelete: (Int) -> Unit) 
    : PagingDataAdapter<Repo, RepoAdapter.ViewHolder>(COMPARATOR) {

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(p0: Repo, p1: Repo): Boolean {
                return p0.id == p1.id
            }

            override fun areContentsTheSame(p0: Repo, p1: Repo): Boolean {
                return p0 == p1
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name_text)
        val description: TextView = itemView.findViewById(R.id.description_text)
        val startCount: TextView = itemView.findViewById(R.id.star_count_text)
        val startImage: ImageView = itemView.findViewById(R.id.star_image)
        val deleteTv: TextView = itemView.findViewById(R.id.delete_tv)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.repo_item, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        val repo = getItem(p1)
        repo?.let {
            holder.name.text = it.name
            holder.description.text = it.description
            holder.startCount.text = it.starCount.toString()
            holder.startImage.apply {
                setOnClickListener {
                   itemUpdate(p1, repo, this@RepoAdapter)
                }
            }
            
            holder.deleteTv.setOnClickListener { 
//                itemDelete(p1)
            }
        }
    }

    fun getData(position: Int): Repo? {
        return getItem(position)
    }
    
}