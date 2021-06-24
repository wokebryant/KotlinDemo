package com.example.kotlindemo.activity

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.kotlindemo.R
import com.example.kotlindemo.adapter.Fruit
import com.example.kotlindemo.adapter.FruitAdapter
import com.example.kotlindemo.utils.StatusBarUtil
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_material_design.*
import kotlinx.android.synthetic.main.activity_material_design.view.*
import kotlin.concurrent.thread

class MaterialDesignActivity : BaseActivity() {

    var fruitList = ArrayList<Fruit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)
        setSupportActionBar(toolbar)
//        supportActionBar?.let {
//            it.setDisplayHomeAsUpEnabled(true)
//            it.setHomeAsUpIndicator(R.drawable.ic_menu)
//        }
//        collapsingToolbar.title = "wokebryant"
        collapsingToolbar.expandedTitleMarginStart = 35
        Glide.with(this).load(R.drawable.lf_combsend_heart).into(fruitImage)
        navView.setCheckedItem(R.id.navCall)
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navCall -> ""
                R.id.navFriends -> ""
                R.id.navLocation -> drawerLayout.closeDrawers()

            }
            true
        }
        fab.setOnClickListener {
            Snackbar.make(it, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo") {
                Toast.makeText(this, "Data restored", Toast.LENGTH_SHORT).show()
            }.show()
        }

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView.layoutManager = layoutManager
        initFruits()
        val adapter = FruitAdapter(this, fruitList)
        recyclerView.adapter = adapter

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary)
        swipeRefresh.setOnRefreshListener {
            refreshFruits(adapter)
        }
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
                swipeRefresh.isRefreshing = false
            }
        }
    }


}