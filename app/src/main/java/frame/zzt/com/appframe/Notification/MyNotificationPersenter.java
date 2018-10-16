package frame.zzt.com.appframe.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;

import frame.zzt.com.appframe.Notification.bus.EventMsg;
import frame.zzt.com.appframe.Notification.bus.RxBus;
import frame.zzt.com.appframe.Notification.bus.RxBusTwo;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.login.LoginActivity;
import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by allen on 18/10/11.
 */
public class MyNotificationPersenter {

    private final static String TAG = ActivityNotification.class.getSimpleName() + "/" + MyNotificationPersenter.class.getSimpleName();

    public Context mContext;

    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mBuilder ;
    private Notification mNotification ;
    private MyNotificationView myNotificationView ;

    public MyNotificationPersenter(Context mContext , MyNotificationView myNotificationView) {
        this.mContext = mContext;
        this.myNotificationView = myNotificationView ;
        //获取NotificationManager实例
        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

    }

    /**
     *  一个简单的通知栏
     */
    public void showNotify1() {
        Bitmap mLargeIcon = getBitmapFromResource(mContext.getResources(), R.mipmap.ic_launcher_round);
        //实例化NotificationCompat.Builde并设置相关属性
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                //设置小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                // 设置大图标
                .setLargeIcon(mLargeIcon)
                //设置通知标题
                .setContentTitle("最简单的Notification")
                //设置通知内容
                .setContentText("只有小图标、标题、内容")
                //设置通知时间，默认为系统发出通知的时间，通常不用设置
//        .setWhen(System.currentTimeMillis())
                //设置了 setAutoCancel() 或 FLAG_AUTO_CANCEL 的通知，点击该通知时会清除它

                ;
        //通过builder.build()方法生成Notification对象,并发送通知,id=1
        mNotifyManager.notify(1, builder.build());
    }

