package com.example.kotlindemo.jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap

/**
 *  ViewModel 数据通过LiveData包装，可以将数据变化通知给观察者
 */
class MainViewModel(countReserved: Int) : ViewModel() {

    private var _counter = MutableLiveData<Int>()

    //get方法中返回_counter, 外部调用counter属性时返回的是_counter，但是无法更改counter
    //_ 下划线修饰的变量表示对外部不可见
    val counter: LiveData<Int>
        get() = _counter


    init {
        _counter.value = countReserved
    }

    fun plusOne() {
        val count = _counter.value ?: 0
        _counter.value = count + 1
    }

    fun clear() {
        _counter.value = 0
    }


    /**
     *  如果仅仅使用数据类的某字些段
     *  转化为实际需要的数据类
     *  例如userName类只需要name,不需要age
     */
    private val userLiveData = MutableLiveData<User>()

    val userName: LiveData<String> = userLiveData.map {
        "${it.firstName} ${it.secondName}"
    }

    /**
     *  如果liveData是从非viewModel类获取的，就要用到switchMap了
     *  将liveData转换成可观察的liveData
     */
    private val userIdLiveData = MutableLiveData<String>()

    val user: LiveData<User> = userIdLiveData.switchMap {
        Repository.getUser(it)
    }

    fun getUser(userId: String) {
        userIdLiveData.value = userId
    }

}

data class User(val firstName: String, val secondName: String, val age: Int)

object Repository {
     fun getUser(userId: String): LiveData<User> {
         val liveData = MutableLiveData<User>()
         liveData.value = User(userId, userId, 0)
         return liveData
     }
}