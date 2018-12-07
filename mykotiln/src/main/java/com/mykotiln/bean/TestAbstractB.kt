package com.mykotiln.bean

/**
 *
 * Created by zeting
 * Date 18/12/3.
 */
class TestAbstractB : TestAbstractLanauage() {
    override fun init() {
        println("我是$name")
    }

    override var name: String
        get() = "wwwwwwwwwwwww"
        set(value) {}
}