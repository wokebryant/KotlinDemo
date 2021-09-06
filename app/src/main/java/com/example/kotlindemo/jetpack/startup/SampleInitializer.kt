package com.example.kotlindemo.jetpack.startup

import android.content.Context
import androidx.startup.Initializer

/**
 *  StartUp启动示例
 *  对于多个使用ContentProvider作启动的SDK有一定作用，大多数情况下没啥用
 */
class SampleInitializer : Initializer<Unit> {

    private var isDependenciesOther = false

    override fun create(context: Context) {
        SampleSDK.initialize()
    }

    /**
     *  初始化的库是否依赖其它库
     */
    override fun dependencies(): List<Class<OtherInitializer>> {
        return if (isDependenciesOther) {
            listOf(OtherInitializer::class.java)
        } else {
            emptyList()
        }
    }
}