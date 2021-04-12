package com.example.kotlindemo.jetpack.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(var name: String, var pages: Int, val author: String) {

    @PrimaryKey
    var id: Long = 0
}