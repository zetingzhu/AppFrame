package com.mykotiln.activity

import android.content.Context
import android.util.Log

/**
 *  persenter 基类
 */
open class BasePersenter {

    /**
     *
    //对象获取
    person.javaClass// javaClass
    person::class.java // javaClass
    //类获取
    Person::class// kClass
    person.javaClass.kotlin// kClass
    (Person::class as Any).javaClass// javaClass
    Person::class.java // javaClass

    kotlin中有一个自己的Class叫做KClass

     */
    val TAG: String = KotilnPersenter::class.java.simpleName!!
    val TAG1: String = this.javaClass.simpleName

    var mContext: Context? = null
    var mToast: MyMessageUtil? = null;

    constructor()

    constructor(mContext: Context?) {
        this.mContext = mContext
        mToast = MyMessageUtil.getInstance(mContext!!)
    }


    fun showToast(msg: Any?) {
        showToastI(msg.toString())
    }

    fun showToastD(msg: String) {
        if (mToast != null) {
            Log.d(TAG, msg)
            mToast!!.setMessage(msg)
        }
    }

    fun showToastA(message: Any?) {
        if (mToast != null) {
            Log.i(TAG, message.toString())
            mToast!!.setMessage(message.toString())
        }
    }

    fun showToastI(msg: String) {
        if (mToast != null) {
            Log.i(TAG, msg)
            mToast!!.setMessage(msg)
        }
    }

    fun showToastW(msg: String) {
        if (mToast != null) {
            Log.w(TAG, msg)
            mToast!!.setMessage(msg)
        }
    }

    fun showToastE(msg: String) {
        if (mToast != null) {
            Log.e(TAG, msg)
            mToast!!.setMessage(msg)
        }
    }

    fun showToast(msg: Int?) {
        if (mToast != null) {
            Log.i(TAG, msg.toString())
            mToast!!.setMessage(msg.toString())
        }
    }
}