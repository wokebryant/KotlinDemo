package com.example.kotlindemo.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.model.App
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_web.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class WebActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://www.baidu.com")
    }

    fun testHttpURLConnection() {
        thread {
            var connection : HttpURLConnection? = null
            try {
                val response = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 8000
                connection.readTimeout = 8000
                val input = connection.inputStream

                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        response.append(it)
                    }
                }
            } catch (e: Exception) {

            }

        }

    }

    fun testOkhttpRequest() {
        val client = OkHttpClient()

        //对于post请求
        val requestBody = FormBody.Builder().run {
            add("userName", "admin")
            add("password", "123456")
            build()
        }

        val request = Request.Builder().run {
            url("https://ww.baidu.com")
            post(requestBody)
            build()
        }

        val response = client.newCall(request).execute()
//        val responseData = response.body?.string()
        val responseData = response.body()?.string()
        if (responseData != null) Toast.makeText(this, responseData, Toast.LENGTH_SHORT).show()

    }

    fun testGsonParse() {
        val gson = Gson()
        val typeOf = object : TypeToken<List<App>>() {}.type
        val appList = gson.fromJson<List<App>>("", typeOf)
    }

    class AA() {

    }



}