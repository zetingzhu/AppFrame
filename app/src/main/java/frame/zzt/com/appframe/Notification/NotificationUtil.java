package frame.zzt.com.appframe.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.Random;

import frame.zzt.com.appframe.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by zeting
 * Date 19/2/15.
 */

public class NotificationUtil {
    private static int NOTIFYID = 123 ;
    private Context mContext ;
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private int requestCode ;

    public NotificationUtil(Context context ) { mContext = context;
        mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        requestCode = 0 ;
    }

    public void notifyShow() {
        mNotification = createNotification(mContext , "大图标标题" );
        mNotificationManager.notify( NOTIFYID , mNotification);
    }

    public void notifyRefresh(){
        Random random = new Random();
        int i = random.nextInt(100) ;
        String msg = "大图标标题 - " + i ;
        mNotification = createNotification(mContext , msg );
        mNotificationManager.notify( NOTIFYID , mNotification);
    }

    private Notification createNotification(Context context , String textMsg ) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext);

//        notificationBuilder.setContentTitle("Wonderful music");
//        notificationBuilder.setContentText("My Awesome Band");

        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        // 设置大图标
        Bitmap largeBitmap = BitmapFactory.decodeResource( context.getResources(), R.drawable.icon_01 ) ;
        notificationBuilder.setLargeIcon(largeBitmap);

        notificationBuilder.setTicker("开始啦~~");
        // 不能滑动清除
        notificationBuilder.setOngoing(true);
//        notificationBuilder.setContent(CreateNormalView(context));//设置普通notification视图
        notificationBuilder.setCustomBigContentView(CreateBigView(context , textMsg  ));//设置显示bigView的notification视图
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);//设置最大优先级
        // 绑定到按钮上后，去掉这个
//        Intent mIntent2 = new Intent(mContext, ActivityIntentShow2.class);
//        mIntent2.putExtra("msg" , "点击整个通知栏的跳转");
//        PendingIntent ContentIntent = PendingIntent.getActivity(mContext, ++requestCode , mIntent2, PendingIntent.FLAG_UPDATE_CURRENT);
//        notificationBuilder.setContentIntent(ContentIntent);
        // 悬挂式通知栏
//        notificationBuilder.setFullScreenIntent(CustomViewIntent3 , false) ;

        Notification notification = notificationBuilder.build();
        return notification;
    }

    private RemoteViews CreateNormalView(Context context ){
        /** * 双指上下滑动切换大小视图 * */
        //普通notification用到的视图
        RemoteViews normalView = new RemoteViews( mContext.getPackageName(), R.layout.notify_small_layout );
        return normalView ;
    }

    private RemoteViews CreateBigView(Context context , String textMsg ){
        if (requestCode >= Integer.MAX_VALUE ){
            requestCode = 0 ;
        }
        requestCode += requestCode ;
        //显示bigView的notification用到的视图
        RemoteViews bigView = new RemoteViews( mContext.getPackageName(), R.layout.notify_bit_layout);
        //给一个控件设置内容
        bigView.setTextViewText(R.id.tv_big_name , textMsg );
        //给一个控件添加单击事件
        Intent mIntent1 = new Intent(mContext, ActivityIntentShow.class);
        mIntent1.putExtra("msg" , "自定义消息通知 1");
        PendingIntent CustomViewIntent = PendingIntent.getActivity(mContext, requestCode , mIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mIntent2 = new Intent(mContext, ActivityIntentShow2.class);
        mIntent2.putExtra("msg" , "自定义消息通知 2");
        PendingIntent CustomViewIntent2 = PendingIntent.getActivity(mContext, requestCode , mIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mIntent3 = new Intent(mContext, ServiceNotification.class);
        mIntent3.putExtra("msg" , "自定义消息通知 3");
        PendingIntent CustomViewIntent3 = PendingIntent.getService(mContext, requestCode , mIntent3, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent mIntent4 = new Intent();
        mIntent4.putExtra("msg" , "自定义消息通知 4");
        mIntent4.setAction(ReceiverNotification.BECEIVER_CHECK) ;
        PendingIntent CustomViewIntent4 = PendingIntent.getBroadcast(mContext, requestCode , mIntent4, PendingIntent.FLAG_UPDATE_CURRENT);

        bigView.setOnClickPendingIntent(R.id.ib_big_01, CustomViewIntent );
        bigView.setOnClickPendingIntent(R.id.ib_big_02, CustomViewIntent2 );
        bigView.setOnClickPendingIntent(R.id.ib_big_03, CustomViewIntent3 );
        bigView.setOnClickPendingIntent(R.id.ib_big_04, CustomViewIntent4 );
        return bigView ;
    }
}
