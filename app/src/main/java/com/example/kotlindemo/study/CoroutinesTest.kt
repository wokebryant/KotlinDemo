package com.example.kotlindemo.study

import com.example.kotlindemo.utils.HttpUtil
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 *  kotlin协程
 */



fun main() {
//    createCoroutine()
//    createBlockCoroutine()
    createChildCoroutines()
}

const val TAG = "Coroutine"

/**
 *  创建顶级协程
 *  当应用程序结束之后协程也会结束，就算协程没运行完毕
 */
fun createCoroutine() {
    GlobalScope.launch {
        println("codes run in coroutine scope")
        delay(1500)
        println("codes run in coroutine scope finish")
    }
    Thread.sleep(1000)
}

/**
 *  runBlocking创建的协程作用域保证作用域内的代码在全部执行完之前阻塞当前线程
 */
fun createBlockCoroutine() {
    runBlocking {
        println("codes run in coroutine scope")
        delay(1500)
        println("codes run in coroutine scope finish")
    }
}

/**
 *  创建子协程
 */
fun createChildCoroutines() {
    val start = System.currentTimeMillis()
    runBlocking {
        repeat(100000) {
            launch {
                println(".")
                printDo()
            }
        }
    }
    val end = System.currentTimeMillis()
    println(end -start)
}

/**
 *  suspend关键字允许声明任何方法为挂起方法，挂起方法之间可以互相调用
 *  coroutineScope可以继承外部协程作用域并创建子作用域,会阻塞当前协程，不阻塞其他
 */
fun printDo() {
    runBlocking {
        coroutineScope {
            launch {
                delay(100)
            }
            launch {

            }
        }
    }

}

/**
 *  常用的协程构建
 */
fun createCommonScope() {
    val job = Job()
    val scope = CoroutineScope(job)
    scope.launch {

    }
    job.cancel()
}


/**
 *  获取协程返回值
 *  async关键字
 *  await关键阻塞当前协程
 */
fun getResult() {
    runBlocking {
        val result = async {
            5 + 5
        }
        val result2 = async {
            4 + 6
        }
        println("result is ${result.await() + result2.await()}")
    }
}

/**
 *  类似与async().await
 *  withContext参数为线程参数，高并发，低并发
 */
fun getResultWithContext() {
    runBlocking {
        val result = withContext(Dispatchers.Default) {
            5 + 5
        }
        println(result)
    }
}

/**
 *  协程优化网络请求回调
 *  suspendCoroutines关键字，在挂起函数或者协程作用域执行，将当前协程挂起
 *  lambda表达式代码在子线程(需要手动开启一个子线程)中运行，通过resume方法恢复协程（协程是在主线程中运行）
 */
suspend fun getBaiduResopnse() {
    try {
        val response = webRequest("https://www.baidu.com")
        //对返回数据进行处理
    } catch (e: Exception) {
    }
}

suspend fun webRequest(address: String): String{
    return suspendCoroutine {
        HttpUtil.sendHttpRequest(address, object : HttpUtil.HttpCallbackListener {
            override fun onFinish(response: String) {
                it.resume(response)
            }

            override fun onError(error: Exception) {
                it.resumeWithException(error)
            }
        })
    }
}