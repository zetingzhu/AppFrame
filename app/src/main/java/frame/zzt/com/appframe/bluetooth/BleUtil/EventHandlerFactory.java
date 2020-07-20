package frame.zzt.com.appframe.bluetooth.BleUtil;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by allen on 18/8/22.
 */

public class EventHandlerFactory {
    static final int ACTION_UNLOCK = 0x01; //开锁
    static final int ACTION_LOCK = 0x02; //关锁


    static IBluetoothEventHandler create(BluetoothGattCharacteristic commandControl, BluetoothGattCharacteristic notifyControl, int action) {
        switch (action) {
            case ACTION_UNLOCK:
                return new UnlockEventHandler.Builder()
                        .setCommandControl(commandControl)
                        .setNotifyControl(notifyControl)
                        .build();
            case ACTION_LOCK:

            default:
                return null;
        }
    }
}
