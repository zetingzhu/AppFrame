package frame.zzt.com.appframe.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * 蓝牙socket 客户端
 */
public class ActivityBluetooth6 extends BaseAppCompatActivity {
    public final static String TAG = ActivityBluetooth6.class.getSimpleName();


    BluetoothAdapter mBluetoothAdapter;
    //创建BluetoothServerSocket类
    BluetoothServerSocket mServerSocket;
    BluetoothSocket socket = null;
    //新建BluetoothDevice对象
    BluetoothDevice mDevice;

    @BindView(R.id.tv_msg)
    TextView tv_msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth6);
        ButterKnife.bind(this);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    @OnClick(R.id.button1)
    public void connectDevice() {
        // 三星手机的蓝牙地址
        String address = "68:05:71:19:F1:DF";
        mDevice = mBluetoothAdapter.getRemoteDevice(address);
        if (mDevice != null) {
            ClientThread mClientThread = new ClientThread();
            mClientThread.start();
        }
    }

    /**
     * 客户端，进行连接的线程
     *
     * @author Administrator
     */
    class ClientThread extends Thread {
        @Override
        public void run() {
            try {
                //创建一个socket尝试连接，UUID用正确格式的String来转换而成*
                socket = mDevice.createRfcommSocketToServiceRecord(ActivityBluetooth7.MY_UUID);
                Log.i(TAG, "正在连接，请稍后......");
                //该方法阻塞，一直尝试连接*
                socket.connect();
                Log.i(TAG, "连接成功");
                //进行接收线程
                new BlutSocketReadMsg(ActivityBluetooth6.this, socket, tv_msg).start();
            } catch (IOException e) {
                Log.i(TAG, "连接失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送消息
     */
    @OnClick(R.id.button2)
    public void sendMessage() {
        String msg = "客户端 发送消息：" + System.currentTimeMillis();
        if (socket == null) {
            Toast.makeText(getApplicationContext(), "未建立连接", Toast.LENGTH_SHORT).show();
            return;
        }//防止未连接就发送信息
        try {
            //使用socket获得outputstream*
            OutputStream out = socket.getOutputStream();
            out.write(msg.getBytes());//将消息字节发出
            out.flush();//确保所有数据已经被写出，否则抛出异常
            Toast.makeText(getApplicationContext(), "发送:" + msg, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
        }
    }

}
