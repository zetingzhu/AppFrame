package frame.zzt.com.appframe.bluetooth;

import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import frame.zzt.com.appframe.util.ByteUtil;

/**
 * Created by allen on 17/6/26.
 * 蓝牙服务
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BleService19 extends Service {
    private static final String TAG = "ActivityBluetooth serv";
    private IBinder iBinder = new BleService19.LocalBinder();

    public final static String ACTION_GATT_CONNECTED =
            "com.example.ble.service.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "com.example.ble.service.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "com.example.ble.service.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "com.example.ble.service.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA =
            "com.example.ble.service.EXTRA_DATA";
    public final static String ACTION_GATT_SERVICE_RED =
            "com.example.ble.service.ACTION_GATT_SERVICE_RED";

    public final static String ACTION_GET_RSSI = "ACTION_GET_RSSI"; //获取蓝牙信号
    public final static String ACTION_START_GETRSSI = "ACTION_START_GETRSSI";//

    /////////////额外添加
    public final static String ACTION_STOP_BLE = "ACTION_STOP_BLE";  //停止连接和扫描
    public final static String ACTION_START_BLE = "ACTION_START_BLE"; //开始扫描>>连接
    public final static String ACTION_GATT_SERVICE_READ_ERROR = "ACTION_GATT_SERVICE_READ_ERROR"; //错误

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;

    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;

    private int mConnectionState = STATE_DISCONNECTED;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    private BluetoothGattCharacteristic mCharacteristic;
    private BluetoothGattCharacteristic mCharacteristic2AA4;
    private int accessReadFrequency; // 访问读取方法次数
    public static BleService19 bleService;


    public static void setInsurance(BleService19 bleService1) {
        bleService = bleService1;
    }

    public static BleService19 getInsurance() {
        return bleService;
    }


    MyInterfaceCallback myInterfaceCallback;
    MyInterfaceRssi myInterfaceRssi;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean initialize() {
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.i(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.i(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean connect(final String address, MyInterfaceCallback myInterfaceCallback) {
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null && /*for test*/false) {
            Log.i(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }

        this.myInterfaceCallback = myInterfaceCallback;
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.i(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }


    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null) return null;

        return mBluetoothGatt.getServices();
    }

    BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {


        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            String intentAction;
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                myInterfaceCallback.bleStateConnected(intentAction);
                Log.i(TAG, "Connected to GATT server.");
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGatt.discoverServices());

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                mBluetoothGatt.close();
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.i(TAG, "Disconnected from GATT server.");
                myInterfaceCallback.bleStateDisconnected(intentAction);
            }
        }


        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            super.onReadRemoteRssi(gatt, rssi, status);
            final Intent intent = new Intent(ACTION_GET_RSSI);
            intent.putExtra("get_assi", rssi);
            sendBroadcast(intent);
            Log.i(TAG, "onReadRemoteRssi:rssi = " + rssi + "  status=" + status);

            myInterfaceRssi.getRssi(rssi, status);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            Log.i(TAG, "onServicesDiscovered: " + status);
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                List<BluetoothGattService> serviceList = getSupportedGattServices();
                for (int i = 0; i < serviceList.size(); i++) {
                    // 找到对应的UUID
//                    if (serviceList.get(i).getUuid().toString().equals( ActivityBluetooth5.SERV_UUID)) {
////                        mCharacteristic = serviceList.get(i).getCharacteristic(UUID.fromString( ActivityBluetooth5.CHAR_UUID_1));
////                        Log.i(TAG, "onReceive: ACTION_GATT_SERVICES_DISCOVERED111");
////                        myInterfaceCallback.bleServicesDiscovered(ACTION_GATT_SERVICES_DISCOVERED , mCharacteristic );
//                        myInterfaceCallback.bleServicesDiscoveredCharacteristic(gatt , ACTION_GATT_SERVICES_DISCOVERED , serviceList.get(i).getCharacteristics() );
//                    }

                    if (serviceList.get(i).getUuid().toString().equals(SampleGattAttributes.DEVICE_SERVICE_UUID)) {
                        myInterfaceCallback.bleServicesDiscoveredCharacteristic(gatt, ACTION_GATT_SERVICES_DISCOVERED, serviceList.get(i).getCharacteristics());
                    }

                    if (serviceList.get(i).getUuid().toString().equals(SampleGattAttributes.DEVICE_SERVICE_UUID)) {
                        mCharacteristic2AA4 = serviceList.get(i).getCharacteristic(UUID.fromString(SampleGattAttributes.DEVICE_KEY_UUID));
                        Log.i(TAG, "onReceive: ACTION_GATT_SERVICES_DISCOVERED111");
                        mBluetoothGatt.readCharacteristic(mCharacteristic2AA4);
                    }

                }

