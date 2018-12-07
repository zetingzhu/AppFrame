package com.mykotiln.bean

import android.util.Log

/**
 *
 * Created by zeting
 * Date 18/11/30.
 */
class TestBean2 constructor(var id : Int = 666 ) {

    val TAG : String = TestBean2::class.java.simpleName!!

    init {
        Log.i(TAG , "这是 ${TAG} 一个init代码块 ，这个代码块的id值： $id ")
    }



}