package com.example.kotlindemo.jetpack.paging3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.activity.TransformActivity
import com.example.kotlindemo.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_paging.*
import kotlinx.android.synthetic.main.repo_footer.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 *  Paging3分页示例
 */
class PagingActivity : TransformActivity() {

    companion object {
        private const val TAG = "PagingActivityLog"
    }

    private lateinit var mPagingData: PagingData<Repo>

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private val itemUpdate: (Int, Repo, RepoAdapter) -> Unit = {position, repo, adapter ->
        repo.name = "修改数据$position"
        adapter.notifyDataSetChanged()
    }

    private val itemDelete: (Int) ->Unit = { position ->
        mPagingData = mPagingData.filter {
            it != repoAdapter.getData(0) }
        repoAdapter.submitData(lifecycle, mPagingData)
//        repoAdapter.notifyItemRemoved(position)
        Toast.makeText(this, "删除 $position", Toast.LENGTH_SHORT).show()
    }

    private val repoAdapter = RepoAdapter(itemUpdate, itemDelete)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paging)
        initView()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
    }

    private fun initView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = repoAdapter.withLoadStateFooter(FooterAdapter {
            repoAdapter.retry()
        })

        refresh_layout.setOnRefreshListener {
            repoAdapter.refresh()
        }

        lifecycleScope.launch {
            viewModel.getPagingData().flowOn(Dispatchers.IO).collect { pagingData ->
                Log.i(TAG, " getDataPaging")
                mPagingData = pagingData
                repoAdapter.submitData(lifecycle, mPagingData)
            }
        }

        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    refresh_layout.isRefreshing = false
                    if (it.source.append.endOfPaginationReached) {
                        Log.i("LUOJIA ", " loadData= start" )
                    }
                }

                is LoadState.Loading -> {
                    refresh_layout.isRefreshing = true
                }

                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    refresh_layout.isRefreshing = false
                    Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}