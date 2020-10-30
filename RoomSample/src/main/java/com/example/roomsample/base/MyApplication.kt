package com.example.roomsample.base

import com.example.roomsample.database.AppDatabase
import com.zzt.commonmodule.base.BaseApplication

/**
 * @author: zeting
 * @date: 2020/9/17
 */
class MyApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()

        AppDatabase.getInstance(baseContext)
    }
}