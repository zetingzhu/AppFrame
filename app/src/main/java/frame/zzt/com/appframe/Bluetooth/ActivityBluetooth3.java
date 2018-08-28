package frame.zzt.com.appframe.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

/**
 * Created by allen on 18/8/21.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ActivityBluetooth3 extends BaseAppCompatActivity implements BluetoothView {

    public final static String TAG = ActivityBluetooth3.class.getSimpleName();

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;

    @BindView(R.id.btn_scan)
    Button btn_scan;

    private Context mContext;

    private String bleManufacturers; //厂商ID   扫描～

    private MyBleAQPresenter mAQPresenter ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);



//        bleManufacturers = "41510695d188";
        bleManufacturers = "41510695d188111";

        mAQPresenter = new MyBleAQPresenter(ActivityBluetooth3.this);
        mAQPresenter.init();

    }

    @OnClick(R.id.btn_scan)
    public void OnClickScan() {

//        initService();
//        startBleScanDevice();

        mAQPresenter.startBleScanDevice(bleManufacturers);

    }

    @OnClick(R.id.btn_stop_scan)
    public void OnClickStopScan() {
    }


    @OnClick(R.id.btn_connect)
    public void OnClickConnect() {
        String address = "EE:D7:65:55:58:DE" ;
        mAQPresenter.connectBletooth(address);
    }

    @OnClick(R.id.btn_dis)
    public void OnClickDisconnect() {
        mAQPresenter.disCloseDevice();
    }

    @OnClick(R.id.btn_open)
    public void onClickOpen(){
        mAQPresenter.openBle();
    }

    @OnClick(R.id.btn_close)
    public void onClickClose(){
        mAQPresenter.closeBle();
    }


    @OnClick(R.id.btn_start_rssi)
    public void OnClickReadRssi() {
        mAQPresenter.readRssi();
    }

    @OnClick(R.id.btn_stop_rssi )
    public void OnClickCloseReadRssi() {
        mAQPresenter.closeReadRssi();
    }



    public void openBluetooth() {
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
                case Activity.RESULT_OK: {
                    // TODO 用户选择开启 Bluetooth，Bluetooth 会被开启
                    Log.i(TAG, "Bluetooth 会被开启 - 蓝牙的状态：");
                }
                break;
                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    // TODO 用户拒绝打开 Bluetooth, Bluetooth 不会被开启
                    Log.i(TAG, "Bluetooth 不会被开启 - 蓝牙的状态：");
                }
                break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (mServiceConnection != null) {
//           unbindService(mServiceConnection);
//        }
        mAQPresenter.onDestroy();
    }
}
