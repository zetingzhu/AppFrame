package frame.zzt.com.appframe.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

/**
 * Created by allen on 18/8/21.
 */

public class ActivityBluetooth2 extends BaseAppCompatActivity implements BluetoothView {

    public final static String TAG = ActivityBluetooth2.class.getSimpleName() ;

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;

    @BindView(R.id.btn_scan)
    Button btn_scan ;

    BluetoothClient mClient ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

         mClient = new BluetoothClient(this);

    }

    @OnClick(R.id.btn_scan)
    public void OnClickScan(){
        SearchRequest request = new SearchRequest.Builder()
//                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(5000)      // 再扫BLE设备2s
                .build();

        mClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {
                Log.i(TAG , "Bluetooth onSearchStarted "  ) ;
            }

            @Override
            public void onDeviceFounded(SearchResult device) {
                Beacon beacon = new Beacon(device.scanRecord);
                BluetoothLog.v(String.format("beacon for %s\n%s", device.getName(), device.getAddress(), beacon.toString()));
                Log.i(TAG , "Bluetooth onDeviceFounded name :" + device.getName() + " - " + device.getAddress() ) ;
            }

            @Override
            public void onSearchStopped() {
                Log.i(TAG , "Bluetooth onSearchStopped"  ) ;
            }

            @Override
            public void onSearchCanceled() {
                Log.i(TAG , "Bluetooth onSearchCanceled"  ) ;
            }
        });
    }
    public void openBluetooth(){
        // 第一种打开蓝牙方式
//            BleManager.getInstance().enableBluetooth();

        // 第二种打开蓝牙方式
//        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//        startActivityForResult(intent, 0x01);

        // 第二种 调用系统界面打开蓝牙
                Intent requestBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // 设置 Bluetooth 设备可以被其它 Bluetooth 设备扫描到
//                requestBluetoothOn.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        // 设置 Bluetooth 设备可见时间
//                requestBluetoothOn.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, BLUETOOTH_DISCOVERABLE_DURATION);
        // 请求开启 Bluetooth
                startActivityForResult(requestBluetoothOn, REQUEST_CODE_BLUETOOTH_ON);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // requestCode 与请求开启 Bluetooth 传入的 requestCode 相对应
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK:
                {
                    // TODO 用户选择开启 Bluetooth，Bluetooth 会被开启
                    Log.i(TAG , "Bluetooth 会被开启 - 蓝牙的状态：" ) ;
                }
                break;
                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED:
                {
                    // TODO 用户拒绝打开 Bluetooth, Bluetooth 不会被开启
                    Log.i(TAG , "Bluetooth 不会被开启 - 蓝牙的状态：" ) ;
                }
                break;
                default:
                    break;
            }
        }
    }
}
