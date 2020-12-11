package com.example.work

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * @author: zeting
 * @date: 2020/12/7
 */
object WorkerUtil {
    private fun repeatWork(context: Context) {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 需要联网
                .build()
        val checkDisk = PeriodicWorkRequestBuilder<RepeatWorker>(
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(context.applicationContext).enqueue(checkDisk)
    }
}