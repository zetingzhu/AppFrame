package com.mykotiln.activity

import android.content.Context
import android.widget.Toast

/**
 * Created by allen on 18/10/10.
 */

open class BasePersenter{
    var mContext: Context? = null
    var mToast : MyMessageUtil? = null ;

    constructor()

    constructor(mContext: Context?){
        this.mContext = mContext
        mToast = MyMessageUtil.getInstance(mContext!!)
    }

    fun showToast(msg : String ){
        if (mToast != null ) {
            println(msg)
            mToast!!.setMessage(msg)
        }
    }

    fun showToast(msg : Int? ){
        if (mToast != null ) {
            println(msg)
            mToast!!.setMessage(msg.toString() )
        }
    }
}