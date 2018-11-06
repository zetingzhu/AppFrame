package com.mykotiln.activity

import android.content.Context
import android.os.Handler
import android.os.Message
import android.widget.Toast

/**
 * Created by allen on 18/5/10.
 * 消息工具
 */

class MyMessageUtil private constructor(internal var mContext: Context) {

    internal var toast: Toast? = null
    internal var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    if (toast == null) {
                        toast = Toast.makeText(mContext, msg.obj.toString() + "", Toast.LENGTH_SHORT)
                    } else {
                        toast!!.setText(msg.obj.toString() + "")
                    }
                    toast!!.show()
                }
            }
        }
    }

    fun setMessage(strMessage: String) {
        val msg = Message()
        msg.what = 0
        msg.obj = strMessage
        mHandler.sendMessage(msg)
    }

    companion object {
        private var instance: MyMessageUtil? = null

        @Synchronized fun getInstance(mContext: Context): MyMessageUtil {
            if (instance == null) {
                instance = MyMessageUtil(mContext.applicationContext)
            }
            return instance
        }
    }
}
