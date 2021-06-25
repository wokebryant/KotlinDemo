package com.example.kotlindemo.jetpack.paging3

import com.google.gson.annotations.SerializedName
import java.util.Collections.emptyList

data class RepoResponse(
    @SerializedName("items") val items: List<Repo> = emptyList()
)

data class Repo (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("stargazers_count") val starCount: Int
)

enum class ServiceType {
    NORMAL,
    GITHUB
}