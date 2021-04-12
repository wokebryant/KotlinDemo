package com.example.kotlindemo.study

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.UriMatcher
import android.net.Uri
import com.example.kotlindemo.activity.FirstActivity
import com.example.kotlindemo.activity.SecondActivity
import java.lang.Exception
import java.lang.IllegalArgumentException
import kotlin.reflect.KProperty

/**
 * kotlin标准函数
 *  with：用于连续调用对象的方法, 接受一个对象和lambda参数, lambda表达式最后一行作为返回值
 *  run: 类似于with函数，区别在于run函数不能单独调用，必须object.run()，只接受一个lambda参数
 *  apply: 类似run函数，区别在于返回值无法指定，而返回object本身
 */

fun withSample() {
    //normal code
    val list = listOf("apple", "orange", "banana", "pear", "grape")
    val builder = StringBuilder()
    builder.append("Start eating fruits.\n")
    for (fruit in list) {
        builder.append(fruit + "\n")
    }
    builder.append("Ate all fruits")
    val  result = builder.toString()
    print(result)

    //great code
    val list1 = listOf("apple", "orange", "banana", "pear", "grape")
    val results1 = with(StringBuilder()){
        append("Start eating fruits.\n")
        for (fruit in list1) {
            append(fruit)
        }
        append("Ate fruit end")
        toString()
    }
    print(results1)
}

fun runSample() {
    val list1 = listOf("apple", "orange", "banana", "pear", "grape")
    val results1 = StringBuilder().run{
        append("Start eating fruits.\n")
        for (fruit in list1) {
            append(fruit)
        }
        append("Ate fruit end")
        toString()
    }
    print(results1)
}

fun applySample() {
    val list1 = listOf("apple", "orange", "banana", "pear", "grape")
    val results1 = StringBuilder().apply{
        append("Start eating fruits.\n")
        for (fruit in list1) {
            append(fruit)
        }
        append("Ate fruit end")
    }
    print(results1.toString())

}

/**
 *  repeat函数， 重复动作
 */
fun repeatSmaple() {
    val  list = mutableListOf<String>("apple", "orange", "banana", "pear", "grape")
    val list1 = mutableListOf<String>()
    repeat(2) {
        list.add("juice")
    }
    for (fruit in list) {
        if ("juice" == fruit) list1.add(fruit)
    }
}


/**
 *  静态方法，加上@JvmStatic注解，可以被java代码识别为静态方法
 *  顶层方法，指任务没有定义在class中的方法，比如此file中的所有没有定义在class中的方法
 */
object StaticClass {

    @JvmStatic
    fun doAction() {

    }

}

/**
 *  kotlin匿名内部类的使用
 *
 */

//实现一个接口或类
interface AA {
    fun a()
}

fun test1(args: Array<String>) {

    val aa = object : AA {
        override fun a() {
            println("a invoked")
        }
    }

    aa.a()
}

//不实现任何接口和类，并且在匿名内部类中添加方法
fun test2(args: Array<String>) {

    val obj = object  {
        fun a() {
            println("a invoked")
        }
    }

    obj.a()  //打印：a invoked
}



/**
 *  kotlin中使用inner关键字定义内部类
 *  使用lateinit关键字 声明对变量延迟初始化，可以不用给变量赋值null
 *  ::object.isInitialized,判断是否初始化
 */
class Parent{
    //normal code
    private var child : Child? = null
    //great code
    private lateinit var child1: Child


    fun action() {
        if (!::child1.isInitialized) {
            child1 = Child()
        }
        child?.testInnerAction()
        child1.testInnerAction()
    }


    inner class Child{

        fun testInnerAction(){

        }

    }
}

/**
 *  kotlin密封类的使用, 加上sealed关键字，可以避免写无用的else分支
 *  密封类和其子类的定义必须处于kotlin file的顶层，不能嵌套在class类中
 */
sealed class  Result
class Success(val msg: String) : Result()
class Failure(val error: Exception) : Result()
class Unknow(val unknowInfo : String) : Result()

