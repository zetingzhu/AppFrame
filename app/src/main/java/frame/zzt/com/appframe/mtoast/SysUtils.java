package frame.zzt.com.appframe.mtoast;

import android.app.AppOpsManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: zeting
 * @date: 2020/7/2
 * 系统工具判断
 */
public class SysUtils {
    private static final String TAG = SysUtils.class.getSimpleName();

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 是否允许了通知的打开，只适用4.4及以上系统，对于4.4以下的系统一律返回false。
     * @return
     */
    public static boolean isNotificationEnabled( Application appContext) {

        //NotificationManagerCompat.areNotificationsEnabled();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            Logger.warn(TAG, "SDK版本小于" + Build.VERSION_CODES.KITKAT + "，无法获取通知是否打开");
            return true;
        }
        AppOpsManager mAppOps = (AppOpsManager) appContext.getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = appContext.getApplicationInfo();

        String pkg = appContext.getApplicationContext().getPackageName();

        int uid = appInfo.uid;

        Class appOpsClass = null; /* Context.APP_OPS_MANAGER */

        try {

            appOpsClass = Class.forName(AppOpsManager.class.getName());

            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (int) opPostNotificationValue.get(Integer.class);

            return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchMethodException e) {
            Log.e(TAG, e.getMessage());
        } catch (NoSuchFieldException e) {
            Log.e(TAG, e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage());
        }
        return false;
    }


    /**
     * Returns a SystemProperty
     *
     * @param propName The Property to retrieve
     * @return The Property, or NULL if not found
     */
    public static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    /**
     * 判断系统是否为MIUI。
     *
     * @return 如果是MIUI系统返回true，否则返回false。
     */
    public static boolean isMIUI() {
        String name = SysUtils.getSystemProperty("ro.miui.ui.version.name");
        return !isBlank(name);
        //return false;
    }

    /**
     * 判断字符是否为空字符串。
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs
     * @return 如果为空字符串返回true，否则返回false。
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }//end isBlank


}
