package com.example.kotlindemo.study

/**
 *  数据类，class前面加上  data  字段声明
 */
data class CellPhone(val band: String, val price: Double)

fun main() {
    val cellPhone1 = CellPhone("xiaomi", 3999.00)
    val cellPhone2 = CellPhone("meizu", 4999.00)
    println(cellPhone1)
    println("cellPhone1 equals cellPhone2 " + (cellPhone1 == cellPhone2))
}