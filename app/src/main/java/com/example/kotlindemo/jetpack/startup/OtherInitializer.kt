package com.example.kotlindemo.jetpack.startup

import android.content.Context
import androidx.startup.Initializer

class OtherInitializer : Initializer<Unit>{

    fun initialize() {

    }

    override fun create(context: Context) {
        TODO("Not yet implemented")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        TODO("Not yet implemented")
    }
}