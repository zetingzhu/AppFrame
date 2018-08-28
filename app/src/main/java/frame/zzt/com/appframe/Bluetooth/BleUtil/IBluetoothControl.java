package frame.zzt.com.appframe.Bluetooth.BleUtil;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;

/**
 * Created by allen on 18/8/22.
 */

public interface IBluetoothControl {
    void setAdapter(BluetoothAdapter adapter);
    void setDeviceMacAddress(String address);
    void doSomething(Callback callback);
    void unlock(Context context, Callback callback);

}
