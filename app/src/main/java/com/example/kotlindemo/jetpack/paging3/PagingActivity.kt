package com.example.kotlindemo.jetpack.paging3

import android.annotation.SuppressLint
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
import com.example.kotlindemo.databinding.ActivityPagingBinding
import com.example.kotlindemo.utils.StatusBarUtil
import com.example.kotlindemo.utils.binding
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

    private val binding: ActivityPagingBinding by binding()

    private lateinit var mPagingData: PagingData<Repo>

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val itemUpdate: (Int, Repo, RepoAdapter) -> Unit = { position, repo, adapter ->
        repo.name = "修改数据$position"
        adapter.notifyDataSetChanged()
    }

    private val itemDelete: (Repo) ->Unit = { repo ->
        val pagingData = viewModel.pagingData.value

        pagingData
            ?.filter { repo.id != it.id }
            .let {
                if (it != null) {
                    viewModel.updatePagingData(it)
                }
            }

        Toast.makeText(this, "删除 ${repo.id}", Toast.LENGTH_SHORT).show()
    }

    private val repoAdapter = RepoAdapter(itemUpdate, itemDelete)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        StatusBarUtil.setRootViewFitsSystemWindows(this, true)
    }

    private fun initView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = repoAdapter.withLoadStateFooter(FooterAdapter {
            repoAdapter.retry()
        })

        binding.refreshLayout.setOnRefreshListener {
            repoAdapter.refresh()
        }

//        lifecycleScope.launch {
//            viewModel.getPagingData().flowOn(Dispatchers.IO).collect { pagingData ->
//                Log.i(TAG, " getDataPaging")
//                repoAdapter.submitData(lifecycle, mPagingData)
//            }
//        }

        //增删liveData相比flow容易操作
        viewModel.pagingData.observe(this) {
            repoAdapter.submitData(lifecycle, it)
        }

        repoAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    binding.refreshLayout.isRefreshing = false
                    if (it.source.append.endOfPaginationReached) {
                        Log.i("LUOJIA ", " loadData= start" )
                    }
                }

                is LoadState.Loading -> {
                    binding.refreshLayout.isRefreshing = true
                }

                is LoadState.Error -> {
                    val state = it.refresh as LoadState.Error
                    binding.refreshLayout.isRefreshing = false
                    Toast.makeText(this, "Load Error: ${state.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}