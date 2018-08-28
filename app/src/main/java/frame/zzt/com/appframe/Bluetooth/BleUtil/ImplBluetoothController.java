package frame.zzt.com.appframe.Bluetooth.BleUtil;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;

/**
 * Created by allen on 18/8/22.
 */

public class ImplBluetoothController implements IBluetoothControl {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BluetoothGatt gatt;

    public ImplBluetoothController() {

    }

    public ImplBluetoothController(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public void setAdapter(BluetoothAdapter adapter) {
        bluetoothAdapter = adapter;
    }

    @Override
    public void setDeviceMacAddress(String address) {
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
    }

    @Override
    public void doSomething(Callback callback) {

    }

    @Override
    public void unlock(Context context, Callback callback) {
        if (isEnable() && bluetoothDevice != null) {
            //我们自己创建的GattCallback对象需要一个从Activity等传入来的Callback，和一个指定
            //相应action的int参数用于创建相对应的EventHandler。
            gatt = bluetoothDevice.connectGatt(context, false, new GattCallback(callback, EventHandlerFactory.ACTION_UNLOCK));
        } else {
            callback.onFailed("bluetooth is disable or device is null.");
        }
    }

    public void cancelEvent(){
        if(this.gatt != null){
            gatt.disconnect();
        }
    }

    private boolean isEnable() {
        return bluetoothAdapter.isEnabled();
    }


}
