package com.example.kotlindemo.jetpack.paging3

import com.google.gson.annotations.SerializedName
import java.util.Collections.emptyList

data class RepoResponse(
    @SerializedName("items") var items: List<Repo> = emptyList()
)

data class Repo (
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") var starCount: Int
)

enum class ServiceType {
    NORMAL,
    GITHUB
}