    /**
     * 发送一个点击跳转到MainActivity的消息

     PendingIntent 具有以下几种 flag：

     FLAG_CANCEL_CURRENT:如果当前系统中已经存在一个相同的 PendingIntent 对象，那么就将先将已有的 PendingIntent 取消，然后重新生成一个 PendingIntent 对象。

     FLAG_NO_CREATE:如果当前系统中不存在相同的 PendingIntent 对象，系统将不会创建该 PendingIntent 对象而是直接返回 null 。

     FLAG_ONE_SHOT:该 PendingIntent 只作用一次。

     FLAG_UPDATE_CURRENT:如果系统中已存在该 PendingIntent 对象，那么系统将保留该 PendingIntent 对象，但是会使用新的 Intent 来更新之前 PendingIntent 中的 Intent 对象数据，例如更新 Intent 中的 Extras 。

     */
    public void sendSimplestNotificationWithAction() {
        //获取PendingIntent
        Intent mainIntent = new Intent(mContext, ActivityIntentShow.class);
        // Sets the Activity to start in a new, empty task
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainIntent.putExtra("msg" , "第一次发生的消息");
        PendingIntent mainPendingIntent = PendingIntent.getActivity(mContext, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("我是带Action的Notification")
                .setContentText("点我会打开 ActivityIntentShow ")
                .setContentIntent(mainPendingIntent)
                // 设置为false 点击不会取消
                .setAutoCancel(false);
        //发送通知
        mNotifyManager.notify( TAG ,2, builder.build());
    }

    /**
     *  第二次更新消息 只需要再次发送相同 ID 的通知即可
     */
    public void sendUpdateNotification() {
        //获取PendingIntent
        Intent mainIntent = new Intent(mContext, ActivityIntentShow.class);
        mainIntent.putExtra("msg" , "第二次更新的消息");
        PendingIntent mainPendingIntent = PendingIntent.getActivity(mContext, 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //创建 Notification.Builder 对象
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                //点击通知后自动清除
                .setAutoCancel(true)
                .setContentTitle("我是更新的Notification")
                .setContentText("点我会打开 LoginActivity ")
                .setContentIntent(mainPendingIntent);
        //发送通知
        mNotifyManager.notify( TAG ,2, builder.build());
    }


    /**
     *
     通过 NotificationManager 调用 cancel(int id) 方法清除指定 ID 的通知
     通过 NotificationManager 调用 cancel(String tag, int id) 方法清除指定 TAG 和 ID 的通知
     通过 NotificationManager 调用 cancelAll() 方法清除所有该应用之前发送的通知
     如果你是通过 NotificationManager.notify(String tag, int id, Notification notify) 方法创建的通知，
     那么只能通过 NotificationManager.cancel(String tag, int id) 方法才能清除对应的通知，调用NotificationManager.cancel(int id) 无效。
     */

    public void cancleNotify(){
        mNotifyManager.cancel(1);
    }


    public void cancleNotifyAsTag(){
        mNotifyManager.cancel(TAG , 2);
    }


    /**

         设置FLAG_NO_CLEAR
         该 flag 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
         Notification.flags属性可以通过 |= 运算叠加效果

        设置 Notification 的 flags = FLAG_NO_CLEAR
        FLAG_AUTO_CANCEL 表示该通知能被状态栏的清除按钮给清除掉
        等价于 builder.setAutoCancel(true);

        设置 Notification 的 flags = FLAG_NO_CLEAR
        FLAG_ONGOING_EVENT 表示该通知通知放置在正在运行,不能被手动清除,但能通过 cancel() 方法清除
        等价于 builder.setOngoing(true);
     */
    public void sendFlagNoClearNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("通知被设置了FLAG_NO_CLEAR")
                .setContentText("Hi,I can't be clear.");
        Notification notification = builder.build();
        //设置 Notification 的 flags = FLAG_NO_CLEAR
        //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
        //flags 可以通过 |= 运算叠加效果
        notification.flags |= Notification.FLAG_NO_CLEAR;
        mNotifyManager.notify( 3 , notification);
    }

    public void cancleNotifyFlag(){
        mNotifyManager.cancel(3);
    }

    /**
     * 通过资源ID获取Bitmap
     *
     * @param res
     * @param resId
     * @return
     */
    public static Bitmap getBitmapFromResource(Resources res, int resId) {
        return BitmapFactory.decodeResource(res, resId);
    }


    /**
     * 展示有自定义铃声效果的通知
     * 补充:使用系统自带的铃声效果:Uri.withAppendedPath(Audio.Media.INTERNAL_CONTENT_URI, "6");
     */
    public void showNotifyWithRing() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有铃声效果的通知")
                .setContentText("美妙么?安静听~")
                //调用系统默认响铃,设置此属性后setSound()会无效
                .setDefaults(Notification.DEFAULT_SOUND);
                //调用系统多媒体裤内的铃声
//                .setSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2"));
                //调用自己提供的铃声，位于 /res/values/raw 目录下
//                .setSound(Uri.parse("android.resource://com.littlejie.notification/" + R.raw.sound));
        //另一种设置铃声的方法
        //Notification notify = builder.build();
        //调用系统默认铃声
        //notify.defaults = Notification.DEFAULT_SOUND;
        //调用自己提供的铃声
        //notify.sound = Uri.parse("android.resource://com.littlejie.notification/"+R.raw.sound);
        //调用系统自带的铃声
        //notify.sound = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI,"2");
        //mManager.notify(2,notify);
        mNotifyManager.notify(4, builder.build());
    }

    /**
     * 展示有震动效果的通知,需要在AndroidManifest.xml中申请震动权限
     * <uses-permission android:name="android.permission.VIBRATE" />
     * 补充:测试震动的时候,手机的模式一定要调成铃声+震动模式,否则你是感受不到震动的
     */
    public void showNotifyWithVibrate() {
        //震动也有两种设置方法,与设置铃声一样,在此不再赘述
        long[] vibrate = new long[]{0, 500, 1000, 1500};
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是伴有震动效果的通知")
                .setContentText("颤抖吧,凡人~")
                //使用系统默认的震动参数,会与自定义的冲突
                //.setDefaults(Notification.DEFAULT_VIBRATE)
                //自定义震动效果
                .setVibrate(vibrate);
        //另一种设置震动的方法
        //Notification notify = builder.build();
        //调用系统默认震动
        //notify.defaults = Notification.DEFAULT_VIBRATE;
        //调用自己设置的震动
        //notify.vibrate = vibrate;
        //mManager.notify(3,notify);
        mNotifyManager.notify(5, builder.build());
    }

