package com.example.kotlindemo.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.R
import com.example.kotlindemo.adapter.Fruit
import com.example.kotlindemo.adapter.FruitAdapter
import com.example.kotlindemo.databinding.ActivityMaterialDesignBinding
import com.example.kotlindemo.utils.binding
import com.google.android.material.snackbar.Snackbar
import kotlin.concurrent.thread

class MaterialDesignActivity : TransformActivity() {

    private val binding: ActivityMaterialDesignBinding by binding()

    var fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
//        supportActionBar?.let {
//            it.setDisplayHomeAsUpEnabled(true)
//            it.setHomeAsUpIndicator(R.drawable.ic_menu)
//        }
//        collapsingToolbar.title = "wokebryant"
        binding.collapsingToolbar.expandedTitleMarginStart = 35
        Glide.with(this).load(R.drawable.lf_combsend_heart).into(binding.fruitImage)
        binding.navView.setCheckedItem(R.id.navCall)
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navCall -> ""
                R.id.navFriends -> ""
                R.id.navLocation -> binding.drawerLayout.closeDrawers()

            }
            true
        }
        binding.fab.setOnClickListener {
            Snackbar.make(it, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
            }.show()
        }

        val layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.layoutManager = layoutManager
        initFruits()
        val adapter = FruitAdapter(this, fruitList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(scrollListener)

        binding.swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        binding.swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
        setFreeChatBall()
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            android.R.id.home -> drawerLayout.openDrawer(GravityCompat.START)
//        }
//        return true
//    }

    val fruits = mutableListOf(Fruit("Apple", R.drawable.lf_combsend_heart)
        ,Fruit("Orange", R.drawable.lf_combsend_laugh)
        ,Fruit("Pear", R.drawable.lf_combsend_like))

    private fun getFriutList(): List<Fruit> {
        val fruitList = ArrayList<Fruit>()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
        return fruitList
    }

    private fun initFruits() {
        fruitList.clear()
        repeat(50) {
            val index = (0 until fruits.size).random()
            fruitList.add(fruits[index])
        }
    }

    private fun refreshFruits(adapter: FruitAdapter) {
        thread {
            Thread.sleep(2000)
            runOnUiThread {
                initFruits()
                adapter.notifyDataSetChanged()
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    /**
     * 悬浮球控制
     */
    private fun setFreeChatBall() {
//        binding.freeChatBall.setFirstLevelChat()
        binding.freeChatBall.setSecondLevelChat(2, 3)
    }

    private var curScrollState = RecyclerView.SCROLL_STATE_IDLE
    private val handler = Handler(Looper.getMainLooper())

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            Log.i("LuoJia: ", newState.toString())
            curScrollState = newState
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                showBallAnim()
            } else {
                binding.freeChatBall.startBallHideAnim()
            }
        }
    }

    private fun showBallAnim() {
        handler.removeCallbacks(runnable)
        handler.postDelayed(runnable, 2000)
    }


    private var runnable = Runnable {
        if (curScrollState != RecyclerView.SCROLL_STATE_IDLE) {
            return@Runnable
        }
        binding.freeChatBall.startBallShowAnim()
    }

}