package com.mykotiln.bean

/**
 *
 * Created by zeting
 * Date 18/12/3.
 */
class TestClazzD(override val num1: Int, override val num2: Int) : TestInterfaceD {

    override val num3: Int
        get() = super.num3

    override val num4: Int = 4

    fun sum() : Int{
        return num1 + num2
    }
}