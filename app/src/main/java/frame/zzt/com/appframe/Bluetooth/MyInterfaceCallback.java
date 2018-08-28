package frame.zzt.com.appframe.Bluetooth;

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
    void bleServicesDiscoveredCharacteristic (String action , List<BluetoothGattCharacteristic> characteristicList);
}
