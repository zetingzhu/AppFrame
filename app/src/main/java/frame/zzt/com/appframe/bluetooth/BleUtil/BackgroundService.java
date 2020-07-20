package frame.zzt.com.appframe.bluetooth.BleUtil;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

/**
 * Created by allen on 18/8/22.
 */

public class BackgroundService extends Service {
    private IBluetoothControl bluetoothController;
    private Binder binder;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new Binder();
        initBluetooth();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void initBluetooth() {
        BluetoothAdapter bluetoothAdapter = getBluetoothAdapter();

        if (bluetoothAdapter != null) {
            bluetoothAdapter.enable();
            bluetoothController = new ImplBluetoothController(bluetoothAdapter);
        }else {
            bluetoothController = new ImplBluetoothController();
        }
    }

    private BluetoothAdapter getBluetoothAdapter(){
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        return bluetoothManager != null ? bluetoothManager.getAdapter() : null;
    }

    class Binder extends android.os.Binder {
        IBluetoothControl getBluetoothController() {
            return bluetoothController;
        }
    }

}
