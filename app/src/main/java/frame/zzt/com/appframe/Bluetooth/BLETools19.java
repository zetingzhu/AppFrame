package frame.zzt.com.appframe.Bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.BLUETOOTH_SERVICE;

/**
 * Created by allen on 17/6/23.
 * 蓝牙工具
 */

public class BLETools19 {
    private static final String TAG = "BLETools";
    private static BLETools19 instance;
    public static final String SECRET = "TqtRDxZWVsGRibHsvCyRUTv9"; //秘钥
    public static final String SERIAL_NUMBER = "2001"; // 序列号

    private BluetoothLeScanner scanner;
    private ScanSettings scanSettings;

    private ScanCallback mScanCallback;

    public String mTestImei = "357550110390180"; // 测试设备的IMEI
    private List<ScanFilter> bleScanFilters;

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;

    private boolean mBleStartStatus;

    BluetoothAdapter.LeScanCallback mLeScanCallback;

    public synchronized static BLETools19 getInstance() {
        if (instance == null) {
            instance = new BLETools19();
        }
        return instance;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initBle(Context context) {
        if (judgmentVersion()) {
            bluetoothManager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);//这里与标准蓝牙略有不同
            bluetoothAdapter = bluetoothManager.getAdapter();
            bleScanFilters = new ArrayList<>();

            scanSettings = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();
            scanner = bluetoothAdapter.getBluetoothLeScanner();
        }

    }

    private void BLETools() {

    }

    public boolean isBleStartStatus() {
        return mBleStartStatus;
    }

    public void setBleStartStatus(boolean bleStartStatus) {
        this.mBleStartStatus = bleStartStatus;
    }



    public void scanDevice(Context context, final DeviceCallback callback) {
        if (judgmentVersion()) {
            if (bluetoothAdapter.isEnabled()  ) {
                mBleStartStatus = true;
                bluetoothAdapter.startLeScan(mLeScanCallback= new BluetoothAdapter.LeScanCallback(){
                    @Override
                    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
//            mDevices.add(bluetoothDevice);
                        //成功扫描到设备后，在这里获得bluetoothDevice。可以放进设备列表成员变量当中方便后续操作。
                        //也可以发广播通知activity发现了新设备，更新活动设备列表的显示等。
                        //这里需要注意一点，在onLeScan当中不能执行耗时操作，不宜执行复杂运算操作，切记，
                        //下面即将提到的onScanResult，onBatchScanResults同理。
                        Log.i(TAG, "扫描蓝牙扫描结果");
                        callback.onScanDevice( device );
                    }
                }
                );
            }
        }
    }

    /**
     *  5.0 以上蓝牙扫描
     * @param sign
     * @param context
     * @param callback
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void scanDevice( int sign ,Context context, final BLETools19.DeviceCallback callback) {
        if (judgmentVersion()) {
            if (bluetoothAdapter.isEnabled() && scanner != null) {
                mBleStartStatus = true;
                if (mScanCallback != null) {
                    scanner.stopScan(mScanCallback);
                }
                scanner.startScan(bleScanFilters, scanSettings, mScanCallback = new ScanCallback() {
                    @Override
                    public void onScanResult(int callbackType, ScanResult result) {
                        super.onScanResult(callbackType, result);
                        Log.i(TAG, "这没有扫描蓝牙的结果");
                        callback.onScanResult(result);
                    }
                });
            }
        }
    }

    public void scanDevice(){
        bluetoothAdapter.startDiscovery() ;
    }


    public void stopScan() {
        Log.i(TAG, "stopScans1: ");
        if (judgmentVersion()) {
            if (bluetoothAdapter != null && bluetoothAdapter.isEnabled() ) {
                Log.i(TAG, "stopScans: ");
                bluetoothAdapter.stopLeScan(mLeScanCallback);
            }
        }
    }



    public boolean scanCallbackIsNull() {
        return mScanCallback == null ? true : false;
    }

    /*
    * 判断SDk是否大于21   蓝牙才能用
    * */
    public boolean judgmentVersion() {
        if (Build.VERSION.SDK_INT <= 18) { // SDK小于19
            Log.i(TAG , "不能使用蓝牙");
            return false;
        }
        return true;
    }


    public interface DeviceCallback {
        /*
        * 扫描
        * */
        void onScanResult(ScanResult result);
        void onScanDevice(BluetoothDevice result);
    }
}
