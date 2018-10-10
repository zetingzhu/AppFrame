package com.mykotiln

import android.app.Activity

/**
 * Created by allen on 18/9/21.
 */

class DemoIn() : BaseDao() {
    /** 上面的是类的主构造函数 */
    /** 下面是类的成员属性 */

    var title: Int? = null
    var desc: Int? = null
    var demoClass: Class<out Activity>? = null

    /**
     * 第一个构造函数
     */
    constructor (title: Int?) : this() {
        this.title = title
    }

    /**
     * 第二个构造函数
     */
    constructor (title: Int?, desc: Int?, demoClass: Class<out Activity>?) : this(title) {
        this.desc = desc
        this.demoClass = demoClass
    }


    /**
     *  设置 set get 方法
     */
    var lastName: String = "zhang"
        get() = field.toUpperCase()   // 将变量赋值后转换为大写
        set

    var no: Int = 100
        get() = field                // 后端变量
        set(value) {
            if (value < 10) {       // 如果传入的值小于 10 返回该值
                field = value
            } else {
                field = -1         // 如果传入的值大于等于 10 返回 -1
            }
        }

    var heiht: Float = 145.4f
        private set

    /**
    getter 和 setter
    属性声明的完整语法：
    var <propertyName>[: <PropertyType>] [= <property_initializer>]
    [<getter>]
    [<setter>]

    val不允许设置setter函数，因为它是只读的。
    var allByDefault: Int? // 错误: 需要一个初始化语句, 默认实现了 getter 和 setter 方法
    var initialized = 1    // 类型为 Int, 默认实现了 getter 和 setter
    val simple: Int?       // 类型为 Int ，默认实现 getter ，但必须在构造函数中初始化
    val inferredType = 1   // 类型为 Int 类型,默认实现 getter
     */


    /**
     * 重写父类的方法
     */
    override fun dao() {
        /** 这里可以用泛型，指引需要调用哪个类的方法 */
        super<BaseDao>.dao()

    }
}
