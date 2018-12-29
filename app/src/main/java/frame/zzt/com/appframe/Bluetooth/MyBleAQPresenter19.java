package frame.zzt.com.appframe.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
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
import android.os.ParcelUuid;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import frame.zzt.com.appframe.Util.ByteUtil;

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

    Integer cmdsn = 0 ;

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

    /**
     *  写入数据
     */
    public void writeData(String str ){
        if (mGattCharacteristic != null) {
            Log.w(TAG, "蓝牙连接 写入  数据： booWirte " + str);


            Log.i(TAG, "蓝牙连接 写入  数据： mGattCharacteristic " + mGattCharacteristic.getUuid());
            boolean booWirte = mBleService.wirteCharacteristic2(mGattCharacteristic, str);
            Log.i(TAG, "蓝牙连接 写入  数据： booWirte " + booWirte);

        }else {
            Log.i(TAG, "蓝牙连接 写入  数据 错误： mGattCharacteristic " + mGattCharacteristic);
        }

    }

    /**
     *
     没有验证md5的时候 - 加密前：c1001600000000000000000044a4253c41510695d17e74646466786a6f4136776b41716e6a505043557848745154
     没有验证md5的时候 - 加密后：b964f1b8e606b694f6a116ee1868dd15
     蓝牙连接 写入wmd5值：c10016000000000000000000e606b694f6a116ee

     没有验证md5的时候 - 加密前：c2001600000000000000000044a4253c41510695d17e74646466786a6f4136776b41716e6a505043557848745154
     没有验证md5的时候 - 加密后：f77c12a7ba985c1d7b42d7a7c1bb4661
     蓝牙连接 写入wmd5值：c20016000000000000000000ba985c1d7b42d7a7

     * @param bt
     */
    public void writeDataOpenCloseLock(byte bt ){
        if (mGattCharacteristic != null) {

//            byte open = (byte) 0xC1;
//            byte close = (byte) 0xC2;
//            String wmd5 =  writeData(bt) ;
            byte[] wmd5 =  writeData(bt) ;
            Log.e(TAG, "蓝牙连接 写入wmd5值：" + ByteUtil.bytesToHex2( wmd5 ) );


            Log.i(TAG, "蓝牙连接 写入  数据： mGattCharacteristic " + mGattCharacteristic.getUuid());
            boolean booWirte = mBleService.wirteCharacteristic3(mGattCharacteristic, wmd5);
            Log.i(TAG, "蓝牙连接 写入  数据： booWirte " + booWirte);

        }else {
            Log.i(TAG, "蓝牙连接 写入  数据 错误： mGattCharacteristic " + mGattCharacteristic);
        }

    }


    /**
     * 0xC1 锁车
     * 0xC2 解锁
     * @param bt
     * @return
     */
    public byte[] writeData(byte bt){
        byte[] writeByte = new byte[20];

        writeByte[0] = bt ;
        byte [] cmdSnByte = ByteUtil.intToByteHex(cmdsn + 1);
        System.arraycopy(cmdSnByte, 0, writeByte, 1, 2);

        writeByte[3] = 0x00 ;
        byte[] dataByte = new byte[8];

        System.arraycopy(dataByte, 0, writeByte, 4, 8);

        Log.w(TAG, "没有验证md5的时候" + ByteUtil.bytesToHex2(  writeByte ) );

        // md5 验证 需要验证
        //前 12 字节数据 +
        byte[] byte12 = new byte[12] ;
        System.arraycopy(writeByte , 0 ,byte12 , 0 , 12);
        // 遥控器 ID +
//        byte[] keyId = "1009099844".getBytes() ;
        byte[] keyId = ByteUtil.intToByteArrayLittel(Integer.valueOf(1009099844))  ;
        // 需配对的产商信息+
        // 110481790
        String imei = "357550110481790" ;
        byte[] imeiByte = ByteUtil.intToByteArrayBig(Integer.valueOf(imei.substring(6 , imei.length())));
        Log.w(TAG, "电车的imei:" +   ByteUtil.bytesToHex2(imeiByte)  );
        String mHex = "41510695d17e" ;
//        String mHex = "0695d17e" ;
        byte[] manufByte = ByteUtil.hexToByteArray(mHex) ;
        //固定 秘钥("tddfxjoA6wkAqnjPPCUxHtQT")
        byte[] byteStr = "tddfxjoA6wkAqnjPPCUxHtQT".getBytes() ;
        // 组合所有的byte数组
        byte[] byteMd5L = byteMerger(byte12 , keyId  , manufByte , byteStr) ;

        Log.w(TAG, "没有验证md5的时候 - 加密前：" + ByteUtil.bytesToHex2(  byteMd5L ) );
        // md5 加密后的byte
        byte[] md5DataDigest = MD5Util.getMD5Byte(byteMd5L);
        Log.w(TAG, "没有验证md5的时候 - 加密后：" + ByteUtil.bytesToHex2(  md5DataDigest ) );
        // md5 截取验证的字符串
        byte[] md5Byte = new byte[8];
        System.arraycopy(md5DataDigest, 4 , md5Byte, 0, 8);
        // 将MD5 数组放入到数据中
        System.arraycopy(md5Byte, 0, writeByte, 12, 8);


//        return  ByteUtil.bytesToHex2(writeByte) ;
        return  writeByte ;
    }


    public static byte[] byteMerger(byte[]... byteN ){
        int byteLength = 0 ;
        for (int i = 0; i <byteN.length ; i++) {
            byteLength += byteN[i].length ;
        }
        Log.i(TAG, "所有数组长度：" + byteLength);

        byte[] byteNew = new byte[byteLength];

        int byteIndex = 0 ;
        for (int j = 0; j < byteN.length ; j++) {
            byte[] bInd = byteN[j] ;
            System.arraycopy(bInd, 0, byteNew, byteIndex, bInd.length);
            byteIndex += bInd.length ;
        }
        return byteNew;
    }


    /**
     *  写入数据
     */
    public void readData(){
        if (mGattCharacteristic != null) {
            Log.w(TAG, "蓝牙连接 读取  数据：");
             mBleService.readCharacteristic(mGattCharacteristic);
        }else {
            Log.i(TAG, "蓝牙连接 读取  数据 错误： mGattCharacteristic " + mGattCharacteristic);
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
        Log.i(TAG, "扫描蓝牙");
        mBletools.scanDevice(mContext, mScanDeviceCallback);//开始搜索
    }

    public void startBleScanDevice50() {

        mBletools.scanDevice(1 , mContext, mScanDeviceCallback);//开始搜索
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

                typeOfJudge(device) ;

            }
        }
    };


    /**
     *  判断设备类型
     */
    public void typeOfJudge(BluetoothDevice device ){
        //如何判断 其他蓝牙设备实现了哪些Profile呢？即是如何判断某设备是媒体音频、电话音频的呢？
        int deviceClass = device.getBluetoothClass().getDeviceClass();
        int deviceClassMasked = deviceClass & 0x1F00;

        if(deviceClass == BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES)
        {
            //耳机
        }
        else if(deviceClass == BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE)
        {
            //麦克风
        }
        else if(deviceClassMasked == BluetoothClass.Device.Major.COMPUTER)
        {
            //电脑
        }
        else if(deviceClassMasked == BluetoothClass.Device.Major.PHONE)
        {
            //手机
        }
        else if(deviceClassMasked == BluetoothClass.Device.Major.HEALTH)
        {
            //健康类设备
        }
        else
        {
            //蓝牙：比如蓝牙音响。
        }
    }


    public void stopBleScanDevice( ) {
        mBletools.stopScan();
    }


    BLETools19.DeviceCallback mScanDeviceCallback = new BLETools19.DeviceCallback() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onScanResult(ScanResult result) {
            Log.i(TAG, "onScanResult:  ====" + result.getDevice().getName() + " - " +  result.getDevice().getAddress() );
            if (result.getDevice().fetchUuidsWithSdp()){
                ParcelUuid[] uuids = result.getDevice().getUuids();
                if (uuids != null) {
                    Log.i(TAG, "onScanResult:  ==== uuids:" + uuids.toString());
                }else {
                    Log.i(TAG, "onScanResult:  ==== uuids:" + uuids );
                }
            }
        }

        @Override
        public void onScanDevice(BluetoothDevice result) {
            Log.i(TAG, "onScanDevice:  ====" + result.getName() + " - " +  result.getAddress() + " - " +  result.getType()    );
            if (result.fetchUuidsWithSdp()){
                ParcelUuid[] uuids = result.getUuids();
                if (uuids != null) {
                    Log.i(TAG, "onScanDevice:  ==== uuids:" + uuids.toString()  );
                }else {
                    Log.i(TAG, "onScanDevice:  ==== uuids:" + uuids );
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

            Log.i(TAG, "蓝牙连接 读到的数据： bluetoothResult " + new String(data)  );

        }

        @Override
        public void bleServicesDiscoveredCharacteristic(BluetoothGatt gatt, String action, List<BluetoothGattCharacteristic> characteristicList) {
            for (int i = 0; i < characteristicList.size(); i++) {
                BluetoothGattCharacteristic characteristic = characteristicList.get(i);
                Log.i(TAG, "蓝牙连接 特征值：uuid：" + characteristic.getUuid());
                Log.i(TAG, "蓝牙连接 characteristic 可读状态：" + ifCharacteristicReadable(characteristic) );
                Log.i(TAG, "蓝牙连接 characteristic 可写状态：" + ifCharacteristicWritable(characteristic) );
                Log.i(TAG, "蓝牙连接 characteristic 通知状态：" + ifCharacteristicNotifiable(characteristic) );

                /**
                 *  如果是可通知的 设置通知属性
                 */
                if (ifCharacteristicNotifiable(characteristic)){
                    gatt.setCharacteristicNotification(characteristic, true);
                }

//                if (ifCharacteristicWritable(characteristic) ){
//                    mGattCharacteristic = characteristic ;
//                    break ;
//                }

                if (characteristic.getUuid().toString().equals(SampleGattAttributes.DEVICE_KEY_UUID)){
                    mGattCharacteristic = characteristic ;
                    break ;
                }

            }
        }

        @Override
        public void getCmdSn(Integer sn, byte[] btC6) {
            Log.i(TAG, "蓝牙连接 给我了cmd_sn " + sn   );
            cmdsn = sn ;

            /**
            String readData = ByteUtil.bytesToHex2(  btC6 ) ;
            // 截取获取到的md5 校验数据
            String writeMd5 = readData.substring(24 , 40);
            // md5 验证 需要验证
            Log.w(TAG, "验证前的： " + writeMd5   );

            // md5 验证 需要验证
            //前 12 字节数据 +
            byte[] byte12 = new byte[12] ;
            System.arraycopy(btC6 , 0 ,byte12 , 0 , 12);
            // 遥控器 ID +
//        byte[] keyId = "1009099844".getBytes() ;
            byte[] keyId = ByteUtil.intToByteArrayLittel(Integer.valueOf(1009099844))  ;
            // 需配对的产商信息+
            // 110481790
            String imei = "357550110481790" ;
            byte[] imeiByte = ByteUtil.intToByteArrayBig(Integer.valueOf(imei.substring(6 , imei.length())));
            Log.w(TAG, "电车的imei:" +   ByteUtil.bytesToHex2(imeiByte)  );
            String mHex = "41510695d17e" ;
//        String mHex = "0695d17e" ;
            byte[] manufByte = ByteUtil.hexToByteArray(mHex) ;
            //固定 秘钥("tddfxjoA6wkAqnjPPCUxHtQT")
            byte[] byteStr = "tddfxjoA6wkAqnjPPCUxHtQT".getBytes() ;
            // 组合所有的byte数组
            byte[] byteMd5L = byteMerger(byte12 , keyId  , manufByte , byteStr) ;


            Log.w(TAG, "没有验证md5的时候 - 加密前：" + ByteUtil.bytesToHex2(  byteMd5L ) );
            // md5 加密后的byte
            byte[] md5DataDigest = MD5Util.getMD5Byte(byteMd5L);
            Log.w(TAG, "没有验证md5的时候 - 加密后：" + ByteUtil.bytesToHex2(  md5DataDigest ) );

            // md5 加密后的字符串
            String md5DataDigestString = ByteUtil.bytesToHex2(  md5DataDigest ).substring(8 , 22) ;

            Log.i(TAG, "4.md5 验证：  md5DataDigest:" + md5DataDigest + "\n md5Cut:" + md5DataDigestString );
            if (writeMd5.equalsIgnoreCase(md5DataDigestString)) {
                Log.i(TAG, "验证MD5算法-成功" );
            }else {
                Log.i(TAG, "验证MD5算法-失败" );
            }
*/
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
