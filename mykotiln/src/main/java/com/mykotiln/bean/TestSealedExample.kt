package com.mykotiln.bean

/**
 *  密封类的创建
 * Created by zeting
 * Date 18/12/3.
 */
sealed class TestSealedExample {
    data class User(var name: String , var age: Int ): TestSealedExample()

    object user1 : TestSealedExample()// 单例模式

}

// 其子类可以定在密封类外部，但是必须在同一文件中 v1.1之前只能定义在密封类内部
object user2 : TestSealedExample()