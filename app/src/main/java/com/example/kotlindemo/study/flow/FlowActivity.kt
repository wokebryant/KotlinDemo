package com.example.kotlindemo.study.flow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.databinding.ActivityFlowBinding
import com.example.kotlindemo.utils.binding
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

/**
 * @Description
 * @Author LuoJia
 * @Date 2023/3/30
 */
class FlowActivity : BaseActivity() {

    private val binding: ActivityFlowBinding by binding()

    private val viewModel by viewModels<FlowViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            binding.btnClose.setOnClickListener {
                Intent().putExtra("result", "updateTagLayout")
                this@FlowActivity.setResult(Activity.RESULT_OK, intent)
                this@FlowActivity.finish()
            }
            viewModel.startTimer()
            collectStateFlow()
//            button.setOnClickListener {

//                collectFlow()
//            }

            lifecycleScope.launch {
                val list = fetchUserData().single()
                print(list)
            }
        }


    }

    /**
     * Flow是冷流，没有订阅者时不会工作，且无法存储数据
     * 当前台 -> 后台：取消订阅Flow的数据，冷流不被订阅后，不会继续生产数据
     * 当后台 -> 前台：Flow重新被订阅，Flow被重新订阅后，会从头开始生产数据，之前的数据不被保存
     */
    private fun collectFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.timeFlow.collect { time ->
                    binding.button.text = time.toString()
                    Log.d("FlowTest", "Update time $time in UI.")
                }
            }
        }
    }

    /**
     * StateFlow是热流，热流没有订阅者也可以工作，能够存储数据
     * 当前台 -> 后台：取消订阅StateFlow的数据，但是SateFlow依然在生产数据，保存在内存中，只是接受者没有收到数据而已
     * 当后台 -> 前台：StateFlow重新被订阅，会将内存中当最新数据发送给订阅者
     */
    private fun collectStateFlow() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { time ->
                    binding.button.text = time.toString()
                    Log.d("FlowTest", "Update time $time in UI.")
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    fun fetchUserData() = flow {
        val mergeResultList = flowOf("user1", "user2", "user3")
            .flatMapMerge {
                flow {
                    val userResponse = User(it + "哈哈哈哈")
                    emit(userResponse)
                }
            }
            .toList() // 将所有结果收集到List中


        emitAll(flowOf(mergeResultList))
    }

    data class User (
        val name: String
    )

}