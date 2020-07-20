package frame.zzt.com.appframe.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * 蓝牙作为中心设备
 * 客户端(也叫主机/中心设备/Central)
 * 客户端的核心类是 BluetoothGatt
 */
public class ActivityBluetooth4 extends BaseAppCompatActivity implements BluetoothView {

    public final static String TAG = ActivityBluetooth4.class.getSimpleName() ;

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;

    @BindView(R.id.btn_scan)
    Button btn_scan ;
    @BindView(R.id.btn_stop_scan)
    Button btn_stop_scan ;

    @BindView(R.id.btn_wirte)
    Button btn_wirte ;
    @BindView(R.id.btn_read)
    Button btn_read ;

    private MyBleAQPresenter19 mAQPresenter ;
    private String bleManufacturers; //厂商ID   扫描～

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        // 检查手机是否支持BLE，不支持则退出
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "您的设备不支持蓝牙BLE，将关闭", Toast.LENGTH_SHORT).show();
            finish();
        }


//        bleManufacturers = "41510695d188";
        bleManufacturers = "41510695d188111";

        mAQPresenter = new MyBleAQPresenter19(ActivityBluetooth4.this);
        mAQPresenter.init();

    }

    @OnClick(R.id.btn_scan)
    public void OnClickScan() {
        Log.i(TAG, "开始扫描");
        // 5.0 已下
//        Galaxy S5 - 51:A8:58:E5:38:4E - 2
//        mAQPresenter.startBleScanDevice();
        // 5.0 以上
        // ble 需要蓝牙，位置 权限，没有就不能扫描
        // Galaxy S5 - 6E:51:CD:53:83:A5 - 2
        mAQPresenter.startBleScanDevice50();
    }

    @OnClick(R.id.btn_stop_scan)
    public void OnClickStopScan() {
        mAQPresenter.stopBleScanDevice();
    }


    /**
     *  连接 服务端
     */
    @OnClick(R.id.btn_connect)
    public void OnClickConnect() {
        // 三星手机的蓝牙地址
        String address = "68:05:71:19:F1:DF" ;

        // 测试设备的连接地址
        String addressCS = "E6:F8:DB:40:C0:BF" ;
        mAQPresenter.connectBletooth(addressCS);
    }

    /**
     *  断开连接服务端
     */
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

    /**
     * 向 服务端 写入数据
     */
    @OnClick(R.id.btn_wirte)
    public void OnClickWirteData(){
        mAQPresenter.writeData("wirte" + + (int) (Math.random() * 100));
    }

    /**
     * 读 服务端 返回的数据
     */
    @OnClick(R.id.btn_read)
    public void OnClickReadData(){

        mAQPresenter.readData();
    }

    /** 蓝牙开锁*/
    @OnClick(R.id.btn_ble_open)
    public void OnClickBleOpen(){
        byte open = (byte) 0xC2;
        mAQPresenter.writeDataOpenCloseLock(open) ;
    }

    /** 蓝牙关锁*/
    @OnClick(R.id.btn_ble_close)
    public void OnClickBleClose(){
            byte close = (byte) 0xC1;
        mAQPresenter.writeDataOpenCloseLock(close) ;
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
