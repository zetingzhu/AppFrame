package frame.zzt.com.appframe.Bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

import static java.sql.DriverManager.println;

/**
 * 蓝牙作为外围设备
 * 服务端(也叫从机/外围设备/peripheral)
 * 服务端的核心类是 BluetoothGattServer 和 BluetoothLeAdvertiser
 */
public class ActivityBluetooth5 extends BaseAppCompatActivity implements BluetoothView {

    public final static String TAG = ActivityBluetooth5.class.getSimpleName() ;

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;


    public static String SERV_UUID = "00001834-0000-1000-8000-00805f9b34fb" ;
    public static String DESC_UUID = "00001835-0000-1000-8000-00805f9b34fb" ;
    public static String CHAR_READ_UUID = "00001836-0000-1000-8000-00805f9b34fb" ;
    public static String CHAR_WRITE_UUID = "00001837-0000-1000-8000-00805f9b34fb" ;


    private Context mContext ;
    BluetoothManager mBluetoothManager ;
    BluetoothAdapter mBluetoothAdapter;

    BluetoothGattServer bluetoothGattServer  ; // ble 服务端
    BluetoothLeAdvertiser bluetoothLeAdvertiser ;// ble 广播

    BluetoothGattCharacteristic characteristicRead ; //添加可读+通知characteristic
    BluetoothGattCharacteristic characteristicWrite ;//添加可写characteristic

    UUID UUID_SERVER = UUID.fromString(SERV_UUID) ;
    UUID UUID_DESCRIPTOR = UUID.fromString(DESC_UUID) ;
    UUID UUID_CHARREAD = UUID.fromString(CHAR_READ_UUID) ;
    UUID UUID_CHARWRITE = UUID.fromString(CHAR_WRITE_UUID) ;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this ;
        setContentView(R.layout.activity_bluetooth5);
        ButterKnife.bind(this);

        // 检查手机是否支持BLE，不支持则退出
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "您的设备不支持蓝牙BLE，将关闭", Toast.LENGTH_SHORT).show();
            finish();
            return ;
        }


        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
