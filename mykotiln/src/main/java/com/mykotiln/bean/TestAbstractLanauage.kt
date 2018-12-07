package com.mykotiln.bean

/**
 * 声明一个抽象（类或函数）的关键字为：abstract
 * Created by zeting
 * Date 18/12/3.
 */
abstract class TestAbstractLanauage {
    val TAG = this.javaClass.simpleName  // 自身的属性

    // 自身的函数
    fun test() : Unit{
        // exp
    }
    abstract var name : String           // 抽象属性
    abstract fun init()                  // 抽象方法

}