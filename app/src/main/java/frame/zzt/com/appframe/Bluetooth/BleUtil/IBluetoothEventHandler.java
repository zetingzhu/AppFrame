package frame.zzt.com.appframe.Bluetooth.BleUtil;

import android.bluetooth.BluetoothGatt;

/**
 * Created by allen on 18/8/22.
 */

public interface IBluetoothEventHandler {
    void startEvent(BluetoothGatt gatt);
    <T> void onHandlerEvent(BluetoothGatt gatt, T t);
    boolean getResult();
}
