package com.example.kotlindemo.jetpack.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(var firstName: String, var lastName: String, var age: Int) {

    @PrimaryKey
    var id: Long = 0
}