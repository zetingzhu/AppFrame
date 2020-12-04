package frame.zzt.com.appframe.intentservice;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.JobIntentService;

import frame.zzt.com.appframe.R;

public class ActivityIntentService extends AppCompatActivity {

    MyIntentService intentService1;
    JobIntentService jobIntentService;
    JobScheduler jobScheduler;
    JobService jobService;
    HandlerThread handlerThread;

    LinearLayout ll_service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        ll_service = findViewById(R.id.ll_service);
//        initIntentService() ;
//        initJobIntentService();
        initJobService();
    }

    private void initJobService() {
        addButton("启动 JobService", v -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                ComponentName myJobServiceComponentName = new ComponentName(this, MyJobService.class);
                JobInfo.Builder builder = new JobInfo.Builder(MyJobService.MYJOBSERVICE_JOB_ID, myJobServiceComponentName);
                /**
                 * 设置截止日期，即最大调度延迟。 即使没有满足其他要求，该工作也将在此截止日期前完成。
                 * 因为在定期作业上设置此属性没有意义，所以这样做会在调用build（）时抛出IllegalArgumentException。
                 * 注意：即便其他条件没满足此期限到了也要立即执行
                 */
//                builder.setOverrideDeadline(1000);
                /**
                 * 指定此作业应延迟提供的时间量。因为在定期作业上设置此属性没有意义，
                 * 所以这样做会在调用build（）时抛出IllegalArgumentException。
                 */
//                builder.setMinimumLatency(5 * 1000);
                /**
                 * 设置是否在设备重新启动后保留此作业。 需要RECEIVE_BOOT_COMPLETED权限。
                 */
//                builder.setPersisted(true);
                /**
                 * initialBackoffMillis：作业失败时最初等待的毫秒时间间隔 ；
                 * backoffPolicy：BACKOFF_POLICY_LINEAR or BACKOFF_POLICY_EXPONENTIAL.
                 * 设置后退/重试策略。
                 */
//                builder.setBackoffCriteria(10000, JobInfo.BACKOFF_POLICY_LINEAR);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    builder.setPeriodic(1000, 60 * 1000L);
                } else {
                    builder.setPeriodic(1000);
                }
                PersistableBundle mBundle = new PersistableBundle();
                mBundle.putString("JobService", "MyJobService");
                builder.setExtras(mBundle);

                JobInfo myJob = builder.build();
                JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);


                scheduler.schedule(myJob);
            }
        });
    }


    public void addButton(String text, View.OnClickListener onClickListener) {
        if (ll_service != null) {
            Button button = new Button(ActivityIntentService.this);
            button.setText(text);
            button.setOnClickListener(onClickListener);
            ll_service.addView(button);
        }
    }


    void initIntentService() {
        // 创建1
        Intent intent1 = new Intent(this, MyIntentService.class);
        intent1.setAction(MyIntentService.ACTION_1);
        startService(intent1);
        // 创建2
        Intent intent2 = new Intent(this, MyIntentService.class);
        intent2.setAction(MyIntentService.ACTION_2);
        startService(intent2);
        // 再次启动
        startService(intent1);
    }


    void initJobIntentService() {

        Intent intent3 = new Intent();
        intent3.setAction(MyIntentService.ACTION_1);
        intent3.putExtra("jobService", "Job 1");
        MyJobIntentService.enqueueWork(this, intent3);
        Intent intent4 = new Intent();
        intent4.setAction(MyIntentService.ACTION_2);
        intent4.putExtra("jobService", "Job 2");
        MyJobIntentService.enqueueWork(this, intent4);

        intent3.putExtra("jobService", "Job 3");
        MyJobIntentService.enqueueWork(this, intent3);

    }

}

