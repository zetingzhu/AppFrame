package frame.zzt.com.appframe.bluetooth.BleUtil;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.util.Log;

import java.util.List;

/**
 * Created by allen on 18/8/22.
 */

public class GattCallback extends BluetoothGattCallback {
    private IBluetoothEventHandler eventHandler;
    private Callback callback;
    private int action;

    GattCallback(Callback callback, int action) {
        this.callback = callback;
        this.action = action;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("shoppingCar", "gatt connected and try to discover services...");
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.i("shoppingCar", "gatt disconnected...");
                    //这就是为什么在eventHandler里面一定要设置result的结果并调用gatt.disconnect方法的理由，
                    //这么做也遵循了ble的操作规范，一次链接，一串命令，用完之后就关闭。
                    if(eventHandler.getResult()){
                        callback.onSuccess("operation success, callback.onSuccess is called...");
                    }else {
                        callback.onFailed("operation failed, callback.onFailed is called...");
                    }
                    gatt.close();
                    eventHandler = null;
                    callback = null;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            BluetoothGattCharacteristic commandControl = null;
            BluetoothGattCharacteristic notifyControl = null;
            List<BluetoothGattService> services = gatt.getServices();

            for (BluetoothGattService service : services) {
                List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                for (BluetoothGattCharacteristic characteristic : characteristics) {
                    String uuid = characteristic.getUuid().toString();
                    if (uuid.equals(CommandSet.UUID_CHARACTERISTIC_COMMAND_CONTROL)) {
                        commandControl = characteristic;
                    } else if (uuid.equals(CommandSet.UUID_CHARACTERISTIC_NOTIFY_CONTROL)) {
                        notifyControl = characteristic;
                    }
                }
            }

            if (commandControl != null && notifyControl != null) {
                Log.i("shoppingCar", "gatt found commandControl = " + commandControl.getUuid().toString());
                Log.i("shoppingCar", "gatt found notifyControl = " + notifyControl.getUuid().toString());

                //当gatt真实的发现了设备服务后，我们才去创建相应的eventHandler ，action
                //就是控制事件的参数，不同的action，工厂就会创建不同的eventHandler 来实现不同逻辑。
                eventHandler = EventHandlerFactory.create(commandControl, notifyControl, action);
                Log.i("shoppingCar", "eventHandler is created...");
                eventHandler.startEvent(gatt);
                Log.i("shoppingCar", "eventHandler is start to handle...");
            }else {
                Log.i("shoppingCar", "commandControl or notifyControl is null...");
                Log.i("shoppingCar", "eventHandler can not be created...");
                Log.i("shoppingCar", "gatt is going to disconnect...");

                gatt.disconnect();
            }
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            eventHandler.onHandlerEvent(gatt, characteristic);
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        eventHandler.onHandlerEvent(gatt, characteristic);
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        if (status == BluetoothGatt.GATT_SUCCESS) {
            eventHandler.onHandlerEvent(gatt, descriptor);
        }
    }


}
