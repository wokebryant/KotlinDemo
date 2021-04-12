package com.example.kotlindemo.retrofit.`interface`

import com.example.kotlindemo.retrofit.model.App
import com.example.kotlindemo.retrofit.model.Data
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface AppService {

    /**
     *  静态接口地地址
     *  例如：http://example.com/get_data.json
     */
    @GET("get_data.json")
    fun getAppData(): Call<List<App>>

    /**
     * 动态接口地址
     * Path注解会在方法调用时将page参数替换到{page}占位符中
     * 例如：http://example.com/<page>/get_data.json
     */
    @GET("{page}/get_data.json")
    fun getData(@Path("page")page: Int): Call<Data>

    /**
     *  对于带参数的请求地址
     *  例如：http://example.com/get_data.json?u=<user>&t=<token>
     */
    @GET("get_data.json")
    fun getDataWithParams(@Query("u")user: String, @Query("t")token: String): Call<Data>

    /**
     *  删除一条服务器数据
     *  例如：http://example.com/data/<id>
     */
    @DELETE("data/{id}")
    fun deleteData(@Path("id")id: String): Call<ResponseBody>

    /**
     * Post请求
     * 提交的数据放在@Body注解中，以实体类的形式
     *
     */
    @POST("data/create")
    fun postData(@Body data: Data): Call<ResponseBody>

    /**
     *  对于需要传递Header传输的请求(静态)
     */
    @Headers("User-Agent: okHttp", "Cache-Control: max-age=0")
    @GET("get_data.json")
    fun getDataWithHeader(): Call<Data>

    /**
     *  对于需要传递Header传输的请求(动态)
     */
    @GET("get_data.json")
    fun getDataWithHeader1(@Header("User-Agent")userAgent: String,
                           @Header("Cache-Control")cacheControl: String): Call<Data>


}