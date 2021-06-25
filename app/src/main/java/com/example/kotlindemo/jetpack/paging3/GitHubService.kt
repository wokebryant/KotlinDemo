package com.example.kotlindemo.jetpack.paging3

import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("search/repositories?sort=stars&q=Android")
    suspend fun searchRepos(@Query("page") page: Int, @Query("per_page") perPage: Int): RepoResponse

}