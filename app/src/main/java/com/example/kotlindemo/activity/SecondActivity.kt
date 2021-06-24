package com.example.kotlindemo.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlindemo.*
import com.example.kotlindemo.study.LambdaFun
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        LambdaFun()
        val extraData = intent.getStringExtra("extra_data")
        Log.i("SecondActivity", "extra data is $extraData")
    }

    private fun initView() {
        button2.setOnClickListener {
        }
    }

    /**
     *  类似于java public static 方法调用
     */
    companion object {
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra("params1", data1)
            intent.putExtra("params2", data2)
            context.startActivity(intent)
        }
    }
}
