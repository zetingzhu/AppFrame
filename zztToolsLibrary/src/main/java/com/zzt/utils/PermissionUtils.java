package com.zzt.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.WindowManager;


import androidx.annotation.RequiresApi;

import com.zzt.constant.PermissionConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


/**
 permission              : 设置请求权限
 permissionGroup         : 设置请求权限组
 permission.explain      : 设置权限请求前的解释
 permission.rationale    : 设置拒绝权限后再次请求的回调接口
 permission.callback     : 设置回调
 permission.theme        : 设置主题
 permission.request      : 开始请求
 getPermissions          : 获取应用权限
 isGranted               : 判断权限是否被授予
 isGrantedWriteSettings  : 判断修改系统权限是否被授予
 requestWriteSettings    : 申请修改系统权限
 isGrantedDrawOverlays   : 判断悬浮窗权限是否被授予
 requestDrawOverlays     : 申请悬浮窗权限
 launchAppDetailsSettings: 打开应用具体设置
 */
public final class PermissionUtils {
    /**
     * Return the permissions used in application.
     *
     * @return the permissions used in application
     */
    public static List<String> getPermissions(Context mContext) {
        return getPermissions(mContext, mContext.getPackageName());
    }

    /**
     * Return the permissions used in application.
     *
     * @param packageName The name of the package.
     * @return the permissions used in application
     */
    public static List<String> getPermissions(Context mContext, final String packageName) {
        PackageManager pm = mContext.getPackageManager();
        try {
            String[] permissions = pm.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
            if (permissions == null) return Collections.emptyList();
            return Arrays.asList(permissions);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * Return whether <em>you</em> have been granted the permissions.
     *
     * @param permissions The permissions.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isGranted(Context mContext, final String... permissions) {
        Pair<List<String>, List<String>> requestAndDeniedPermissions = getRequestAndDeniedPermissions(mContext, permissions);
        List<String> deniedPermissions = requestAndDeniedPermissions.second;
        if (!deniedPermissions.isEmpty()) {
            return false;
        }
        List<String> requestPermissions = requestAndDeniedPermissions.first;
        for (String permission : requestPermissions) {
            if (!isGranted(mContext, permission)) {
                return false;
            }
        }
        return true;
    }

    private static Pair<List<String>, List<String>> getRequestAndDeniedPermissions(Context mContext, final String... permissionsParam) {
        List<String> requestPermissions = new ArrayList<>();
        List<String> deniedPermissions = new ArrayList<>();
        List<String> appPermissions = getPermissions(mContext);
        for (String param : permissionsParam) {
            boolean isIncludeInManifest = false;
            String[] permissions = PermissionConstants.getPermissions(param);
            for (String permission : permissions) {
                if (appPermissions.contains(permission)) {
                    requestPermissions.add(permission);
                    isIncludeInManifest = true;
                }
            }
            if (!isIncludeInManifest) {
                deniedPermissions.add(param);
                Log.e("PermissionUtils", "U should add the permission of " + param + " in manifest.");
            }
        }
        return Pair.create(requestPermissions, deniedPermissions);
    }

    /**
     * Return whether the app can draw on top of other apps.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isGrantedDrawOverlays(Context mContext) {
        return Settings.canDrawOverlays(mContext);
    }

}