    /**
     * 显示带有呼吸灯效果的通知,但是不知道为什么,自己这里测试没成功
     */
    public void showNotifyWithLights() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是带有呼吸灯效果的通知")
                .setContentText("一闪一闪亮晶晶~")
                //ledARGB 表示灯光颜色、 ledOnMS 亮持续时间、ledOffMS 暗的时间
                .setLights(0xFF0000, 3000, 3000);
        Notification notify = builder.build();
        //只有在设置了标志符Flags为Notification.FLAG_SHOW_LIGHTS的时候，才支持呼吸灯提醒。
        notify.flags = Notification.FLAG_SHOW_LIGHTS;
        //设置lights参数的另一种方式
        //notify.ledARGB = 0xFF0000;
        //notify.ledOnMS = 500;
        //notify.ledOffMS = 5000;
        //使用handler延迟发送通知,因为连接usb时,呼吸灯一直会亮着
        mNotifyManager.notify(6, builder.build());

    }

    /**
     * 显示带有默认铃声、震动、呼吸灯效果的通知
     * 如需实现自定义效果,请参考前面三个例子
     */
    public void showNotifyWithMixed() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是有铃声+震动+呼吸灯效果的通知")
                .setContentText("我是最棒的~")
                //等价于setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
                .setDefaults(Notification.DEFAULT_ALL);
        mNotifyManager.notify(7, builder.build());
    }

    /**
     * 通知无限循环,直到用户取消或者打开通知栏(其实触摸就可以了),效果与 FLAG_ONLY_ALERT_ONCE 相反
     * 注:这里没有给Notification设置PendingIntent,也就是说该通知无法响应,所以只能手动取消
     */
    public void showInsistentNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("我是一个死循环,除非你取消或者响应")
                .setContentText("啦啦啦~")
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notify = builder.build();
        notify.flags |= Notification.FLAG_INSISTENT;
        mNotifyManager.notify(8, notify);
    }

    /**
     * 通知只执行一次,与默认的效果一样
     */
    public void showAlertOnceNotify() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("仔细看,我就执行一遍")
                .setContentText("好了,已经一遍了~")
                .setDefaults(Notification.DEFAULT_ALL);
        Notification notify = builder.build();
        notify.flags |= Notification.FLAG_ONLY_ALERT_ONCE;
        mNotifyManager.notify(9, notify);
    }


    /**
     * 清除所有通知
     */
    public void clearNotify() {
        mNotifyManager.cancelAll();
    }

    /**
     *  设置进度通知消息
     */
    public void setProgressNotify() {
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setContentTitle("Picture Download")
                .setContentText("Download in progress")
                .setSmallIcon(R.mipmap.ic_launcher);
        // Start a lengthy operation in a background thread
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr += 5) {
                            // Sets the progress indicator to a max value, the
                            // current completion percentage, and "determinate"
                            // state
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            mNotifyManager.notify(10, mBuilder.build());
                            // Sleeps the thread, simulating an operation
                            // that takes time
                            try {
                                // Sleep for 2 seconds
                                Thread.sleep(2 * 1000);
                            } catch (InterruptedException e) {
                                Log.d(TAG, "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0, 0, false);
                        mNotifyManager.notify(10, mBuilder.build());
                    }
                }
                // Starts the thread by calling the run() method in its Runnable
        ).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setNotifyAction(){
        Intent intentPrevious = new Intent(mContext, ActivityIntentShow.class);
        intentPrevious.putExtra("msg" , "Previous");
        PendingIntent prevPendingIntent = PendingIntent.getActivity(mContext, 0, intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentPause = new Intent(mContext, ActivityIntentShow.class);
        intentPause.putExtra("msg" , "Pause");
        PendingIntent pausePendingIntent = PendingIntent.getActivity(mContext, 1, intentPause, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent intentNext = new Intent(mContext, ActivityIntentShow.class);
        intentNext.putExtra("msg" , "Next");
        PendingIntent nextPendingIntent = PendingIntent.getActivity(mContext, 2, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(mContext)
            // Show controls on lock screen even when user hides sensitive content.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.mipmap.ic_launcher)
            // Add media control buttons that invoke intents in your media service
            .addAction(android.R.drawable.ic_media_previous , "Previous", prevPendingIntent) // #0
            .addAction(android.R.drawable.ic_media_pause , "Pause", pausePendingIntent)  // #1
            .addAction(android.R.drawable.ic_media_next , "Next", nextPendingIntent)     // #2
            .setContentTitle("带有Action的消息")
            .setContentText("带有 action 按钮的")
        ;

        // 在展开时使用 BigTextStyle （一个扩展样式）
        NotificationCompat.BigTextStyle textStyle = new NotificationCompat.BigTextStyle(mBuilder);
        textStyle.bigText("Here is some additional text to be displayed when " +
                "the notification is "+"in expanded mode. I can fit so much content " +
                "into this giant view!");
        textStyle.build();


        // 在展开时使用 BigPictureStyle
        NotificationCompat.BigPictureStyle pictureStyle =
                new NotificationCompat.BigPictureStyle(mBuilder);
        pictureStyle.bigPicture(BitmapFactory.decodeResource(
                mContext.getResources(), R.drawable.icon_01));
        pictureStyle.build();


        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        for (int i=0; i < 6; i++) {
            inboxStyle.addLine( "new message " + i );
        }
        inboxStyle.setBigContentTitle("6 new message");
        inboxStyle.setSummaryText("mtwain@android.com");
        mBuilder.setStyle(inboxStyle);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            mBuilder.setCustomBigContentView()
        }else {
//            mBuilder.setCustomBigContentView()
        }

        mNotifyManager.notify(11, mBuilder.build());
    }

    boolean isBind = false;
    NotificationReceiver18 myBindService;
    NotificationReceiver18.ServiceInterface msi ;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
//            NotificationReceiver18.MyBinder binder = (NotificationReceiver18.MyBinder) service;
//            myBindService = binder.getService();
//            msi = myBindService.getServiceInterface();
            myNotificationView.showMsg( "服务 绑定 成功" );
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            myNotificationView.showMsg( "服务 绑定 失败" );
        }
    };
    //绑定服务
    public void bindService(int index ){
//        Intent intent = new Intent(mContext, NotificationReceiver18.class);
//        isBind = mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    //解绑服务
    public void unBindService(int index ){
//        if (isBind) {
//            mContext.unbindService(mConnection);
//            isBind = false ;
//        }else {
//            myNotificationView.showMsg( "还没有绑定服务" );
//        }
    }

    public void skipAction(int index ){
//        if(msi!= null) {
//            msi.skipAction(index);
//        } else {
//            myNotificationView.showMsg( "服务还没有绑定成功" );
//        }

        /** 使用广播来实现消息传递，界面传递到服务 **/
//        Intent mIntent = new Intent();
//        mIntent.setAction(NotificationReceiver18.BROADCAST_ACTION);
//        mIntent.putExtra("index" , index ) ;
//        mContext.sendBroadcast(mIntent);


        /** 使用RxBus 来实现组件间的传递消息 **/
        EventMsg eventMsg = new EventMsg();
        eventMsg.setIndex(index);
        eventMsg.setMsg("这是是从Activity界面发送过来的数据");
//        RxBus.getInstance().post(eventMsg);

        /** 使用RxBusTwo 来实现组件间的传递消息 **/
        RxBusTwo.getInstance().post(eventMsg);

    }

    protected void onCreate() {
//        Disposable register = RxBusTwo.getInstance().register( EventMsg.class , new Consumer<EventMsg>() {
//            @Override
//            public void accept(EventMsg eventMsg) throws Exception {
//                /**这个地方获取到数据。并执行相应的操作*/
//
//            }
//
//        });
//        //TODO 这个地方必须添加注册
//        RxBusTwo.getInstance().addSubscription(mContext,register);
    }

    protected void onDestroy() {
//        RxBusTwo.getInstance().unSubscribe( mContext );
    }



}
