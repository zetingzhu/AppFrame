package frame.zzt.com.appframe.mtoast;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * @author: zeting
 * @date: 2020/7/2
 */
public class PermissionUtils {
    // 申请日历权限
    private static final int REQUEST_PERMISSION_CODE = 1234;
    private static final int REQUEST_ALERT_WINDOW_CODE = 1235;
    private static final int REQUEST_WRITE_SETTINGS_CODE = 1236;
    // 申请的权限
    private String[] permissions;
    // activity 对象
    private Activity mActivity;
    // 权限处理接口
    private PermissionListener permissionListener;

    public PermissionUtils(Activity mActivity, String[] permissions, PermissionListener permissionListener) {
        this.permissions = permissions;
        this.mActivity = mActivity;
        this.permissionListener = permissionListener;
    }


    /**
     * 检查并且申请权限
     * 日历权限 Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR
     * SYSTEM_ALERT_WINDOW and WRITE_SETTINGS, 这两个权限比较特殊，不能通过代码申请方式获取，必须得用户打开软件设置页手动打开，才能授权
     *
     * @param request
     */
    public boolean checkPermission(Boolean request) {
        if (mActivity != null) {
            if (checkSelfPermission(permissions)) {
                // 已经有了权限
                if (permissionListener != null) {
                    permissionListener.requestPermissions(true);
                }
                return true;
            } else {
                // 没有权限，
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0])) {
                    // 用户拒绝了权限，应该提示用户，为什么需要这个权限。
                    if (permissionListener != null) {
                        permissionListener.requestPermissions(false);
                        permissionListener.requestPermissionsRefusal();
                    }
                } else {
                    if (request) {
                        ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_PERMISSION_CODE);
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 检测所有权限是否都已经获取
     *
     * @return
     */
    private boolean checkSelfPermission(String[] permiss) {
        if (permiss != null && permiss.length > 0) {
            for (String permission : permiss) {
                if (ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 权限申请结果返回处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (mActivity != null) {
            if (requestCode == REQUEST_PERMISSION_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 申请权限 成功
                    if (permissionListener != null) {
                        permissionListener.requestPermissions(true);
                    }
                } else {
                    // 申请权限 失败
                    if (permissionListener != null) {
                        permissionListener.requestPermissions(false);
                    }
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permissions[0])) {
                        // 不在提示
                        if (permissionListener != null) {
                            permissionListener.requestPermissionsRefusal();
                        }
                    }
                }
            }
        }
    }

    /**
     * 进入到应用系统设置
     */
    public void openAppSysSetting() {
        if (mActivity != null) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mActivity.getApplicationContext().getPackageName(), null);
            intent.setData(uri);
            mActivity.startActivity(intent);
        }
    }

    /**
     * 权限处理接口
     */
    public interface PermissionListener {
        /**
         * 申请权限成功/失败
         */
        void requestPermissions(boolean status);


        /**
         * 申请权限已拒绝不在提示
         * 此时应该提示用户，应用为什么需要使用这个权限
         */
        void requestPermissionsRefusal();
    }

    /**
     * 申请 SYSTEM_ALERT_WINDOW 权限
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestDrawOverLays() {
        if (!Settings.canDrawOverlays(mActivity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + mActivity.getApplicationContext().getPackageName()));
            mActivity.startActivityForResult(intent, REQUEST_ALERT_WINDOW_CODE);
        } else {
            // 已经拥有 SYSTEM_ALERT_WINDOW 权限，执行addview或其他操作。
        }
    }


    /**
     * 申请  WRITE_SETTINGS
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestCanWrite() {
        if (!Settings.System.canWrite(mActivity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + mActivity.getApplicationContext().getPackageName()));
            mActivity.startActivityForResult(intent, REQUEST_WRITE_SETTINGS_CODE);
        } else {
            // 已经拥有 WRITE_SETTINGS 权限
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ALERT_WINDOW_CODE) {
            if (!Settings.canDrawOverlays(mActivity)) {
                // 未授予 SYSTEM_ALERT_WINDOW 权限...
            } else {
                //已经拥有 SYSTEM_ALERT_WINDOW 权限，执行addview或其他操作。
            }
        } else if (requestCode == REQUEST_WRITE_SETTINGS_CODE) {
            if (!Settings.System.canWrite(mActivity)) {
                // 未授予 WRITE_SETTINGS 权限...
            } else {
                //已经拥有 WRITE_SETTINGS 权限
            }
        }
    }
}
