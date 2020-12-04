package frame.zzt.com.appframe.intentservice;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

/**
 * @author: zeting
 * @date: 2020/12/4
 *  间隔时间不能小于15分钟，不然将不会执行
 *
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService  extends JobService {

    private String TAG = MyJobIntentService.class.getSimpleName();
    public static final int MYJOBSERVICE_JOB_ID = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.w(TAG, "MyJobService onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "MyJobService destroyed.");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.w(TAG, "MyJobService onStartJob()");

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.w(TAG, "MyJobService stopped & wait to restart params:" + params);
        return true;
    }


}
