package com.mykotiln.bean

/**
 *
 * Created by zeting
 * Date 18/11/30.
 */
class TestClassC : TestClassA() , TestInterfaceB {
    override fun test1() {
        super<TestClassA>.test1()
        super<TestInterfaceB>.test1()
    }

    override fun test2() {
        super<TestClassA>.test2()
        super<TestInterfaceB>.test2()
    }
}