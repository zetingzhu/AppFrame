package com.example.viewautosizelayout;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * @author: zeting
 * @date: 2020/11/26
 * 自动修改context密度
 */
public class AutoContextDensityUtil {

    private static final String TAG = AutoContextDensityUtil.class.getSimpleName();
    private static float sNonCompatDensity;
    private static float sNonCompatScaledDensity;

    private static void setCustomDensity(Activity activity, Application application) {
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNonCompatDensity == 0) {
            sNonCompatDensity = appDisplayMetrics.density;
            sNonCompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }

        final float targetDensity = appDisplayMetrics.widthPixels / 360;
        final float targetScaledDensity = targetDensity * (sNonCompatScaledDensity / sNonCompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    /**
     * 获取屏幕分辨率
     */
    public static void getWidthHeight(Application application) {
        ScreenUtils.ScreenUtilsInit(application);
        Log.d(TAG, "屏幕宽：" + ScreenUtils.getScreenWidth());
        Log.d(TAG, "屏幕高：" + ScreenUtils.getScreenHeight());
        Log.d(TAG, "屏幕密度dp：" + ScreenUtils.getScreenDensity());
        Log.d(TAG, "屏幕密度dpi：" + ScreenUtils.getScreenDensityDpi());
        Log.d(TAG, "屏幕宽(dp)：" + ScreenUtils.getScreenWidthDp());
        Log.d(TAG, "屏幕高(dp)：" + ScreenUtils.getScreenHeightDp());
    }

}
