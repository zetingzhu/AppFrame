package frame.zzt.com.appframe.intentservice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

/**
 * @author: zeting
 * @date: 2020/12/4
 */
public class MyJobIntentService extends JobIntentService {
    private String TAG = MyJobIntentService.class.getSimpleName();
    static final int JOB_ID = 10111;

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.w(TAG, "MyJobIntentService  启动成功");
        if (intent != null) {
            String jobService = intent.getStringExtra("jobService");
            String action = intent.getAction();
            for (int i = 0; i < 5; i++) {
                Log.w(TAG, "MyJobIntentService  ACTION:" + action + " - jobService:" + jobService + " i:" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, MyJobIntentService.class, JOB_ID, work);
    }

}
