package frame.zzt.com.appframe.intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author: zeting
 * @date: 2020/12/4
 */
public class MyIntentService extends IntentService {

    private static String serviceName = "abc";
    private String TAG = MyIntentService.class.getSimpleName();

    public static final String ACTION_1 = "com.zzt.test.MyIntentService1";
    public static final String ACTION_2 = "com.zzt.test.MyIntentService2";

    public MyIntentService() {
        super(serviceName);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            for (int i = 0; i < 5; i++) {
                Log.w(TAG, "MyIntentService service:" + serviceName + " ACTION:" + action + " i:" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
