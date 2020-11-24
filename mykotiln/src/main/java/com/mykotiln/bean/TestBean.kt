package com.mykotiln.bean

import android.util.Log

/**
 *
 * Created by zeting
 * Date 18/11/30.
 */
class TestBean constructor(var id: Int) {

    val TAG: String = TestBean::class.java.simpleName!!

    init {
        id = 12345
        Log.i(TAG, "这是 ${TAG} 一个init代码块 ，这个代码块的id值： $id ")
    }


    constructor(id: Int, title: String) : this(id) {
        Log.i(TAG, "这是 ${TAG} 第二个构造函数 ，id： $id title: $title ")
    }

    constructor(id: Int, title: String = "TTT", value: String = "VVV") : this(id) {
        Log.i(TAG, "这是 ${TAG} 第二个构造函数 ，id： $id title: $title ")
    }

}