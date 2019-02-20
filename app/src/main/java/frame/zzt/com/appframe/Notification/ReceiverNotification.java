package frame.zzt.com.appframe.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by zeting
 * Date 19/2/14.
 */

public class ReceiverNotification extends BroadcastReceiver {
    private final static String TAG = ServiceNotification.class.getSimpleName();

    public static String BECEIVER_CHECK = "com.notification.check" ;

    NotifyListener mListener ;

    public void setNotifyUi(NotifyListener mListener){
        Log.d(TAG , "广播  设置广播接口 "  ) ;
        this.mListener = mListener ;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG , "广播Action " + intent.getAction() ) ;
        if (intent.getAction().equals(BECEIVER_CHECK)){
            if (mListener != null ) {
                mListener.notifyUi();
            }
        }

    }




}
