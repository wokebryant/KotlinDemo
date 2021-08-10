package com.example.kotlindemo.jetpack.paging3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R

class FooterAdapter(val retry: () -> Unit) : LoadStateAdapter<FooterAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
        val retryButton: Button = itemView.findViewById(R.id.retry_button)
        val noMoreDataTv: TextView = itemView.findViewById(R.id.no_more_data_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_footer, parent, false)
        val holder = ViewHolder(view)
        holder.retryButton.setOnClickListener {
            retry()
        }

        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        Log.i("LUOJIA ", " loadData= $loadState" )
        holder.progressBar.visibility = if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
        holder.retryButton.visibility = if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        holder.noMoreDataTv.visibility = if (loadState.endOfPaginationReached) View.VISIBLE else View.GONE
    }

    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return loadState is LoadState.Loading
                || loadState is LoadState.Error
                || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
    }
}