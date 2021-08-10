package com.example.kotlindemo.utils

import com.example.kotlindemo.application.MyApplication
import com.google.gson.Gson
import com.google.gson.JsonParser
import java.io.*


    /**
     *  Json字符串转数组
     */
    inline fun <reified T> transformJsonStringToArray(assetFilePath: String, cls: Class<T>): Array<T> {
        val list = arrayListOf<T>()

        try {
            val jsonString = getStringFromAssets(assetFilePath)
            val jsonArray = JsonParser.parseString(jsonString).asJsonArray

            jsonArray.forEach {
                val skillInfo = Gson().fromJson(it, cls)
                list.add(skillInfo)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return list.toTypedArray()
    }

    fun getStringFromAssets(text: String): String? {
        try {
            val inputStream: InputStream = MyApplication.context.assets.open(text)
            return getString(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 获取文件内容
     */
    fun getString(inputStream: InputStream?): String? {
        var inputStreamReader: InputStreamReader? = null
        try {
            inputStreamReader = InputStreamReader(inputStream, "UTF-8")
        } catch (e1: UnsupportedEncodingException) {
            e1.printStackTrace()
        }
        val reader = BufferedReader(inputStreamReader)
        val sb = StringBuffer("")
        var line: String?
        try {
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
                sb.append("\n")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }
