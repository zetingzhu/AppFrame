package frame.zzt.com.appframe.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * 循环读取信息的线程
 */

public class BlutSocketReadMsg extends Thread {
    public final static String TAG = "ActivityBluetoothSocket" ;

    BluetoothSocket socket = null;
    TextView mMsg;
    Context mContext ;
    public BlutSocketReadMsg(Context mContext , BluetoothSocket socket , TextView textView) {
        this.socket = socket ;
        this.mMsg = textView ;
        this.mContext = mContext ;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];//定义字节数组装载信息
        int bytes;//定义长度变量
        InputStream in = null;
        try {
            //使用socket获得输入流*
            in = socket.getInputStream();
            //一直循环接收处理消息*
            while (true) {
                if ((bytes = in.read(buffer)) != 0) {
                    byte[] buf_data = new byte[bytes];
                    for (int i = 0; i < bytes; i++) {
                        buf_data[i] = buffer[i];
                    }
                    final String msg = new String(buf_data);//最后得到String类型消息
                    Log.i(TAG ,msg);
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMsg.setText(msg);
                        }
                    });
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i(TAG ,"连接已断开");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
