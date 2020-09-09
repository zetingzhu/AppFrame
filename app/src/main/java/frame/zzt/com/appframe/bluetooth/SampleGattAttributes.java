/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package frame.zzt.com.appframe.bluetooth;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }


    // 智慧芯服务UUID
    public static String DEVICE_SERVICE_UUID = "000028af-0000-1000-8000-00805f9b34fb";

    // 锁车、解锁特征
    public static String DEVICE_CHARACTERISTIC_UUID = "00002aa2-0000-1000-8000-00805f9b34fb";

    // 蓝牙操作锁车解锁特征值
    public static String DEVICE_KEY_UUID = "00002aa4-0000-1000-8000-00805f9b34fb";

    //调试
    public static String testServiceUuid = "0000180f-0000-1000-8000-00805f9b34fb";
    public static String testCharacteristicUuid = "00002a19-0000-1000-8000-00805f9b34fb";

}
