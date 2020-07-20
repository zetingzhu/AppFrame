
package frame.zzt.com.appframe.notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import java.util.HashMap;

import frame.zzt.com.appframe.MyApplication;
import frame.zzt.com.appframe.rxbus.EventMsg;
import frame.zzt.com.appframe.rxbus.RxBusTwo;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * This class will receive and process all notifications.
 */
public class NotificationReceiver18 extends NotificationListenerService 
        implements Handler.Callback {

    private static final String TAG = "NotificationReceiver18";

    // Messages
    private static final int MSG_NOTIFICATION_POSTED = 1;
    private static final int MSG_NOTIFICATION_REMOVED = 2;

    private final Context mContext = MyApplication.getInstance()
                                    .getApplicationContext();

    // Notification
    private HashMap<String, StatusBarNotification> mNotificationMap;

    private Handler mHandler;

    StatusBarNotification mySbn ;

//    private MyBinder mBinder = new MyBinder();

    MyBroadcast mBroadcast ;
    public static final String BROADCAST_ACTION = "frame.zzt.com.appframe.MyApplication.NotificationReceiver18" ;

    public NotificationReceiver18() {
        Log.d(TAG, "NotificationReceiver18 created!");

        HandlerThread thread = new HandlerThread("NotificationHandler");
        thread.start();
        this.mHandler = new Handler(thread.getLooper(), this);

        if (mNotificationMap == null) {
            mNotificationMap = new HashMap<String, StatusBarNotification>();
        }

        mBroadcast = new MyBroadcast();




    }

    @Override
    public void onCreate() {
        super.onCreate();

        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(BROADCAST_ACTION);
        registerReceiver(mBroadcast,mIntentFilter);

        Disposable register = RxBusTwo.getInstance().register( EventMsg.class , new Consumer<EventMsg>() {
            @Override
            public void accept(EventMsg eventMsg) throws Exception {
                /**这个地方获取到数据。并执行相应的操作*/
                if (eventMsg != null) {
                    Log.i(TAG, "通过 RxBus 获取到的数据：" + eventMsg.toString() );
                    int index = eventMsg.getIndex() ;
                    getActionIndex(index);
                }
            }
        });
        //TODO 这个地方必须添加注册
        RxBusTwo.getInstance().addSubscription(mContext,register);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBusTwo.getInstance().unSubscribe( mContext );
    }

    // IBinder是远程对象的基本接口，是为高性能而设计的轻量级远程调用机制的核心部分。但它不仅用于远程
    // 调用，也用于进程内调用。这个接口定义了与远程对象交互的协议。
    // 不要直接实现这个接口，而应该从Binder派生。
    // Binder类已实现了IBinder接口

//    public class MyBinder extends android.os.Binder{
//        /** * 获取Service的方法 * @return 返回PlayerService */
//        public NotificationReceiver18 getService() {
//            return NotificationReceiver18.this;
//        }
//    }


//    @Override
//    public IBinder onBind(Intent intent) {
//        return mBinder ;
//    }


    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind()");
        return false;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        Log.i(TAG, "=> Notification Posted, " + "ID: " + sbn.getId()
                + ", Package: " + sbn.getPackageName());
        this.mHandler.obtainMessage(MSG_NOTIFICATION_POSTED, sbn).sendToTarget();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Log.i(TAG, "=> Notification Removed, " + "ID: " + sbn.getId()
                + ", Package: " + sbn.getPackageName());
        this.mHandler.obtainMessage(MSG_NOTIFICATION_REMOVED, sbn).sendToTarget();
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
    }

    private String getNotificationMapKey(StatusBarNotification sbn) {
        return getNotificationMapKey(sbn.getPackageName(), sbn.getTag(), sbn.getId());
    }

    private String getNotificationMapKey(String pkg, String tag, int id) {
        return pkg + "." + tag + "." + String.valueOf(id);
    }

    /**
     *  添加广播
     * @param sbn
     */
    private void handleNotificationPosted(StatusBarNotification sbn) {
        CharSequence pkgName = sbn.getPackageName();
        Log.w(TAG, "handleNotificationPosted  pkgName:" + pkgName );
        if ( pkgName.equals("frame.zzt.com.appframe")  ){
            this.mySbn = sbn ;
        }
    }



    /**
     *  移除广播
     * @param sbn
     */
    private void handleNotificationRemoved(StatusBarNotification sbn) {

    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
        case MSG_NOTIFICATION_POSTED:
            handleNotificationPosted((StatusBarNotification) msg.obj);
            break;
        case MSG_NOTIFICATION_REMOVED:
            handleNotificationRemoved((StatusBarNotification) msg.obj);
            break;
        default:
            return false;
        }

        return true;
    }


    public ServiceInterface getServiceInterface(){
        return new ServiceInterface() {
            @Override
            public void skipAction(int index) {
                getActionIndex(index);
            }
        };
    }

    /**
     * 跳转对应的action
     * @param index
     */
    public void getActionIndex(int index) {
        if (mySbn != null) {
           int count =  NotificationCompat.getActionCount(mySbn.getNotification());
            if (count >0){
                try {
                    NotificationCompat.Action action = NotificationCompat.getAction(mySbn.getNotification(), index);
                    if (action != null) {
                        action.actionIntent.send();
                    }
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }else {
                Log.i(TAG , "在广播中没有action 按钮");
            }
        }else {
            Log.i(TAG , "应用没有广播");
        }
    }

    public interface ServiceInterface {
        void skipAction(int index );
    }


    class MyBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG , "接收到的广播信息：" + intent.getAction() );
            if (BROADCAST_ACTION.equals(intent.getAction())){
                int index = intent.getIntExtra("index" , 0);
                getActionIndex(index);
            }
        }
    }

}
