package com.mykotiln.bean

/**
 *
 * Created by zeting
 * Date 18/12/3.
 */
class TestAbstractA : TestAbatractLanauage() {
    override fun init() {
        println("我是$name")
    }

    override var name: String
        get() = "qqqqqqqqqqqq"
        set(value) {}
}