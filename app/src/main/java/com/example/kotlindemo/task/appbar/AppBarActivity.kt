package com.example.kotlindemo.task.appbar

import android.os.Bundle
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityAppbarBinding
import com.example.kotlindemo.utils.binding
import com.example.kotlindemo.utils.setVisible

/**
 * @Description
 * @Author
 * @Date
 */
class AppBarActivity : BaseActivity() {

    private val binding: ActivityAppbarBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
//            appbar.setVisible()
            appbar2.setViewPager()
//            appbar2.setViewPager2(lifecycle)
        }
    }


}