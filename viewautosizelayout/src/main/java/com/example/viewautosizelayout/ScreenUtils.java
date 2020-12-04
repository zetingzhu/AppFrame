package com.example.viewautosizelayout;


import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;


/**
 * getScreenWidth     : 获取屏幕的宽度（单位：px）
 * getScreenHeight    : 获取屏幕的高度（单位：px）
 * getScreenDensity   : 获取屏幕密度
 * getScreenDensityDpi: 获取屏幕密度 DPI
 * setFullScreen      : 设置屏幕为全屏
 * setNonFullScreen   : 设置屏幕为非全屏
 * toggleFullScreen   : 切换屏幕为全屏与否状态
 * isFullScreen       : 判断屏幕是否为全屏
 * setLandscape       : 设置屏幕为横屏
 * setPortrait        : 设置屏幕为竖屏
 * isLandscape        : 判断是否横屏
 * isPortrait         : 判断是否竖屏
 * getScreenRotation  : 获取屏幕旋转角度
 * screenShot         : 截屏
 * isScreenLock       : 判断是否锁屏
 * setSleepDuration   : 设置进入休眠时长
 * getSleepDuration   : 获取进入休眠时长
 * isTablet           : 判断是否是平板
 * Created by zeting
 * Date 19/2/27.
 */

public final class ScreenUtils {
    private static Application application;

    public static void ScreenUtilsInit(Application app) {
        application = app;
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    public static int getScreenWidthDp() {
        return (int) (getScreenWidth() / getScreenDensity());
    }


    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) application.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public static int getScreenHeightDp() {
        return (int) (getScreenHeight() / getScreenDensity());
    }


    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static int getScreenDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }


    public static void setFullScreen(final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public static void setNonFullScreen(final Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static void toggleFullScreen(final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window window = activity.getWindow();
        if ((window.getAttributes().flags & fullScreenFlag) == fullScreenFlag) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                    | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static boolean isFullScreen(final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    public static void setLandscape(final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    public static void setPortrait(final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public static boolean isLandscape() {
        return application.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }


    public static boolean isPortrait() {
        return application.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }


    public static int getScreenRotation(final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    public static boolean isScreenLock() {
        KeyguardManager km =
                (KeyguardManager) application.getSystemService(Context.KEYGUARD_SERVICE);
        //noinspection ConstantConditions
        return km.inKeyguardRestrictedInputMode();
    }


    public static void setSleepDuration(final int duration) {
        Settings.System.putInt(
                application.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }


    public static int getSleepDuration() {
        try {
            return Settings.System.getInt(
                    application.getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }

}
