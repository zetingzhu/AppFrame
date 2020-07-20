package frame.zzt.com.appframe.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * 蓝牙socket 服务端
 */
public class ActivityBluetooth7 extends BaseAppCompatActivity {
    public final static String TAG = ActivityBluetooth7.class.getSimpleName() ;
    public final static UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter mBluetoothAdapter;
    //创建BluetoothServerSocket类
    BluetoothServerSocket mServerSocket;
    BluetoothSocket socket = null;

    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth7);
        ButterKnife.bind(this);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        ServiceThread mServiceThread = new ServiceThread();
        mServiceThread.start();

    }

    /**
     * 　　　* 服务端，接收连接的线程
     *
     * @author Administrator
     */
    class ServiceThread extends Thread {
        @Override
        public void run() {
            try {
                //先用本地蓝牙适配器创建一个serversocket *
                mServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(mBluetoothAdapter.getName(), MY_UUID);
                Log.i(TAG ,"正在等待连接");
                if (socket != null) {
                    Log.i(TAG ,"连接成功");
                }
                //等待连接，该方法阻塞*
                socket = mServerSocket.accept();
                Log.i(TAG ,"连接成功");
                new BlutSocketReadMsg(ActivityBluetooth7.this , socket , tv_msg).start();
            } catch (IOException e) {
                Log.i(TAG ,"连接失败");
                e.printStackTrace();
            }

        }
    }
    /**
     * 发送消息
     */
    @OnClick(R.id.button2)
    public void sendMessage() {

        String msg = "服务端 发送消息：" + System.currentTimeMillis() ;
        if(socket==null){
            Toast.makeText(getApplicationContext(), "未建立连接", Toast.LENGTH_SHORT).show();return;}//防止未连接就发送信息
        try {
            //使用socket获得outputstream*
            OutputStream out=socket.getOutputStream();
            out.write(msg.getBytes());//将消息字节发出
            out.flush();//确保所有数据已经被写出，否则抛出异常
            Toast.makeText(getApplicationContext(), "发送:"+msg, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
        }
    }


}
