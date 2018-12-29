package frame.zzt.com.appframe;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.util.Log;

import frame.zzt.com.appframe.Notification.NotificationReceiver18;
import frame.zzt.com.appframe.greenUtil.MyGreenUtil;
import frame.zzt.com.appframe.greendao.DaoSession;

/**
 * Created by allen on 18/8/7.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    private static MyApplication mInstance;
    MyGreenUtil mMyGreenUtil ;
    Handler mWorkHandler = null; // 用于向工作线程发送执行代码
    HandlerThread mWorkThread = null; // 工作线程处理耗时操作,防止在主线程中执行界面卡

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mMyGreenUtil = new MyGreenUtil() ;

        /**
         被杀后再次启动

         该方法使用前提是 NotificationListenerService 已经被用户授予了权限，否则无效。
         幸运的是，官方也已经发现了这个问题，在 API 24 中提供了 requestRebind(ComponentName componentName) 方法来支持重新绑定。
         */
        String strListener = Settings.Secure.getString(this.getContentResolver(),
                "enabled_notification_listeners");
        Log.i(TAG, "strListener = " + strListener);
        if (strListener != null
                && strListener
                .contains("com.mediatek.swp/com.mediatek.app.notification.NotificationReceiver18")) {
            ComponentName localComponentName = new ComponentName(this, NotificationReceiver18.class);
            PackageManager localPackageManager = this.getPackageManager();
            localPackageManager.setComponentEnabledSetting(localComponentName, 2, 1);
            localPackageManager.setComponentEnabledSetting(localComponentName, 1, 1);
            Log.i(TAG, "setComponentEnabledSetting");
        }

    }

    public DaoSession getDaoSession() {
        return mMyGreenUtil.getDaoSession(getApplicationContext());
    }


    /**
     * 工作线程
     *
     * @param r
     * @return
     */
    public boolean postWorkRunnable(Runnable r) {
        if (mWorkHandler == null) {
            mWorkHandler = new Handler(getWorkLooper());
        }
        return mWorkHandler.post(r);
    }

    /**
     * 获取Looper实现自定义Handler,在指定线程(非当前线程)处理消息 获取工作线程Looper,注:获取主线程Looper请调用
     * getMainLooper()
     */
    public Looper getWorkLooper() {
        if (mWorkThread == null) { // 如果工作线程未开启,则开启工作线程
            mWorkThread = new HandlerThread("Rtcclient_WorkThread");
            mWorkThread.start();
        }
        return mWorkThread.getLooper();
    }
}
