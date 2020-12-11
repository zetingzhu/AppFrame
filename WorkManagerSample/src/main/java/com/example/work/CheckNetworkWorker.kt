package com.example.work

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

// 定义Worker
class CheckNetworkWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.e("TEST", "Checking network。。。。。。。。")
        Log.d("TEST", "Checking network, params......")
        for ((key, value) in inputData.keyValueMap) {
            Log.d("TEST", "$key ---- $value")
        }
        Thread.sleep(3000)
        Log.e("TEST", "Checking network done.")
        return Result.success(Data.Builder().let {
            it.putInt("code", 200)
            it.putString("msg", "Network is fine")
            it.build()
        })
    }
}
