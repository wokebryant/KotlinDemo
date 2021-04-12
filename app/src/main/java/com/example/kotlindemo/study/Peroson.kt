package com.example.kotlindemo.study


open class Person(var name: String, var age: Int) {
//    var name = ""
//    var age = 0

    fun eat() {
        println(" is eating. He is " + age + " years old.")
    }

}

fun main() {
    val p = Person("Lily", 22)
//    p.name = "Lily"
//    p.age = 22
    p.eat()
}

fun isLeapYear(year: String) = if (year == null) false else {


}

fun getAnimalName() {
    val name = "animal"
}






