package com.example.kotlindemo.study

class Student(val sno: String, val grade: Int, name: String, age: Int) :
    Person(name, age), Study {

    override fun readBooks() {
        println(name + " is reading")
    }


    override fun doHomework(): String {
        println(name + " is doing homework")
        return ""
    }

//    init {
//    println("sno is " + sno)
//    println("grade is " + grade)
//    }

    constructor(name: String, age: Int) : this("", 0, name, age) {

    }

    constructor() : this("", 0) {

    }

    fun test() {

    }

}

fun main() {
    var student = Student("9527", 3, "Jack", 15)
    doStudy(student)
}

fun doStudy(study: Study) {
    study.readBooks()
    study.doHomework()
}