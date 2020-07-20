package frame.zzt.com.appframe.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by allen on 18/8/22.
 */

public class MyBleAQPresenter {

    public final static String TAG = ActivityBluetooth3.class.getSimpleName();

    private ServiceConnection mServiceConnection;
    private BluetoothDevice currentBleDevice; // 蓝牙设备


    private BleService mBleService; // 蓝牙服务
    private BLETools mBletools;
    private Context mContext ;

    private Timer mRssiTimer; //蓝牙信号
    private TimerTask mRiisTimerTask; //蓝牙信号
    private Handler mHandler;
    //15秒搜索时间
    private static final long SCAN_PERIOD = 15000;

    private String bleManufacturers; //厂商ID   扫描～
    BluetoothGattCharacteristic mGattCharacteristic ;

    public MyBleAQPresenter(Context mContext) {
        this.mContext = mContext ;
        mHandler = new Handler();
    }

    /**
     * 最开始初始化
     */
    public void init(){
        mBletools = BLETools.getInstance();
        mBletools.initBle(mContext);
        initService();
    }

    /**
     * 销毁服务
     */
    public void onDestroy() {
        if (mServiceConnection != null) {
            mContext.unbindService(mServiceConnection);
        }
    }

    /**
     * 连接蓝牙
     */
    public void connectBletooth(String address) {
        mBleService.connect(address, myCallback);
    }

    /**
     * 断开连接
     */
    public void disCloseDevice(){
        mBleService.DisCloseDevice();
    }

    /**
     * 打开电门
     */
    public void openBle(){
//        String open = "110046439a4f2a2866d0de2db4727c220cb92500" ;
//        String stop = "1200478dc081fb5127a9fe1c67952699b8a05d00" ;
        String open = "1100483ca30ae9dd7607e63e48f5d524a009f500" ;
        boolean booWirte = mBleService.wirteCharacteristic1(mGattCharacteristic, open);
        Log.i(TAG, "蓝牙连接 写入 开启 数据： booWirte " + booWirte );
    }
    /**
     * 关闭电门
     */
    public void closeBle(){
//        String open = "110046439a4f2a2866d0de2db4727c220cb92500" ;
//        String stop = "1200478dc081fb5127a9fe1c67952699b8a05d00" ;
        String stop = "1200491acd9ed9e392458dac249acc5a8da8b400" ;
        boolean booWirte = mBleService.wirteCharacteristic1(mGattCharacteristic , stop);
        Log.i(TAG, "蓝牙连接 写入 关闭 数据： booWirte " + booWirte );
    }

    /**
     * 开始扫描并连接地址
     * @param bleManufacturers
     */
    public void startBleScanDevice( String bleManufacturers) {
        this.bleManufacturers = bleManufacturers ;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBletools.stopScan();
            }
        }, SCAN_PERIOD);
        mBletools.scanDevice(mContext, mScanDeviceCallback);//开始搜索
    }

    BLETools.DeviceCallback mScanDeviceCallback = new BLETools.DeviceCallback() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(ScanResult result) {

            Log.i(TAG, "onScanResult1:  ====" + result.getDevice().getName() + " - " +  result.getDevice().getAddress() );
            SparseArray<byte[]> dataArray = result.getScanRecord().getManufacturerSpecificData();
            if (dataArray != null) {
                if (dataArray.size() > 0) {
                    if (mBleService != null) {
                        Log.i(TAG, "onScanResult1:  ====" + bytesToHex(dataArray.valueAt(0)));
                        if (bleManufacturers != null && bleManufacturers.length() > 4) {
                            String str = bleManufacturers.substring(4);
                            if (str.equalsIgnoreCase(bytesToHex(dataArray.valueAt(0)))) {
                                // 找到指定设备
                                mBletools.stopScan();

                                if (!mBleService.initialize()) {
                                    Log.i(TAG, "Unable to initialize Bluetooth");
                                    return;
                                }
                                currentBleDevice = result.getDevice();
                                mBleService.connect(currentBleDevice.getAddress(),myCallback);
                                //name:AQ-Ebike - address:EE:D7:65:55:58:DE
                                Log.i(TAG, "连接上了设备: " + currentBleDevice + " - name:" + currentBleDevice.getName()+ " - address:" + currentBleDevice.getAddress());
                            }
                        }
                    } else {
                        Log.i(TAG, "mBleService 为 null");
                    }
                }
            }
        }
    };

    MyInterfaceCallback myCallback = new MyInterfaceCallback(){

        @Override
        public void bleStateConnected(String action) {
            Log.i(TAG, "蓝牙连接 bleStateConnected "  );
        }

        @Override
        public void bleStateDisconnected(String action) {
            Log.i(TAG, "蓝牙连接 bleStateDisconnected "  );
        }

        @Override
        public void bleServicesDiscovered(String action, BluetoothGattCharacteristic characteristic) {
            Log.i(TAG, "蓝牙连接 bleServicesDiscovered 连接成功"  );
            mGattCharacteristic = characteristic ;
        }

        @Override
        public void bleServiceReadError(String action) {
            Log.i(TAG, "蓝牙连接 bleServiceReadError "  );
        }

        @Override
        public void bleServiceRed(String action, BluetoothGattCharacteristic characteristic) {
            Log.i(TAG, "蓝牙连接 bleServiceRed "  );
            byte[] data = characteristic.getValue();
            String str = BleService.byte2HexStr(data);
            if (!TextUtils.isEmpty(str)) {
                String bluetoothResult = str.substring(str.length() - 1, str.length());
                Log.i(TAG, "蓝牙连接 写入数据： bluetoothResult " + bluetoothResult );
            }
        }

        @Override
        public void bleServicesDiscoveredCharacteristic(BluetoothGatt gatt, String action, List<BluetoothGattCharacteristic> characteristicList) {

        }

        @Override
        public void getCmdSn(Integer sn, byte[] btC6) {

        }

    };

    //scanRecords的格式转换
    static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        if (bytes != null && bytes.length > 0) {
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            String str = new String(hexChars);

            return str.toLowerCase();
        }
        return "unknownData";
    }


    private void initService() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    mServiceConnection = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            // // zzt 20180627 改动，不再自动连接蓝牙设备 ，打开蓝牙服务但是不再直接扫描连接蓝牙
                            mBleService = ((BleService.LocalBinder) service).getService();
                            Log.i(TAG, "绑定蓝牙服务成功");
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            Log.i(TAG, "绑定蓝牙服务失败");
                        }
                    };
                    // 服务

                    Intent gattServiceIntent = new Intent(mContext, BleService.class);
                    mContext.bindService(gattServiceIntent, mServiceConnection, mContext.BIND_AUTO_CREATE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void readRssi() {
        mRiisTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mBleService != null) {
                    mBleService.readRssi(new MyInterfaceRssi(){
                        @Override
                        public void getRssi(int rssi, int status) {
                            Log.i(TAG, "获取到的信号强度：" + rssi + " - 状态：" + status  );
                        }
                    });
                }
            }
        };
        mRssiTimer = new Timer();
        mRssiTimer.schedule(mRiisTimerTask, 200, 200);
    }

    public void closeReadRssi() {
        if (mRiisTimerTask != null) {
            mRiisTimerTask.cancel();
            mRiisTimerTask = null;
        }
        if (mRssiTimer != null) {
            mRssiTimer.cancel();
            mRssiTimer = null;
        }
    }




}
