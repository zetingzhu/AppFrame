package com.mykotiln

/**

如果一个类要被继承，可以使用 open 关键字进行修饰。

 */
open class BaseDao {
    var id: Int? = 0 ;

    /**
    在基类中，使用fun声明函数时，此函数默认为final修饰，不能被子类重写。如果允许子类重写该函数，那么就要手动添加 open 修饰它, 子类重写方法使用 override 关键词：
     */
    open fun dao(){
        println("这是需要重写的方法")
    }
}