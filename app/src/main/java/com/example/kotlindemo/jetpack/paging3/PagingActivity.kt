package com.example.kotlindemo.jetpack.paging3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.activity.BaseActivity
import com.example.kotlindemo.utils.StatusBarUtil
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 *  Paging3分页示例
 */
class PagingActivity : BaseActivity() {

    companion object {
        private const val TAG = "PagingActivityLog"
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val repoAdapter = RepoAdapter{ position, starView, adapter ->
        (starView as ImageView).setImageResource(R.drawable.ic_start_light)
        Toast.makeText(this, "is $position item click", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        initView()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
    }

    private fun initView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = repoAdapter.withLoadStateFooter(FooterAdapter {
            repoAdapter.retry()
        })

        lifecycleScope.launch {
            viewModel.getPagingData().collect { pagingData ->
                Log.i(TAG, " getDataPaging")
                repoAdapter.submitData(pagingData)
            }
        }

        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    progressBar.visibility = View.INVISIBLE
                    recyclerView.visibility = View.VISIBLE
                }

                is LoadState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.INVISIBLE
                }

                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}