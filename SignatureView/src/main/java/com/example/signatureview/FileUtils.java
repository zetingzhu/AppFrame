package com.example.signatureview;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @author: zeting
 * @date: 2020/9/16
 */
public class FileUtils {

    public static final String TAG = FileUtils.class.getSimpleName();

    public String getPath(Context context) {
        File filePath = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File[] files = context.getExternalFilesDirs(Environment.DIRECTORY_PICTURES);
            for (File file : files) {
                Log.e(TAG, "获取路据 0：" + file.getPath());
                Log.e(TAG, "获取路据 0：" + file.getAbsolutePath());
            }
        }

        filePath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.e(TAG, "获取路据 1：" + filePath.getPath());
        String filePathDir = context.getExternalFilesDir(null).getAbsolutePath();
        Log.e(TAG, "获取路据 2：" + filePathDir);

        String ePath = context.getExternalCacheDir().getAbsolutePath();
        Log.e(TAG, "获取路据 3：" + ePath);

        ePath = Environment.getExternalStoragePublicDirectory("").getAbsolutePath();
        Log.e(TAG, "获取路据 4：" + ePath);
        ePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e(TAG, "获取路据 5：" + ePath);

        String apkFilePath = context.getExternalFilesDir("apk").getAbsolutePath();
        Log.e(TAG, "获取路据 6：" + apkFilePath);
        return filePath == null ? "" : filePath.getPath();
    }

}