//                List<BluetoothGattService> serviceList1 = gatt.getServices();
//                BluetoothGattService gattService = serviceList1.get(0) ;
//                List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics() ;
//                BluetoothGattCharacteristic mCharacteristic = gattCharacteristics.get(0);
//                myInterfaceCallback.bleServicesDiscovered(ACTION_GATT_SERVICES_DISCOVERED , mCharacteristic );

            } else {
                Log.i(TAG, "onServicesDiscovered received: " + status);
            }
        }

        //当我们执行了readCharacteristic()方法后，结果会回调在此。
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i(TAG, "onCharacteristicRead: " + status);


            // BLE终端数据写入的事件
            if (status == BluetoothGatt.GATT_SUCCESS)
                Log.e(TAG,
                        "onCharRead "
                                + gatt.getDevice().getName()
                                + " read "
                                + characteristic.getUuid().toString()
                                + " -> "
                                + new String(characteristic.getValue())
                                + " -> "
                                + byte2HexStr(characteristic.getValue())
                );


            if (characteristic.getUuid().toString().equals(SampleGattAttributes.DEVICE_KEY_UUID)) {

                byte[] mKeyByte = new byte[2];
                System.arraycopy(characteristic.getValue(), 5, mKeyByte, 0, 2);
                int snStr1 = ByteUtil.byteHexToInt(mKeyByte);
                myInterfaceCallback.getCmdSn(snStr1, characteristic.getValue());
            }

            if (TextUtils.isEmpty(byte2HexStr(characteristic.getValue()))) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            accessReadFrequency++;
                            if (accessReadFrequency >= 6) { // 最多访问5次 如果拿不到数据则终止
                                myInterfaceCallback.bleServiceReadError(ACTION_GATT_SERVICE_READ_ERROR);
                                return;
                            }
                            readCharacteristic(mCharacteristic);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            } else {
                myInterfaceCallback.bleServiceRed(ACTION_GATT_SERVICE_RED, characteristic);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            Log.i(TAG, String.format("onCharacteristicWrite：device name = %s, address = %s", gatt.getDevice().getName(), gatt.getDevice().getAddress()));
            Log.i(TAG, String.format("onCharacteristicWrite：requestId = %s", new String(characteristic.getValue())));
            Log.i(TAG, "onCharacteristicWrite: status = " + status);


//            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
//                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
//            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//            mBluetoothGatt.writeDescriptor(descriptor);

            /**
             *  设置该特征值可以收到通知
             *  当服务端调用 bluetoothGattServer.notifyCharacteristicChanged(device, characteristicRead, false);
             *  客户端可以在 onCharacteristicChanged 方法中收到通知
             */
//            gatt.setCharacteristicNotification(characteristic, true);

            // 读取特征值
            readCharacteristic(characteristic);

            //写入后读取数据
//            readCharacteristic(mCharacteristic);
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(1000);
//                        accessReadFrequency = 0;
//                        readCharacteristic(mCharacteristic);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();


        }


        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            Log.i(TAG, "onDescriptorWrite: " + status);
        }

        /**
         * 当我们执行了gatt.setCharacteristicNotification或写入特征的时候，结果会回调在此
         */
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic) {
            Log.i(TAG, String.format("onCharacteristicChanged：device name = %s, address = %s", gatt.getDevice().getName(), gatt.getDevice().getAddress()));
            Log.i(TAG, String.format("onCharacteristicChanged：requestId = %s", new String(characteristic.getValue())));
            Log.i(TAG, "onCharacteristicChanged: " + new String(characteristic.getValue()));
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(BleService19.this, new String(characteristic.getValue()), Toast.LENGTH_SHORT).show();
                }
            });
        }


        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
            Log.i(TAG, "onDescriptorRead: ");
        }

        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            Log.i(TAG, "onMtuChanged: ");
        }


        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
            Log.i(TAG, "onReliableWriteCompleted: ");
        }
    };

    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
    }

    public void wirteCharacteristic(BluetoothGattCharacteristic characteristic, String md5Text) {

        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mCharacteristic = characteristic;
//        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE );
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);
        characteristic.setValue(bufferToHex(md5Text));
        boolean booWrite = mBluetoothGatt.writeCharacteristic(characteristic);
        Log.i("VehiclePresenter", "执行蓝牙写数据操作是否成功：" + booWrite);
    }

    /**
     * 通过蓝牙执行某些命令
     *
     * @param characteristic
     * @param md5Text
     * @return
     */
    public boolean wirteCharacteristic1(BluetoothGattCharacteristic characteristic, String md5Text) {

        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        mCharacteristic = characteristic;
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        characteristic.setValue(bufferToHex(md5Text));
        boolean booWrite = mBluetoothGatt.writeCharacteristic(characteristic);
        Log.i("VehiclePresenter", "执行蓝牙写数据操作是否成功：" + booWrite);


        return true;
    }

    /**
     * 写入字节数据
     *
     * @param characteristic
     * @param md5Text
     * @return
     */
    public boolean wirteCharacteristic2(BluetoothGattCharacteristic characteristic, String md5Text) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        mCharacteristic = characteristic;
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        characteristic.setValue(md5Text.getBytes());
        boolean booWrite = mBluetoothGatt.writeCharacteristic(characteristic);
        Log.i("VehiclePresenter", "执行蓝牙写数据操作是否成功：" + booWrite);
        return true;
    }

    public boolean wirteCharacteristic3(BluetoothGattCharacteristic characteristic, byte[] md5byte) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return false;
        }
        mCharacteristic = characteristic;
        characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        characteristic.setValue(md5byte);
        boolean booWrite = mBluetoothGatt.writeCharacteristic(characteristic);
        Log.i("VehiclePresenter", "执行蓝牙写数据操作是否成功：" + booWrite);
        return true;
    }

    public boolean readRssi(MyInterfaceRssi myInterfaceRssi) {
        this.myInterfaceRssi = myInterfaceRssi;
        if (mBluetoothGatt != null) {
            Log.i(TAG, "1readRssi: ");
            return mBluetoothGatt.readRemoteRssi();
        }
        return false;
    }

    public void closeDevice() {
        Log.i(TAG, "mBluetoothGatt.close()");
        if (mBluetoothGatt != null) {
            mBluetoothGatt.close();
        }
    }

    public void DisCloseDevice() {
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
    }

    public BluetoothGatt getBluetoothGatt() {
        if (mBluetoothGatt != null) {
            return mBluetoothGatt;
        }
        return null;
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * bytes转换成十六进制字符串
     *
     * @param
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }


    /**
     * 将16进制的字符串转换为字节数组
     *
     * @param message
     * @return 字节数组
     */
    public static byte[] getHexBytes(String message) {
        int len = message.length() / 2;
        char[] chars = message.toCharArray();
        String[] hexStr = new String[len];
        byte[] bytes = new byte[len];
        for (int i = 0, j = 0; j < len; i += 2, j++) {
            hexStr[j] = "" + chars[i] + chars[i + 1];
            bytes[j] = (byte) Integer.parseInt(hexStr[j], 16);
        }
        return bytes;
    }


    public static byte[] bufferToHex(String text) {
        byte[] arr = new byte[text.length() / 2];
        for (int i = 0; i < text.length() / 2; i++) {
            String subStr = text.substring(i * 2, i * 2 + 2);
            int byteValue = Integer.valueOf(subStr, 16);
            arr[i] = (byte) byteValue;
        }

        return arr;
    }

    public class LocalBinder extends Binder {
        public BleService19 getService() {
            return BleService19.this;
        }
    }
}
