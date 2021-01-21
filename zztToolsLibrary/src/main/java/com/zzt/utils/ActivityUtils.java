package com.zzt.utils;

import android.app.Activity;
import android.os.Build;

/**
 addActivityLifecycleCallbacks   : 增加 Activity 生命周期监听
 removeActivityLifecycleCallbacks: 移除 Activity 生命周期监听
 getAliveActivityByContext       : 根据上下文获取存活的 Activity
 getActivityByContext            : 根据上下文获取 Activity
 isActivityExists                : 判断 Activity 是否存在
 startActivity                   : 启动 Activity
 startActivityForResult          : 启动 Activity 为返回结果
 startActivities                 : 启动多个 Activity
 startHomeActivity               : 回到桌面
 getActivityList                 : 获取 Activity 栈链表
 getLauncherActivity             : 获取启动项 Activity
 getMainActivities               : 获取主的 Activity 们
 getTopActivity                  : 获取栈顶 Activity
 isActivityAlive                 : 判断 Activity 是否存活
 isActivityExistsInStack         : 判断 Activity 是否存在栈中
 finishActivity                  : 结束 Activity
 finishToActivity                : 结束到指定 Activity
 finishOtherActivities           : 结束所有其他类型的 Activity
 finishAllActivities             : 结束所有 Activity
 finishAllActivitiesExceptNewest : 结束除最新之外的所有 Activity
 */
public final class ActivityUtils {

    /**
     * Return whether the activity is alive.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing()
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed());
    }

}
