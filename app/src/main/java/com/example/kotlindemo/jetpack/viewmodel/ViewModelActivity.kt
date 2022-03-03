package com.example.kotlindemo.jetpack.viewmodel

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ActivityViewmodelBinding
import com.example.kotlindemo.jetpack.lifecycle.MyObserver
import com.example.kotlindemo.jetpack.room.database.AppDataBase
import com.example.kotlindemo.jetpack.room.entity.User
import com.example.kotlindemo.utils.binding
import kotlin.concurrent.thread

class ViewModelActivity : AppCompatActivity() {

    private val binding: ActivityViewmodelBinding by binding()

    lateinit var viewModel: MainViewModel
    lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sp = getPreferences(Context.MODE_PRIVATE)
        val countReserved = sp.getInt("count_reserved", 0)
        viewModel = ViewModelProviders.of(this,
            MainViewModelFactory(countReserved)
        ).get(MainViewModel::class.java)
        binding.plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }

        binding.clearBtn.setOnClickListener {
            viewModel.clear()
        }

        binding.getUserBtn.setOnClickListener {
            val userId = (0..10000).random().toString()
            viewModel.user.observe(this, Observer {
                binding.infoText.text = it.firstName
            })
        }

        //liveData核心：观察者模式
        viewModel.counter.observe(this, Observer {
            binding.infoText.text = it.toString()
        })

        viewModel.userName.observe(this, Observer {

        })

        lifecycle.addObserver(MyObserver(lifecycle))

        room()
    }

    override fun onPause() {
        super.onPause()
//        sp.edit {
//            putInt("count_reserved", viewModel.counter.value ?: 0)
//        }
    }

    fun room() {
        val userDao = AppDataBase.getDataBase(this).userDao()
        val user1 = User("Tom", "Brady", 40)
        val user2 = User("Tom", "Hanks", 63)
        binding.addDataBtn.setOnClickListener {
            thread {
                user1.id = userDao.insertUser(user1)
                user2.id = userDao.insertUser(user2)
            }
        }

        binding.updateDataBtn.setOnClickListener {
            thread {
                user1.age = 42
                userDao.updateUser(user1)
            }
        }

        binding.deleteDataBtn.setOnClickListener {
            thread {
                userDao.deleteUserByLastName("Hanks")
            }
        }

        binding.queryDataBtn.setOnClickListener {
            thread {
                for (user in userDao.loadAllUsers()) {
                    Log.d("MainActivity", user.toString())
                }
            }
        }
    }



}