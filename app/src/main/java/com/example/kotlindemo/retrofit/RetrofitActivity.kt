package com.example.kotlindemo.retrofit

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.R
import com.example.kotlindemo.databinding.ActivityRetrofitBinding
import com.example.kotlindemo.retrofit.`interface`.AppService
import com.example.kotlindemo.retrofit.model.App
import com.example.kotlindemo.utils.binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RetrofitActivity : AppCompatActivity() {

    companion object {
        const val TAG = "RetrofitActivity"
    }

    private val binding: ActivityRetrofitBinding by binding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.getAppDataBtn.setOnClickListener(viewClickListener)
    }

    private val viewClickListener = View.OnClickListener {
        when(it.id) {
            binding.getAppDataBtn.id -> getAppData()
        }
    }

    private fun getAppData() {
        val appService = ServiceCreator.create<AppService>()
        appService.getAppData().enqueue(object : Callback<List<App>> {
            override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {
                val list = response.body()
                if (list != null) {
                    for (app in list) {
                        Log.i(TAG, "Get App Data Success")
                        Log.d(TAG, "id is ${app.id}")
                        Log.d(TAG, "name is ${app.name}")
                        Log.d(TAG, "version is ${app.version}")
                    }
                }
            }

            override fun onFailure(call: Call<List<App>>, t: Throwable) {
                Log.i(TAG, "Get App Data Fail")
            }

        })

    }

    /**
     *  通过协程优化
     */
    private suspend fun getDataWithCoroutines() {
        try {
            val appList = ServiceCreator.create<AppService>().getAppData().await()
        }catch (e: Exception) {

        }

    }

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) it.resume(body)
                    else it.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }



}