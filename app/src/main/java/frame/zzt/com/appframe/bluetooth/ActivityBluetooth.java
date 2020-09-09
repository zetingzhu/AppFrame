package frame.zzt.com.appframe.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.widget.Button;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * Created by allen on 18/8/21.
 */

public class ActivityBluetooth extends BaseAppCompatActivity implements BluetoothView {

    public final static String TAG = ActivityBluetooth.class.getSimpleName();

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;

    @BindView(R.id.btn_scan)
    Button btn_scan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);

        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setOperateTimeout(5000);

    }

    @OnClick(R.id.btn_scan)
    public void OnClickScan() {
        if (!BleManager.getInstance().isSupportBle()) {
            showtoast("不支持蓝牙ble");
            return;
        }
        if (!BleManager.getInstance().isBlueEnable()) {
            Log.i(TAG, "蓝牙没有打开");

            openBluetooth();
            return;
        }


        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
//                .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
//                .setDeviceName(true, names)         // 只扫描指定广播名的设备，可选
//                .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
//                .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(30000)              // 扫描超时时间，可选，默认10秒
                .build();

        // 扫描时间配置为小于等于0，会实现无限扫描，直至调用BleManger.getInstance().cancelScan()来中止扫描。
        BleManager.getInstance().initScanRule(scanRuleConfig);


        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                Log.i(TAG, "Bluetooth onScanStarted ");
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
//                Log.i(TAG , "Bluetooth onLeScan "  ) ;
//                Log.d(TAG, "Bluetooth onLeScan  addr = " + bleDevice.getMac()  + " device name = " + bleDevice.getName());

            }

            @Override
            public void onScanning(BleDevice bleDevice) {
//                Log.i(TAG , "Bluetooth onScanning  name:"  + bleDevice.getName() + "  mac:" + bleDevice.getMac()
//                        + "  Rssi:" + bleDevice.getRssi() + "  Record:" + bleDevice.getScanRecord()   ) ;

                bleDevice.getScanRecord();

                Log.i(TAG, "onScanResult1:  ====" + bytesToHex(bleDevice.getScanRecord()));


//                BleAdvertisedData badata = parseAdertisedData(bleDevice.getScanRecord());
//                String deviceName = bleDevice.getName();
//                if( deviceName == null ){
//                    deviceName = badata.getName();
//                    Log.i(TAG , "Bluetooth onScanning  name:"  + bleDevice.getName() );
//                }


            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
                Log.i(TAG, "Bluetooth onScanFinished " + scanResultList.size());
            }
        });


    }


    public BleAdvertisedData parseAdertisedData(byte[] advertisedData) {
        List<UUID> uuids = new ArrayList<UUID>();
        String name = null;
        if (advertisedData == null) {
            return new BleAdvertisedData(uuids, name);
        }

        ByteBuffer buffer = ByteBuffer.wrap(advertisedData).order(ByteOrder.LITTLE_ENDIAN);
        while (buffer.remaining() > 2) {
            byte length = buffer.get();
            if (length == 0) break;

            byte type = buffer.get();
            switch (type) {
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                    while (length >= 2) {
                        uuids.add(UUID.fromString(String.format(
                                "%08x-0000-1000-8000-00805f9b34fb", buffer.getShort())));
                        length -= 2;
                    }
                    break;
                case 0x06: // Partial list of 128-bit UUIDs
                case 0x07: // Complete list of 128-bit UUIDs
                    while (length >= 16) {
                        long lsb = buffer.getLong();
                        long msb = buffer.getLong();
                        uuids.add(new UUID(msb, lsb));
                        length -= 16;
                    }
                    break;
                case 0x09:
                    byte[] nameBytes = new byte[length - 1];
                    buffer.get(nameBytes);
                    try {
                        name = new String(nameBytes, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    buffer.position(buffer.position() + length - 1);
                    break;
            }
        }
        return new BleAdvertisedData(uuids, name);
    }

    public class BleAdvertisedData {
        private List<UUID> mUuids;
        private String mName;

        public BleAdvertisedData(List<UUID> uuids, String name) {
            mUuids = uuids;
            mName = name;
        }

        public List<UUID> getUuids() {
            return mUuids;
        }

        public String getName() {
            return mName;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // requestCode 与请求开启 Bluetooth 传入的 requestCode 相对应
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK: {
                    // TODO 用户选择开启 Bluetooth，Bluetooth 会被开启
                    Log.i(TAG, "Bluetooth 会被开启 - 蓝牙的状态：" + BleManager.getInstance().isBlueEnable());
                }
                break;
                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    // TODO 用户拒绝打开 Bluetooth, Bluetooth 不会被开启
                    Log.i(TAG, "Bluetooth 不会被开启 - 蓝牙的状态：" + BleManager.getInstance().isBlueEnable());
                }
                break;
                default:
                    break;
            }
        }
    }
}
