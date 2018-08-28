package frame.zzt.com.appframe.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by allen on 18/8/22.
 */

public class MyBleAQPresenter19 {

    public final static String TAG = ActivityBluetooth4.class.getSimpleName();

    private ServiceConnection mServiceConnection;
    private BluetoothDevice currentBleDevice; // 蓝牙设备


    private BleService19 mBleService; // 蓝牙服务
    private BLETools19 mBletools;
    private Context mContext ;

    private Timer mRssiTimer; //蓝牙信号
    private TimerTask mRiisTimerTask; //蓝牙信号
    private Handler mHandler;
    //15秒搜索时间
    private static final long SCAN_PERIOD = 30000;

    BluetoothGattCharacteristic mGattCharacteristic ;

    public MyBleAQPresenter19(Context mContext) {
        this.mContext = mContext ;
        mHandler = new Handler();
    }

    /**
     * 最开始初始化
     */
    public void init(){
        mBletools = BLETools19.getInstance();
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
        if (!mBleService.initialize()) {
            Log.i(TAG, "Unable to initialize Bluetooth");
            return;
        }
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


        if (mGattCharacteristic != null) {

//        String open = "110046439a4f2a2866d0de2db4727c220cb92500" ;
//        String stop = "1200478dc081fb5127a9fe1c67952699b8a05d00" ;
            String open = "1100483ca30ae9dd7607e63e48f5d524a009f500";
            boolean booWirte = mBleService.wirteCharacteristic1(mGattCharacteristic, open);
            Log.i(TAG, "蓝牙连接 写入 开启 数据： booWirte " + booWirte);
        }else {
            Log.i(TAG, "蓝牙连接 写入 关闭 数据 错误： mGattCharacteristic " + mGattCharacteristic);
        }


    }

    //判断特征可读
    public static boolean ifCharacteristicReadable(BluetoothGattCharacteristic characteristic){
        return ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_READ) > 0);
    }

    //判断特征可写
    public static boolean ifCharacteristicWritable(BluetoothGattCharacteristic characteristic){
        return ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0 ||
                (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0);
    }

    //判断特征是否具备通知属性
    public static boolean ifCharacteristicNotifiable (BluetoothGattCharacteristic characteristic){
        return ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0 ||
                (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0);
    }

    /**
     * 关闭电门
     */
    public void closeBle(){

        if (mGattCharacteristic != null) {

//        String open = "110046439a4f2a2866d0de2db4727c220cb92500" ;
//        String stop = "1200478dc081fb5127a9fe1c67952699b8a05d00" ;
            String stop = "1200491acd9ed9e392458dac249acc5a8da8b400";
            boolean booWirte = mBleService.wirteCharacteristic1(mGattCharacteristic, stop);
            Log.i(TAG, "蓝牙连接 写入 关闭 数据： booWirte " + booWirte);
        }else {
            Log.i(TAG, "蓝牙连接 写入 关闭 数据 错误： mGattCharacteristic " + mGattCharacteristic);
        }
    }

    /**
     * 开始扫描并连接地址
     */
    public void startBleScanDevice(  ) {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mBletools.stopScan();
            }
        }, SCAN_PERIOD);
        mBletools.scanDevice(mContext, mScanDeviceCallback);//开始搜索


    }

    public void startBleScanDevice1(  ) {
        mBletools.scanDevice();
        // 注册广播接收器。
        // 接收蓝牙发现
        IntentFilter filterFound = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        mContext.registerReceiver(mReceiver, filterFound);

        IntentFilter filterStart = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        mContext.registerReceiver(mReceiver, filterStart);

        IntentFilter filterFinish = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        mContext.registerReceiver(mReceiver, filterFinish);

    }

    // 广播接收发现蓝牙设备
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);  //这个就是所获得的蓝牙设备。
//                mDevices.add(device );
                Log.i(TAG, "onScanDevice:  ====" + device.getName() + " - " +  device.getAddress() + " - " +  device.getType()    );
            }
        }
    };


    public void stopBleScanDevice( ) {
        mBletools.stopScan();
    }


    BLETools19.DeviceCallback mScanDeviceCallback = new BLETools19.DeviceCallback() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(ScanResult result) {
        }

        @Override
        public void onScanDevice(BluetoothDevice result) {
            Log.i(TAG, "onScanDevice:  ====" + result.getName() + " - " +  result.getAddress() + " - " +  result.getType()    );
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
        public void bleServicesDiscoveredCharacteristic(String action, List<BluetoothGattCharacteristic> characteristicList) {

            for (int i = 0; i < characteristicList.size(); i++) {
                BluetoothGattCharacteristic characteristic = characteristicList.get(i);
                Log.i(TAG, "蓝牙连接 特征值：uuid：" + characteristic.getUuid());
                Log.i(TAG, "蓝牙连接 characteristic 可读状态：" + ifCharacteristicReadable(characteristic) );
                Log.i(TAG, "蓝牙连接 characteristic 可写状态：" + ifCharacteristicWritable(characteristic) );
                Log.i(TAG, "蓝牙连接 characteristic 通知状态：" + ifCharacteristicNotifiable(characteristic) );

                if (ifCharacteristicWritable(characteristic) ){
                    mGattCharacteristic = characteristic ;
                    break;
                }

            }
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
                            mBleService = ((BleService19.LocalBinder) service).getService();
                            Log.i(TAG, "绑定蓝牙服务成功");
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            Log.i(TAG, "绑定蓝牙服务失败");
                        }
                    };
                    // 服务

                    Intent gattServiceIntent = new Intent(mContext, BleService19.class);
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
