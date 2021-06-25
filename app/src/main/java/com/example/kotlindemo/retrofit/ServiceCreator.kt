package com.example.kotlindemo.retrofit

import com.example.kotlindemo.jetpack.paging3.ServiceType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private const val NORMAL_URL = "http://10.0.2.2"
    private const val GITHUB_BASE_URL = "https://api.github.com/"

    private var BASE_URL: String = NORMAL_URL

    private fun getRetrofit(type: ServiceType): Retrofit {
        BASE_URL = when (type) {
            ServiceType.NORMAL -> NORMAL_URL

            ServiceType.GITHUB -> GITHUB_BASE_URL
        }
        return Retrofit.Builder().run {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
    }

    fun <T> create(serviceClass: Class<T>, type: ServiceType): T = getRetrofit(type).create(serviceClass)

    inline fun <reified T> create(type : ServiceType = ServiceType.NORMAL): T = create(T::class.java, type)
}