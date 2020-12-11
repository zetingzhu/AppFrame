package com.example.work

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_main)

        initView()

        testWork()

        repeatWork()
    }

    private fun repeatWork() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // 需要联网
                .build()
        val checkDisk = PeriodicWorkRequestBuilder<RepeatWorker>(
                PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(applicationContext).enqueue(checkDisk)
    }

    private fun testWork() {
        val checkNetwork = OneTimeWorkRequestBuilder<CheckNetworkWorker>()
                .setInputData(Data.Builder().let {
                    // Data以Builder的方式进行构建，传入的是键值对
                    it.putString("operator", "Owen")
                    it.putString("description", "Check network state")
                    it.build()
                })
                .build()

        WorkManager.getInstance(applicationContext)
                .getWorkInfoByIdLiveData(checkNetwork.id)
                // observe方法是添加观察者，这个方法有两个参数，第一个参数是LifecycleOwner，可以传入
                .observe(this, object : Observer<WorkInfo> {
                    override fun onChanged(t: WorkInfo?) {
                        // 任务执行完毕之后，会在这里获取到返回的结果
                        if (t?.state == WorkInfo.State.SUCCEEDED) {
                            for ((key, value) in t.outputData!!.keyValueMap) {
                                Log.d("TEST", "Out Data $key ---- $value")
                            }
                        }
                    }
                })
        WorkManager.getInstance(applicationContext).enqueue(checkNetwork)
    }

    private fun initView() {

        // 一次性任务
        val checkSystem = OneTimeWorkRequestBuilder<CheckSystemWorker>().build()
        WorkManager.getInstance(applicationContext).enqueue(checkSystem)

        val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED) // 需要联网
                .build()

        val checkSystem1 = OneTimeWorkRequestBuilder<CheckSystemWorker>()
                .setInitialDelay(3000, TimeUnit.MILLISECONDS)//设定最短初始延迟时间
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(applicationContext).enqueue(checkSystem1)


        // 周期任务


    }
}