package com.zzt.utils;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;


/**
 getScreenWidth     : 获取屏幕的宽度（单位：px）
 getScreenHeight    : 获取屏幕的高度（单位：px）
 getAppScreenWidth  : 获取应用屏幕的宽度（单位：px）
 getAppScreenHeight : 获取应用屏幕的高度（单位：px）
 getScreenDensity   : 获取屏幕密度
 getScreenDensityDpi: 获取屏幕密度 DPI
 setFullScreen      : 设置屏幕为全屏
 setNonFullScreen   : 设置屏幕为非全屏
 toggleFullScreen   : 切换屏幕为全屏与否状态
 isFullScreen       : 判断屏幕是否为全屏
 setLandscape       : 设置屏幕为横屏
 setPortrait        : 设置屏幕为竖屏
 isLandscape        : 判断是否横屏
 isPortrait         : 判断是否竖屏
 getScreenRotation  : 获取屏幕旋转角度
 screenShot         : 截屏
 isScreenLock       : 判断是否锁屏
 setSleepDuration   : 设置进入休眠时长
 getSleepDuration   : 获取进入休眠时长
 */
public final class ScreenUtils {
    /**
     * getScreenWidth     : 获取屏幕的宽度（单位：px）
     */
    public static int getScreenWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * getScreenHeight    : 获取屏幕的高度（单位：px）
     */
    public static int getScreenHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * getAppScreenWidth  : 获取应用屏幕的宽度（单位：px）
     */
    public static int getAppScreenWidth(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * getAppScreenHeight : 获取应用屏幕的高度（单位：px）
     */
    public static int getAppScreenHeight(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * getScreenDensity   : 获取屏幕密度
     */
    public static float getScreenDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * getScreenDensityDpi: 获取屏幕密度 DPI
     */
    public static int getScreenDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    /**
     * setFullScreen      : 设置屏幕为全屏
     */
    public static void setFullScreen(@NonNull final Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * setNonFullScreen   : 设置屏幕为非全屏
     */
    public static void setNonFullScreen(@NonNull final Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * toggleFullScreen   : 切换屏幕为全屏与否状态
     */
    public static void toggleFullScreen(@NonNull final Activity activity) {
        boolean isFullScreen = isFullScreen(activity);
        Window window = activity.getWindow();
        if (isFullScreen) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * isFullScreen       : 判断屏幕是否为全屏
     */
    public static boolean isFullScreen(@NonNull final Activity activity) {
        int fullScreenFlag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        return (activity.getWindow().getAttributes().flags & fullScreenFlag) == fullScreenFlag;
    }

    /**
     * setLandscape       : 设置屏幕为横屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    public static void setLandscape(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * setPortrait        : 设置屏幕为竖屏
     */
    @SuppressLint("SourceLockedOrientationActivity")
    public static void setPortrait(@NonNull final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * isLandscape        : 判断是否横屏
     */
    public static boolean isLandscape(Context mContext) {
        return mContext.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * isPortrait         : 判断是否竖屏
     */
    public static boolean isPortrait(Context mContext) {
        return mContext.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * getScreenRotation  : 获取屏幕旋转角度
     */
    public static int getScreenRotation(@NonNull final Activity activity) {
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

    /**
     * screenShot         : 截屏
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        return screenShot(activity, false);
    }

    /**
     *
     */
    public static Bitmap screenShot(@NonNull final Activity activity, boolean isDeleteStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        Bitmap bmp = ImageUtils.view2Bitmap(decorView);
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (isDeleteStatusBar) {
            int statusBarHeight = BarUtils.getStatusBarHeight(activity);
            return Bitmap.createBitmap(
                    bmp,
                    0,
                    statusBarHeight,
                    dm.widthPixels,
                    dm.heightPixels - statusBarHeight
            );
        } else {
            return Bitmap.createBitmap(bmp, 0, 0, dm.widthPixels, dm.heightPixels);
        }
    }

    /**
     * isScreenLock       : 判断是否锁屏
     */
    public static boolean isScreenLock(Context mContext) {
        KeyguardManager km =
                (KeyguardManager) mContext.getSystemService(Context.KEYGUARD_SERVICE);
        if (km == null) return false;
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * setSleepDuration   : 设置进入休眠时长
     */
    public static void setSleepDuration(Context mContext, final int duration) {
        Settings.System.putInt(
                mContext.getContentResolver(),
                Settings.System.SCREEN_OFF_TIMEOUT,
                duration
        );
    }

    /**
     * getSleepDuration   : 获取进入休眠时长
     */
    public static int getSleepDuration(Context mContext) {
        try {
            return Settings.System.getInt(
                    mContext.getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT
            );
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
        }
    }
}
