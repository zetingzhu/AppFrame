package com.mykotiln.bean

/**
 *  接口中属性的实现
 * Created by zeting
 * Date 18/12/3.
 */
interface TestInterfaceD {
    val num1: Int

    val num2: Int

    // 声明比那俩和提供默认值
    // 注意： val num3: Int = 3  这种方式不提供，为直接报错的
    val num3: Int
        get() = 3

    val num4: Int
}