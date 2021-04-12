package com.example.kotlindemo.jetpack.workmanager

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 *  处理定时任务
 *  可以执行链式任务
 */
class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object{
        const val TAG = "SampleWork"
    }

    fun startDoWork(context: Context) {
        WorkManager.getInstance(context).enqueue(request)
    }

    fun cancelWork(context: Context) {
        WorkManager.getInstance(context).cancelAllWorkByTag("simple")
    }

    fun observeWorkResult(context: Context) {
        val activity = context as LifecycleOwner
        WorkManager.getInstance(context).getWorkInfoByIdLiveData(request.id).observe(activity, Observer{
            when (it.state) {
                WorkInfo.State.SUCCEEDED -> Log.d(TAG, "")
                WorkInfo.State.FAILED -> Log.d(TAG, "")
            }
        })
    }

    /**
     *  构建单次运行的后台任务请求
     */
    val request= OneTimeWorkRequest.Builder(SimpleWorker::class.java).run {
        setInitialDelay(5, TimeUnit.MINUTES)
        addTag("Simple")
        build()
    }


    /**
     *  构建周期性运行的后台任务请求
     */
    val repeatRequest = PeriodicWorkRequest.Builder(SimpleWorker::class.java, 15, TimeUnit.MINUTES).build()

    override fun doWork(): Result {
        Log.d(TAG, "do work in SimpleWorker")
        return Result.success()
    }
}