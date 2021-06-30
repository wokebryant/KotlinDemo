package com.example.kotlindemo.study

import com.example.kotlindemo.jetpack.paging3.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import okhttp3.Dispatcher
import java.lang.RuntimeException
import kotlin.system.measureTimeMillis

/**
 *  Flow数据流
 */
data class FlowModel(
    var id: Int
)

suspend fun main() {
//    builderFlow()
//    changeFlowThread()
//    cancelFlow()
//    exceptionOfFlow()
    retryFlow()
}

/**
 *  创建Flow
 */
suspend fun builderFlow() {
    flow {
        for (i in 1..5) {
            delay(100)
            emit(i)
        }
    }.collect {
        println(it)
    }

}

suspend fun builderFlowOf() {
    flowOf(1, 2, 3, 4, 5).onEach {
        delay(100)
    }.collect {
        println(it)
    }
}

suspend fun builderAsFlow() {
    listOf(1, 2, 3, 4, 5).asFlow().onEach {
        delay(100)
    }.collect {
        println(it)
    }
}

/**
 *  flow是冷流，在没有切换线程的情况下，生产者和消费者是同步非阻塞的
 *  channelFlow是热流， 实现了生产者和消费者异步非阻塞模型
 *  未切换线程的情况下，channelFlow执行效率更高
 */
suspend fun builderChannelFlow() {
    channelFlow {
        for (i in 1..5) {
            delay(100)
            send(i)
        }
    }.collect{
        println(it)
    }

}

/**
 *  Flow切换线程
 *  只影响flow{}代码块和map的线程,即生产者线程
 *  而collect所在的线程要看当前代码处于那个协程域中
 */
suspend fun changeFlowThread() {
    flow {
        for (i in 1..5) {
            delay(100)
            emit(i)
        }
    }.map {
        it * it
    }.flowOn(Dispatchers.IO).collect {
        println(it)
    }
}

/**
 *  取消Flow
 *  如果 flow 是在一个挂起函数内被挂起了，那么 flow 是可以被取消的，否则不能取消。
 */
suspend fun cancelFlow() {
    withTimeoutOrNull(2500) {
        flow {
            for (i in 1..5) {
                delay(1000)
                emit(i)
            }
        }.collect {
            println(it)
        }
    }
}

/**
 *  Flow异常处理
 *  catch操作符只能捕获上游的异常,不影响下游，如果onCompletion在catch后面，则onCompletion不打印异常
 *  onCompletion只能判断是否有异常，不能捕获
 */
suspend fun exceptionOfFlow() {
    flow {
        emit(1)
        throw RuntimeException()
    }.onCompletion { cause ->
        if (cause != null) {
            println("Flow completed exceptionally")
        } else {
            println("Done")
        }
    }.catch {
        println("catch exception")
    }.collect {
        println(it)
    }
}

/**
 *  Flow重试
 *  如果上游遇到了异常，并使用了retry操作符，则retry会让flow重试retries指定到次数
 */
suspend fun retryFlow() {
    (1..5).asFlow().onEach {
        if (it == 3) throw RuntimeException("Error on $it")
    }.retry(2) {
        if (it is RuntimeException) {
            return@retry true
        }
        false
    }.onEach {
        println("Emitting $it")
    }.catch {
        it.printStackTrace()
    }.collect()
}

/**
 *  Flow监听生命周期
 */
suspend fun lifecycleFlow() {
    (1..5).asFlow().onEach {
        if (it == 3) throw RuntimeException("Error on $it")
    }.onStart {
        println("Starting Flow")
    }.onEach {
        println("Emitting $it")
    }.catch {
        it.printStackTrace()
    }.onCompletion {
        println("Flow Completed")
    }.collect()
}

/**
 *  Flow并发
 */
suspend fun bufferFlow() {
    val time = measureTimeMillis {
        flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }.buffer().collect {
            delay(300)
            println(it)
        }
    }

    println("Collect time is $time ms")
}




