package com.example.kotlindemo.study

import com.example.kotlindemo.jetpack.paging3.Repo
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import okhttp3.Dispatcher
import java.lang.RuntimeException
import kotlin.concurrent.thread
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
//    retryFlow()
    debounceFlow()
//    sampleFlow()
//    reduceFlow()
//    foldFlow()
//    flatMapConcatFlow()
//    flatMapMergeFlow()
//    flatMapLatestFlow()
//    zipFlow()
//    bufferFlow2()
//    conflateFlow()
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

/**
 * debounce操作符
 * 用来确保flow的各项数据之间存在一定的时间间隔，如果时间点过于临近的数据只会保留最后一条
 * 如示例：只有两条数据之间间隔超过500ms才能发送成功
 */
@OptIn(FlowPreview::class)
suspend fun debounceFlow() {
    flow {
        emit(1)
        emit(2)
        delay(600)
        emit(3)
        delay(100)
        emit(4)
        delay(100)
        emit(5)
    }
        .debounce(500)
        .collect {
            println(it)
        }
}

/**
 *  对数据进行采样
 *  如示例：无限发送弹幕，但是间隔一秒才取一条弹幕进行发送
 */
@OptIn(FlowPreview::class)
suspend fun sampleFlow() {
    flow {
        while (true) {
            emit("发送一条弹幕")
        }
    }
        .sample(1000)
        .flowOn(Dispatchers.IO)
        .collect {
            println(it)
        }
}

/**
 * 终端操作符
 * reduce函数会通过参数传递一个Flow的累计值和FLow的当前值，我们可以在函数体中对参数进行运算，运算后的值会作为下一个累计值
 * 继续传递到reduce函数中
 * 如示例：计算1到100的累加结果
 * !!!!reduce只能作为flow的最后一个操作符，后面不能追加别的操作符
 */
suspend fun reduceFlow() {
    val result = flow {
        for (i in 1..100) {
            emit(i)
        }
    }
        .reduce {accumulator, value ->
            accumulator + value
        }
    println(result)
}

/**
 * 终端操作符
 * 和reduce函数类似，区别在于fold函数需要传递一个参数，作为初始值
 * 如示例：将字母A-Z进行拼接
 */
suspend fun foldFlow() {
    val result = flow {
        for (i in 'A'..'Z') {
            emit(i.toString())
        }
    }
        .fold("字母: ") { acc, value -> acc + value }
    println(result)
}

/**
 * 将flow中的数据进行映射，合并，压平成一个flow，最后进行输出
 * 适用于请求一个网络资源时需要依赖于先去请求另一个网络资源
 * !!! 一定保证顺序执行
 */
@OptIn(FlowPreview::class)
suspend fun flatMapConcatFlow() {
    flowOf(1, 2, 3)
        .flatMapConcat {
            flowOf("a$it", "b$it")
        }
        .collect {
            println(it)
        }
}

/**
 * 和flatMapConcat功能基本一致，区别在于flatMapMerge，只是合并多个Flow的数据
 * 但不保证顺序执行，而是并发执行
 * 如示例：谁延时少，谁执行的快
 */
@OptIn(FlowPreview::class)
suspend fun flatMapMergeFlow() {
    flowOf(300, 200, 100)
        .flatMapMerge {
            flow {
                delay(it.toLong())
                emit("a$it")
                emit("b$it")
            }
        }
        .collect {
            println(it)
        }
}

/**
 * 如果上一个flow的数据要发送但是下一个flow的数据还没有处理完毕，、
 * 则会直接将下一个flow的逻辑取消，处理上一个flow发送的数据
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun flatMapLatestFlow() {
    flow {
        emit(1)
        delay(150)
        emit(2)
        delay(50)
        emit(3)
    }.flatMapLatest {
        flow {
            delay(100)
            emit("$it")
        }
    }.collect {
        println(it)
    }
}

/**
 * 使用zip连接两个flow,它们之间是并行的关系，和flatMap差异很大
 * 由于flow1数量和flow2数量不对等，因此，flow2的4，5数据将会被舍弃
 * 适用于两个没有依赖关系的耗时操作，但需要同时将两个耗时操作但结果展示出来的场景
 */
suspend fun zipFlow() {
    val flow1 = flowOf("a", "b")
    val flow2 = flowOf(1, 2, 3, 4, 5)
    flow1.zip(flow2) { a, b ->
        a + b
    }.collect {
        println(it)
    }
}

/**
 * flow背压策略，当flow流速不均时，提供一份缓存区，使得数据的发送和处理互不干扰
 * buffer使得数据发送和数据处理位于不同的协程作用域
 */
suspend fun bufferFlow2() {
    flow {
        emit(1)
        delay(1000)
        emit(2)
        delay(1000)
        emit(3)
    }
        .onEach { println("$it is ready") }
        .buffer()
        .collect {
            delay(1000)
            println("$it is handled")
        }
}

/**
 * 和buffer类似，区别在于buffer不会丢弃数据，而conflate会根据需求适当的丢弃数据
 * 和collectLatest的区别在于，collectLatest里如果数据没有处理完毕，下一个数据又进来了，那么当前处理的逻辑会被取消
 * 而conflate不会被取消
 * collectLatest适用场景是：可以舍弃正在执行的部分逻辑，只处理最新的数据
 * conflate适用场景是：不舍弃正在执行的逻辑，等数据处理完毕后，才处理最新接受到的数据，没接收到的(过期)数据被舍弃
 */
suspend fun conflateFlow() {
    flow {
        var count = 0
        while (true) {
            emit(count)
            delay(1000)
            count++
        }
    }.conflate()
    flow {
        var count = 0
        while (true) {
            emit(count)
            delay(1000)
            count ++
        }
    }
//        .collectLatest {
//            println("start handle $it")
//            delay(2000)
//            println("finish handle $it")
//        }
        .conflate()
        .collect {
            println("start handle $it")
            delay(2000)
            println("finish handle $it")
        }
}

/**
 * CallBackFlow
 * 将基于回调当Api包装成Flow，类似于挂起协程
 * https://www.cnblogs.com/joy99/p/15805962.html#%E4%B8%89callbackflow--%E5%B0%86%E5%9F%BA%E4%BA%8E%E5%9B%9E%E8%B0%83%E7%9A%84-api-%E8%BD%AC%E6%8D%A2%E4%B8%BA%E6%95%B0%E6%8D%AE%E6%B5%81
 */
suspend fun callbackFlow(): Flow<Any> = callbackFlow {
    // 注释的代码时请求网络操作
    // trySend 发送flow
//    val res = object: Result<String> {
//        override fun onSuccess(t: String) {
//            trySend(t)
//            close(Exception("completion"))
//        }
//
//        override fun onFail(msg: String) {
//        }
//    }

//    getApi(res)


    awaitClose {
        // 使用 awaitClose 来保持流运行
        // 流取消时会回调此方法
        // ......
    }
}




