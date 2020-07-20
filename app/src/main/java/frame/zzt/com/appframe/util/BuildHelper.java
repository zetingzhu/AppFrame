package frame.zzt.com.appframe.util;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

/**
 * 获取手机型号信息
 * Created by zeting
 * Date 18/12/28.
 */

public class BuildHelper {
    private static final String TAG = BuildHelper.class.getSimpleName();


    public static final String ROM_MIUI = "MIUI";//小米
    public static final String ROM_EMUI = "EMUI";//华为
    public static final String ROM_FLYME = "FLYME";//魅族
    public static final String ROM_OPPO = "OPPO";//OPPO
    public static final String ROM_SMARTISAN = "SMARTISAN";// 锤子
    public static final String ROM_VIVO = "VIVO";//VIVO
    public static final String ROM_QIKU = "QIKU";//360
    public static final String SAMSUNG = "SAMSUNG";// 三星系统

    private static final String KEY_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String KEY_VERSION_EMUI = "ro.build.version.emui";
    private static final String KEY_VERSION_OPPO = "ro.build.version.opporom";
    private static final String KEY_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String KEY_VERSION_VIVO = "ro.vivo.os.version";


    private static String sName;
    private static String sVersion;

    public static boolean isEmui() {
        return check(ROM_EMUI);
    }

    public static boolean isMiui() {
        return check(ROM_MIUI);
    }

    public static boolean isVivo() {
        return check(ROM_VIVO);
    }

    public static boolean isOppo() {
        return check(ROM_OPPO);
    }

    public static boolean isFlyme() {
        return check(ROM_FLYME);
    }

    public static boolean is360() {
        return check(ROM_QIKU) || check("360");
    }

    public static boolean isSmartisan() {
        return check(ROM_SMARTISAN);
    }

    public static boolean isSamsung() {
        return check(SAMSUNG);
    }

    public static String getName() {
        if (sName == null) {
            check("");
        }
        return sName;
    }

    public static String getVersion() {
        if (sVersion == null) {
            check("");
        }
        return sVersion;
    }

    public static boolean check(String rom) {
        if (sName != null) {
            return sName.equals(rom);
        }

        if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_MIUI))) {
            sName = ROM_MIUI;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_EMUI))) {
            sName = ROM_EMUI;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_OPPO))) {
            sName = ROM_OPPO;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_VIVO))) {
            sName = ROM_VIVO;
        } else if (!TextUtils.isEmpty(sVersion = getProp(KEY_VERSION_SMARTISAN))) {
            sName = ROM_SMARTISAN;
        } else {
            sVersion = Build.DISPLAY;
            if (sVersion.toUpperCase().contains(ROM_FLYME)) {
                sName = ROM_FLYME;
            } else {
                sVersion = Build.UNKNOWN;
                sName = Build.MANUFACTURER.toUpperCase();
            }
        }
        return sName.equals(rom);
    }

    public static String getProp(String name) {
        String line = null;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + name);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read prop " + name, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }


    /**
     * Build class所有的字段属性
     * Build.BOARD : Z91
     * Build.BOOTLOADER : unknown
     * Build.BRAND : FaDi
     * Build.CPU_ABI : arm64-v8a
     * Build.CPU_ABI2 :
     * Build.DEVICE : Z91
     * Build.DISPLAY : TEST_FaDi_Z91_S100_20180108
     * Build.FINGERPRINT : FaDi/Z91/Z91:7.1.1/N6F26Q/1515397384:user/release-keys
     * Build.HARDWARE : mt6739
     * Build.HOST : 69959bbb90c6
     * Build.ID : N6F26Q
     * Build.IS_DEBUGGABLE : true
     * Build.IS_EMULATOR : false
     * Build.MANUFACTURER : FaDi
     * Build.MODEL : Z91
     * Build.PERMISSIONS_REVIEW_REQUIRED : false
     * Build.PRODUCT : Z91
     * Build.RADIO : unknown
     * Build.SERIAL : 0123456789ABCDEF
     * Build.SUPPORTED_32_BIT_ABIS : [Ljava.lang.String;@305cf5e
     * Build.SUPPORTED_64_BIT_ABIS : [Ljava.lang.String;@f5c1f3f
     * Build.SUPPORTED_ABIS : [Ljava.lang.String;@578b00c
     * Build.TAG : Build
     * Build.TAGS : release-keys
     * Build.TIME : 1515397382000
     * Build.TYPE : user
     * Build.UNKNOWN : unknown
     * Build.USER : FaDi
     * Build.VERSION.ACTIVE_CODENAMES : [Ljava.lang.String;@f4ecd55
     * Build.VERSION.ALL_CODENAMES : [Ljava.lang.String;@bdb836a
     * Build.VERSION.BASE_OS :
     * Build.VERSION.CODENAME : REL
     * Build.VERSION.INCREMENTAL : 1515397384
     * Build.VERSION.PREVIEW_SDK_INT : 0
     * Build.VERSION.RELEASE : 7.1.1
     * Build.VERSION.RESOURCES_SDK_INT : 25
     * Build.VERSION.SDK : 25
     * Build.VERSION.SDK_INT : 25
     * Build.VERSION.SECURITY_PATCH : 2017-11-05
     */
    public static void getAllBuildInformation() {
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Log.w(TAG, "Build." + field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }

        Field[] fieldsVersion = Build.VERSION.class.getDeclaredFields();
        for (Field field : fieldsVersion) {
            try {
                field.setAccessible(true);
                Log.w(TAG, "Build.VERSION." + field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    // 手机制造商
    public static String getProduct() {
        return Build.PRODUCT;
    }

    // 系统定制商
    public static String getBrand() {
        return Build.BRAND;
    }

    // 硬件制造商
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    // 硬件名称
    public static String getHardWare() {
        return Build.HARDWARE;
    }

    // 型号
    public static String getMode() {
        return Build.MODEL;
    }

    // Android 系统版本
    public static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    // CPU 指令集，可以查看是否支持64位
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }
}
