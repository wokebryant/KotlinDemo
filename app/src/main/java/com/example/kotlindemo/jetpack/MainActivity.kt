package com.example.kotlindemo.jetpack

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.example.kotlindemo.R
import com.example.kotlindemo.jetpack.lifecycle.MyObserver
import com.example.kotlindemo.jetpack.room.dao.UserDao
import com.example.kotlindemo.jetpack.room.database.AppDataBase
import com.example.kotlindemo.jetpack.room.entity.User
import com.example.kotlindemo.jetpack.viewmodel.MainViewModel
import com.example.kotlindemo.jetpack.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_viewmodel.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewmodel)
        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)
        viewModel = ViewModelProviders.of(this,
            MainViewModelFactory(countReserved)
        ).get(MainViewModel::class.java)
        plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }

        clearBtn.setOnClickListener {
            viewModel.clear()
        }

        getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            viewModel.user.observe(this, Observer {
                infoText.text = it.firstName
            })
        }

        //liveData核心：观察者模式
        viewModel.counter.observe(this, Observer {
            infoText.text = it.toString()
        })

        viewModel.userName.observe(this, Observer {

        })

        lifecycle.addObserver(MyObserver(lifecycle))

        room()
    }

    override fun onPause() {
        super.onPause()
        sp.edit {
            putInt("count_reserved", viewModel.counter.value ?: 0)
        }
    }

    fun room() {
        val userDao = AppDataBase.getDataBase(this).userDao()
        val user1 = User("Tom", "Brady", 40)
        val user2 = User("Tom", "Hanks", 63)
        addDataBtn.setOnClickListener {
            thread {
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
            }
        }

        updateDataBtn.setOnClickListener {
            thread {
                user1.age = 42
                userDao.updateUser(user1)
            }
        }

        deleteDataBtn.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("Hanks")
            }
        }

        queryDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()) {
                    Log.d("MainActivity", user.toString())
                }
            }
        }
    }



}