//        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            initGATTServer();
        } else {
            Toast.makeText(this, "当你的系统版本为5.0以上才支持蓝牙客户端操作", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 1.初始化BLE蓝牙广播Advertiser，配置指定UUID的服务
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initGATTServer() {
        // ============启动BLE蓝牙广播(广告) =================================================================================
        //广播设置(必须)
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY) //广播模式: 低功耗,平衡,低延迟
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH) //发射功率级别: 极低,低,中,高
                .setConnectable(true)//能否连接,广播分为可连接广播和不可连接广播
                .build();
        //广播数据(必须，广播启动就会发送)
        AdvertiseData advertiseData = new AdvertiseData.Builder()
                .setIncludeDeviceName(true)
                .setIncludeTxPowerLevel(true)
                .addManufacturerData(1, "11".getBytes()) //设备厂商数据，自定义
                .build();

        //扫描响应数据(可选，当客户端扫描时才发送)
        AdvertiseData scanResponseData = new AdvertiseData.Builder()
                .addManufacturerData(2, "22".getBytes()) //设备厂商数据，自定义
                .addServiceUuid(new ParcelUuid(UUID_SERVER))
                .setIncludeTxPowerLevel(true)
                .build();


        AdvertiseCallback callback = new AdvertiseCallback() {

            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                Log.e(TAG, "BLE advertisement added successfully");

                initServices(mContext);
            }

            @Override
            public void onStartFailure(int errorCode) {
                Log.e(TAG, "Failed to add BLE advertisement, reason: " + errorCode);
            }
        };

        bluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
        //开始广播
        bluetoothLeAdvertiser.startAdvertising(settings, advertiseData, scanResponseData, callback);
    }


    /**
     *  启动蓝牙服务端
     * @param context
     */
    private void initServices(Context context) {
        //这个指定的创建指定UUID的服务
        BluetoothGattService service = new BluetoothGattService(UUID_SERVER, BluetoothGattService.SERVICE_TYPE_PRIMARY);

        //添加指定UUID的可读characteristic
        characteristicRead = new BluetoothGattCharacteristic(UUID_CHARREAD,
                BluetoothGattCharacteristic.PROPERTY_READ | BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_READ);
        //添加可读characteristic的descriptor
        BluetoothGattDescriptor descriptor = new BluetoothGattDescriptor(UUID_DESCRIPTOR, BluetoothGattCharacteristic.PERMISSION_WRITE);
        characteristicRead.addDescriptor(descriptor);
        service.addCharacteristic(characteristicRead);

        //添加指定UUID的可写characteristic
        characteristicWrite = new BluetoothGattCharacteristic(UUID_CHARWRITE,
                BluetoothGattCharacteristic.PROPERTY_WRITE |
                        BluetoothGattCharacteristic.PROPERTY_READ |
                        BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                BluetoothGattCharacteristic.PERMISSION_WRITE | BluetoothGattCharacteristic.PERMISSION_READ );
        service.addCharacteristic(characteristicWrite);


        if (mBluetoothManager != null) {
            //创建GattServer服务器
            bluetoothGattServer = mBluetoothManager.openGattServer(context, bluetoothGattServerCallback);
        }
        bluetoothGattServer.addService(service);
    }

    /**
     * 服务事件的回调
     */
    private BluetoothGattServerCallback bluetoothGattServerCallback = new BluetoothGattServerCallback() {

        /**
         * 1.连接状态发生变化时
         * @param device
         * @param status
         * @param newState
         */
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            Log.e(TAG, String.format("1.onConnectionStateChange：device name = %s, address = %s", device.getName(), device.getAddress()));
            Log.e(TAG, String.format("1.onConnectionStateChange：status = %s, newState =%s ", status, newState));
            super.onConnectionStateChange(device, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.e(TAG, String.format("1.已经被 客户端 连接上：device name = %s, address = %s", device.getName(), device.getAddress()));
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.e(TAG, String.format("1.已经被 客户端 断开：device name = %s, address = %s", device.getName(), device.getAddress()));
            }
        }


        @Override
        public void onServiceAdded(int status, BluetoothGattService service) {
            super.onServiceAdded(status, service);
            Log.e(TAG, String.format(status == 0 ? "添加服务[%s]成功" : "添加服务[%s]失败,错误码:" + status, service.getUuid()));

        }

        /**
         *  2.客户端有读的请求是，服务端回调方法
         *  客户端调用 mBluetoothGatt.readCharacteristic(characteristic); 会回调此方法
         * @param device
         * @param requestId
         * @param offset
         * @param characteristic
         */
        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
            Log.e(TAG, String.format("2.onCharacteristicReadRequest：device name = %s, address = %s", device.getName(), device.getAddress()));
            Log.e(TAG, String.format("2.onCharacteristicReadRequest：requestId = %s, offset = %s", requestId, offset));

//            bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());

            String response = "Request_Read_" + (int) (Math.random() * 100); //模拟数据
            bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, response.getBytes());// 响应客户端
            Log.e(TAG, "客户端读取Characteristic[" + characteristic.getUuid() + "]:\n" + response);

        }

        /**
         * 3. onCharacteristicWriteRequest,接收具体的字节
         *  客户端 mBluetoothGatt.writeCharacteristic(characteristic); 会回调此方法
         * @param device
         * @param requestId
         * @param characteristic
         * @param preparedWrite
         * @param responseNeeded
         * @param offset
         * @param requestBytes
         */
        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] requestBytes) {
            Log.e(TAG, String.format("3.onCharacteristicWriteRequest：device name = %s, address = %s", device.getName(), device.getAddress()));
            Log.e(TAG, String.format("3.onCharacteristicWriteRequest：requestId = %s, preparedWrite=%s, responseNeeded=%s, offset=%s, value=%s", requestId, preparedWrite, responseNeeded, offset, bytesToHexString(requestBytes)));
            //特征被读取，在该回调方法中回复客户端响应成功
            bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, requestBytes);
            //4.处理响应内容
            saveDevice(requestBytes, device, requestId, characteristic);

        }


        /**
         * 5.描述被写入时，在这里执行 bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS...  收，触发 onCharacteristicWriteRequest
         * @param device
         * @param requestId
         * @param descriptor
         * @param preparedWrite
         * @param responseNeeded
         * @param offset
         * @param value
         */
        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
            Log.e(TAG, String.format("5.onDescriptorWriteRequest：device name = %s, address = %s", device.getName(), device.getAddress()));
            Log.e(TAG, String.format("5.onDescriptorWriteRequest：requestId = %s, preparedWrite = %s, responseNeeded = %s, offset = %s, value = %s,", requestId, preparedWrite, responseNeeded, offset, bytesToHexString(value)));

            // now tell the connected device that this was all successfull
            bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, value);
        }

        /**
         * 6.特征被读取。当回复响应成功后，客户端会读取然后触发本方法
         * @param device
         * @param requestId
         * @param offset
         * @param descriptor
         */
        @Override
        public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
            Log.e(TAG, String.format("6.onDescriptorReadRequest：device name = %s, address = %s", device.getName(), device.getAddress()));
            Log.e(TAG, String.format("6.onDescriptorReadRequest：requestId = %s", requestId));
