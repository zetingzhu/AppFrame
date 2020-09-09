package frame.zzt.com.appframe.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * Created by allen on 18/5/10.
 * 消息工具
 */

public class MyMessageUtil {
    Context mContext;

    private MyMessageUtil(Context context) {
        mContext = context;
    }

    private static MyMessageUtil instance = null;

    public static synchronized MyMessageUtil getInstance(Context mContext) {
        if (instance == null) {
            instance = new MyMessageUtil(mContext.getApplicationContext());
        }
        return instance;
    }

    Toast toast;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (toast == null) {
                        toast = Toast.makeText(mContext, msg.obj + "", Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(msg.obj + "");
                    }
                    toast.show();
                    break;
            }
        }
    };

    public void setMessage(String strMessage) {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = strMessage;
        mHandler.sendMessage(msg);
    }
}
