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
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.modle.BaseModel;
import frame.zzt.com.appframe.mvp.mvpbase.BaseView;

/**
 * Created by allen on 18/10/11.
 */
public interface MyNotificationView extends BaseView {

    public void showMsg(String msg);
}