//            super.onDescriptorReadRequest(device, requestId, offset, descriptor);
            bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, null);
        }


        @Override
        public void onNotificationSent(BluetoothDevice device, int status) {
            super.onNotificationSent(device, status);
            Log.e(TAG, String.format("7.onNotificationSent：device name = %s, address = %s", device.getName(), device.getAddress()));
            Log.e(TAG, String.format("7.onNotificationSent：status = %s", status));
        }

        @Override
        public void onMtuChanged(BluetoothDevice device, int mtu) {
            super.onMtuChanged(device, mtu);
            Log.e(TAG, String.format("8.onMtuChanged：mtu = %s", mtu));
        }

        @Override
        public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
            super.onExecuteWrite(device, requestId, execute);
            Log.e(TAG, String.format("9.onExecuteWrite：requestId = %s", requestId));
        }


    };

    BluetoothGattCharacteristic characteristic ;
    BluetoothDevice device ;
    int requestId ;

    /**
     * 4.处理响应内容
     *
     * @param reqeustBytes
     * @param device
     * @param requestId
     * @param characteristic
     */
    private void saveDevice(byte[] reqeustBytes, BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic) {
        Log.e(TAG, String.format("4.onResponseToClient：device name = %s, address = %s", device.getName(), device.getAddress()));
        Log.e(TAG, String.format("4.onResponseToClient：requestId = %s", requestId));
//        String msg = OutputStringUtil.transferForPrint(reqeustBytes);
        Log.e(TAG, "4.收到：" + new String(reqeustBytes)  );
        //println("4.收到:" + msg);
        //showText("4.收到:" + msg);

        String str = new String(reqeustBytes) + " notify hello>";
        characteristicRead.setValue(str.getBytes());
        boolean booNotify = bluetoothGattServer.notifyCharacteristicChanged(device, characteristicRead, false);

        Log.i(TAG, "4.响应：" + str + " - 响应状态" + booNotify);

        this.device = device ;
        this.characteristic = characteristic ;
        this.requestId = requestId ;

    }


    @OnClick(R.id.btn_notify1)
    public void OnClickNotify() {
        if (characteristic != null  && device != null && bluetoothGattServer != null ) {
            Log.i(TAG, "蓝牙连接 characteristic 可读状态：" + MyBleAQPresenter19.ifCharacteristicReadable(characteristic) );
            Log.i(TAG, "蓝牙连接 characteristic 可写状态：" + MyBleAQPresenter19.ifCharacteristicWritable(characteristic) );
            Log.i(TAG, "蓝牙连接 characteristic 通知状态：" + MyBleAQPresenter19.ifCharacteristicNotifiable(characteristic) );

            String mbyte = "123456789012345678"  ;
            characteristic.setValue(mbyte.getBytes());
            boolean booNotify =  bluetoothGattServer.notifyCharacteristicChanged(device, characteristic, false);

//            boolean boo = bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 0, mbyte);

//            boolean booWrite = .writeCharacteristic(characteristic);

//            Log.i(TAG, "发送通知状态:  boo" + boo );
            Log.i(TAG, "发送通知状态:  booNotify:" + booNotify);
        }else {
            Log.e(TAG, "想发送通知，发现各种数据为空");
            Toast.makeText(mContext , "想发送通知，发现各种数据为空" , Toast.LENGTH_SHORT).show();
        }
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
}
