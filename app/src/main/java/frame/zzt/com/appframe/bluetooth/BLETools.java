package frame.zzt.com.appframe.bluetooth;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
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

public class BLETools {
    private static final String TAG = "BLETools";
    private static BLETools instance;
    public static final String SECRET = "TqtRDxZWVsGRibHsvCyRUTv9"; //秘钥
    public static final String SERIAL_NUMBER = "2001"; // 序列号

    private BluetoothLeScanner scanner;
    private ScanCallback mScanCallback;

    public String mTestImei = "357550110390180"; // 测试设备的IMEI

    private List<ScanFilter> bleScanFilters;
    private ScanSettings scanSettings;

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;

    private boolean mBleStartStatus;

    public synchronized static BLETools getInstance() {
        if (instance == null) {
            instance = new BLETools();
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


    /*
    * 扫描设备
    * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void scanDevice(Context context, final DeviceCallback callback) {
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
                        callback.onScanResult(result);
                    }
                });
//                scanner.startScan( mScanCallback = new ScanCallback() {
//                    @Override
//                    public void onScanResult(int callbackType, ScanResult result) {
//                        super.onScanResult(callbackType, result);
//                        callback.onScanResult(result);
//                    }
//                });
            }
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void stopScan() {
        Log.i(TAG, "stopScans1: ");
        if (judgmentVersion()) {
//        mBleStartStatus = bluetoothAdapter.isEnabled();
            if (bluetoothAdapter != null && bluetoothAdapter.isEnabled() && scanner != null && mScanCallback != null) {
                Log.i(TAG, "stopScans: ");
                scanner.stopScan(mScanCallback);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public void startScan() {
//        if (judgmentVersion()) {
//            //        mBleStartStatus = bluetoothAdapter.isEnabled();
//            if (bluetoothAdapter.isEnabled() && scanner != null && mScanCallback != null) {
//                Log.i(TAG, "startScan: ");
//                scanner.startScan(bleScanFilters, scanSettings, mScanCallback);
//            }
//        }
//    }

    public boolean scanCallbackIsNull() {
        return mScanCallback == null ? true : false;
    }

    /*
    * 判断SDk是否大于21   蓝牙才能用
    * */
    public boolean judgmentVersion() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) { // SDK小于21
            return false;
        }
        return true;
    }


    public interface DeviceCallback {
        /*
        * 扫描
        * */
        void onScanResult(ScanResult result);
    }
}
