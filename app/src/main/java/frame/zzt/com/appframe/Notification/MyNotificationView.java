package frame.zzt.com.appframe.Notification;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import frame.zzt.com.appframe.mvp.mvpbase.BaseView;

/**
 * Created by allen on 18/10/11.
 */
public interface MyNotificationView extends BaseView {

    public void showMsg(String msg);
}
