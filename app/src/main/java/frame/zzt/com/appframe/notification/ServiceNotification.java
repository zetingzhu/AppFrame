package frame.zzt.com.appframe.notification;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by zeting
 * Date 19/2/14.
 */

public class ServiceNotification extends Service {

    private final static String TAG = ServiceNotification.class.getSimpleName();

    private IBinder iBinder = new ServiceNotification.MyNorifyBinder();

    public class MyNorifyBinder extends Binder {
        public ServiceNotification getService() {
            return ServiceNotification.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG , "---onBind---" ) ;
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG , "---onCreate---" ) ;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG , "---onStartCommand---" ) ;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG , "---onUnbind---" ) ;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG , "---onDestroy---" ) ;
    }





}
