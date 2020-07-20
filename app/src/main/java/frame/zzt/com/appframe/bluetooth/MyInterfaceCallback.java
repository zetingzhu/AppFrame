package frame.zzt.com.appframe.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import java.util.List;

/**
 * Created by allen on 18/8/21.
 */

public interface MyInterfaceCallback {
    void bleStateConnected(String action);
    void bleStateDisconnected(String action);
    void bleServicesDiscovered (String action , BluetoothGattCharacteristic characteristic);
    void bleServiceReadError (String action);
    void bleServiceRed (String action , BluetoothGattCharacteristic characteristic);
    void bleServicesDiscoveredCharacteristic (BluetoothGatt gatt , String action , List<BluetoothGattCharacteristic> characteristicList);
    void getCmdSn (Integer sn , byte[] btC6);
}
