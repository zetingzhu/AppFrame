package frame.zzt.com.appframe.bluetooth.BleUtil;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;

/**
 * Created by allen on 18/8/22.
 */

public class UnlockEventHandler implements IBluetoothEventHandler {
    private BluetoothGattCharacteristic commandControl;
    private BluetoothGattCharacteristic notifyControl;
    private boolean result = false;//这个变量是用来衡量UnlockEventHandler 是否成功执行完自己的所有命令并得到正确的结果。

    private UnlockEventHandler(UnlockEventHandler.Builder builder) {
        this.commandControl = builder.commandControl;
        this.notifyControl = builder.notifyControl;
    }

    @Override
    public <T> void onHandlerEvent(BluetoothGatt gatt, T t) {
        if (t instanceof BluetoothGattCharacteristic) {
            handleGattCharacteristic(gatt, (BluetoothGattCharacteristic) t);
        }
        if (t instanceof BluetoothGattDescriptor) {
            handleGattDescriptor(gatt, (BluetoothGattDescriptor) t);
        }
    }

    @Override
    public void startEvent(BluetoothGatt gatt) {
        //这里是实现我的逻辑，大家不必多看，你要做什么东西，自由发挥吧。
        //处理你自己的逻辑。
    }

    private void handleGattDescriptor(BluetoothGatt gatt, BluetoothGattDescriptor descriptor) {
        //处理你自己的逻辑。
        //。。。。。。。这里是一大堆命令。
    }

    private void handleGattCharacteristic(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        byte[] value = characteristic.getValue();
        //处理你自己的逻辑。
        //。。。。。。。这里是一大堆命令。
        //如果这里你已经得到了你想要的结果，就可以把成员变量result设置为了true了，
        //记得完成所有读写操作后，执行gatt.disconnect()来正确关闭链接，否则GattCallback无法正确回调，
        //这个问题在上一篇文章当中说过。
    }

    @Override
    public boolean getResult() {
        return result;
    }

    static class Builder {
        private BluetoothGattCharacteristic commandControl;
        private BluetoothGattCharacteristic notifyControl;

        Builder setCommandControl(BluetoothGattCharacteristic commandControl) {
            this.commandControl = commandControl;
            return this;
        }

        Builder setNotifyControl(BluetoothGattCharacteristic notifyControl) {
            this.notifyControl = notifyControl;
            return this;
        }

        UnlockEventHandler build() {
            return new UnlockEventHandler(this);
        }
    }
}