fun getResultMsg(result: Result) = when(result) {
    is Success -> result.msg
    is Failure -> result.error.message
    is Unknow -> result.unknowInfo
}


/**
 *  kotlin拓展函数的使用
 *  例如获取一个字符串中的字母个数
 *  向String类里面拓展一个lettersCount 函数
 */

//Normal
fun lettersCount0(str: String): Int {
    var count = 0
    for (char in str) {
        if (char.isLetter()) count++
    }
    return count
}

//Great
fun String.lettersCount(): Int {
    var count = 0
    for (char in this) {
        if (char.isLetter()) count++
    }
    return count
}


/**
 *  kotlin运算符重载
 *  可以实现对象和对象相加
 *  方法前加上operator修饰符, 运算符对应的方法名师固定的
 *  +：plus
 *  -: minus
 */

class Money(val value: Int) {

    //编辑时会被转换成money1.plus(money2)的形式
    operator fun plus(money: Money): Money {
        val sum = value + money.value
        return Money(sum)
    }

    operator fun plus(newValue: Int): Money {
        val sum = value + newValue
        return Money(sum)
    }
}

/**
 *  vararg 可变表示参数列表类型
 *  mapOf函数 "key to value"会返回一个Pair对象
 */
fun cvOf(vararg pairs: Pair<String, Any?>) = ContentValues().apply{
    for (pair in pairs) {
        val key = pair.first
        val value = pair.second
        when(value) {
            is Int -> put(key, value)
            is Long -> put(key, value)
            is Short -> put(key, value)
            null -> putNull(key)
        }
    }
}

/**
 *  kotlin泛型
 */

class MyClass {

    fun <T : Number> method(param: T): T {
        return param
    }
}


/**
 *  kotlin委托
 *  例如通过实现Set接口实现一个自定义的数据结构类，大部分待实现方法委托给HashSet
 */

class MySet<T> (val helperSet: HashSet<T>) : Set<T> by helperSet {

    fun helloWorld() = println("Hello World")

    override fun isEmpty() = false

}

/**
 *  自定义一个Lazy委托类
 *   operator 委托字段
 *   调用uriMather函数会自动触发getValue方法，执行lambda表达式，返回最后一行
 */

val uriMatcher by later {
    val mather = UriMatcher(UriMatcher.NO_MATCH)
    mather.addURI("", "1", 1)
    mather
}

fun <T> later(block: () -> T) = Later(block)

class Later<T>(val block: () -> T) {
    var value: Any? = null

    operator fun getValue(any: Any?, prop: KProperty<*>): T {
        if (value == null) {
            value = block()
        }
        return value as T
    }

}

/**
 *  kotlin使用infix函数(简化语法，省略.和())
 *  infix只能接受一个参数
 *  必须是成员函数或拓展函数
 */
fun testInfix() {
    val list = listOf("Apple", "Banana", "Orange", "Pear")
    //Normal
//    if(list.contains("Banana")) {
//        TODO()
//    }

    //Great
    if (list has "Banana") {
        TODO()
    }
}

infix fun <T> Collection<T>.has(element: T) = contains(element)


/**
 *  kotlin泛型实例化
 *  通过reified(实例化)关键字来修饰
 *  必须是内联函数
 *
 */

inline fun <reified T> getGenericType() = T::class.java

//优化activity启动
inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}

fun test(context: Context) {
    startActivity<FirstActivity>(context) {
        putExtra("params", "data")
    }
}


/**
 *  kotlin泛型的协变和裂变
 *  todo 比较难理解，最后学习
 */






fun main() {
    val count = "ABC123xyz!@#".lettersCount()
    "abc".reversed()    //kotlin自带的拓展函数

    val money1 = Money(5)
    val money2 = Money(10)
    val money3 = money1 + money2
    val money4 = money3 + 20
    println(money4.value)

    //泛型实例化测试
    val result1 = getGenericType<String>()
    val result2 = getGenericType<Int>()
    println("result1 is $result1")
    println("result2 is $result2")



}