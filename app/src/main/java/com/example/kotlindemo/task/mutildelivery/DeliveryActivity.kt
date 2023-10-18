package com.example.kotlindemo.task.mutildelivery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityMultiDeliveryBinding
import com.example.kotlindemo.utils.binding

/**
 * @Description
 * @Author LuoJia
 * @Date 2022/8/29
 */
class DeliveryActivity : BaseActivity() {

    companion object {
        private const val TAG = "DeliveryActivity"
    }

    private val binding: ActivityMultiDeliveryBinding by binding()

    private lateinit var adapter: DeliveryAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            adapter = DeliveryAdapter(this@DeliveryActivity, getTestJobList(), onItemClick)
            jobRv.layoutManager = LinearLayoutManager(this@DeliveryActivity)
            jobRv.adapter = adapter
//            jobRv.setItemViewCacheSize(20)
            resetBtn.setOnClickListener {
                adapter.notifyDataSetChanged()
            }
        }
    }

    private val onItemClick: (Int) -> Unit = {
        adapter.notifyItemRangeChanged(it, 1, "tou")
    }

    private fun getTestJobList(): List<DeliveryItemBean> {
        val list = mutableListOf<DeliveryItemBean>()
        for (index in 0 until 10) {
            list.add(
                DeliveryItemBean(
                    name = "职位$index",
                    job = "这是测试职位$index"
                )
            )
        }
        return list
    }

}