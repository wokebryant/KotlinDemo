package com.example.kotlindemo.designmode.chain

/**
 *  责任链（链表）
 */
abstract class InterceptChain<T> {

    var next: InterceptChain<T>? = null

    open fun intercept(data: T) {
        next?.intercept(data)
    }

}


class InterceptChainHandle<T> {

    private var _interceptFirst: InterceptChain<T>? = null

    fun add(interceptChain: InterceptChain<T>) {
        if (_interceptFirst == null) {
            _interceptFirst = interceptChain
            return
        }

        var node = _interceptFirst
        while (true) {
            if (node?.next == null) {
                node?.next = interceptChain
                break
            }

            node = node.next
        }
    }

    fun intercept(data: T) {
        _interceptFirst?.intercept(data)
    }

}

fun main() {
    val intercepts = InterceptChainHandle<String>()
    intercepts.add(OneIntercept())
    intercepts.add(TwoIntercept())
    intercepts.intercept("测试拦截器")
}

class OneIntercept : InterceptChain<String>() {
    override fun intercept(data: String) {
        val newData = "$data: OneIntercept"
        println(newData)
        super.intercept(newData)
    }

}

class TwoIntercept : InterceptChain<String>() {
    override fun intercept(data: String) {
        val newData = "$data: OneIntercept"
        println(newData)
        super.intercept(newData)
    }

}