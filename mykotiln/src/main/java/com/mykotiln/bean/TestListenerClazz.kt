package com.mykotiln.bean

/**
 *
 * Created by zeting
 * Date 18/12/3.
 */
class TestListenerClazz {

    lateinit private var listener : TestOnClickListener

    fun setOnClickListener(listener: TestOnClickListener){
        this.listener = listener
    }

    fun testListener(){
        listener.onItemClick("我是匿名内部类的测试方法")
    }
}