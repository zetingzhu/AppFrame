package com.mykotiln.bean

import android.util.Log

/**
 *
 * Created by zeting
 * Date 18/11/30.
 */
class TestBean1 constructor(var id : Int ) {

    val TAG : String = TestBean1::class.simpleName!!

    init {
        id = -1
        Log.i(TAG , "这是一个init代码块 ，这个代码块的id值： $id ")
    }



}