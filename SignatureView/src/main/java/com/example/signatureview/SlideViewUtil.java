package com.example.signatureview;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @author: zeting
 * @date: 2020/9/16
 */
public class SlideViewUtil {
    public static final String TAG = SlideViewUtil.class.getSimpleName();
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    public File getSaveImgPath(Context context, String userId) {
        File fileName = null;
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && (hasExternalStoragePermission(context))) {
            // 获取签名路径
            File filePath = context.getExternalFilesDir("Signature");
            if (filePath != null) {
                if (!filePath.exists()) {
                    filePath.mkdirs();
                }
                fileName = new File(filePath, "userId_" + userId + "_" + System.currentTimeMillis() + ".png");
            }
        }
        if (fileName != null) {
            Log.d(TAG, "创建文件：" + fileName.getAbsolutePath());
        }
        return fileName;
    }


    /**
     * 保存图片
     *
     * @param path
     * @param cacheBitmap
     */
    public void saveViewToFile(String path, Bitmap cacheBitmap) {
        ByteArrayOutputStream bos = null;
        try {
            if (TextUtils.isEmpty(path)) {
                return;
            }
            bos = new ByteArrayOutputStream();
            cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] buffer = bos.toByteArray();
            if (buffer != null) {
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
                OutputStream os = new FileOutputStream(path);
                os.write(buffer);
                os.close();
                bos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getImgBase64(Bitmap cacheBitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        cacheBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] buffer = bos.toByteArray();
        return MyBase64.encode(buffer);
    }


    /**
     * 逐行扫描，清除边界空白
     *
     * @param blank 边界留多少个像素
     */
    public Bitmap clearBlank(Bitmap bmp, int blank, int color) {
        int height = bmp.getHeight();
        int width = bmp.getWidth();
        int top = 0, left = 0, right = 0, bottom = 0;
        int[] pixs = new int[width];
        boolean isStop;
        //扫描上边距不等于背景颜色的第一个点
        for (int i = 0; i < height; i++) {
            bmp.getPixels(pixs, 0, width, 0, i, width, 1);
            isStop = false;
            for (int pix : pixs) {
                if (pix != color) {
                    top = i;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }
        //扫描下边距不等于背景颜色的第一个点
        for (int i = height - 1; i >= 0; i--) {
            bmp.getPixels(pixs, 0, width, 0, i, width, 1);
            isStop = false;
            for (int pix :
                    pixs) {
                if (pix != color) {
                    bottom = i;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }
        pixs = new int[height];
        //扫描左边距不等于背景颜色的第一个点
        for (int x = 0; x < width; x++) {
            bmp.getPixels(pixs, 0, 1, x, 0, 1, height);
            isStop = false;
            for (int pix : pixs) {
                if (pix != color) {
                    left = x;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }
        //扫描右边距不等于背景颜色的第一个点
        for (int x = width - 1; x > 0; x--) {
            bmp.getPixels(pixs, 0, 1, x, 0, 1, height);
            isStop = false;
            for (int pix : pixs) {
                if (pix != color) {
                    right = x;
                    isStop = true;
                    break;
                }
            }
            if (isStop) {
                break;
            }
        }
        if (blank < 0) {
            blank = 0;
        }
        //计算加上保留空白距离之后的图像大小
        left = Math.max(left - blank, 0);
        top = Math.max(top - blank, 0);
        right = Math.min(right + blank, width - 1);
        bottom = Math.min(bottom + blank, height - 1);
        return Bitmap.createBitmap(bmp, left, top, right - left, bottom - top);
    }

    /**
     * 清除bitmap左右边界空白
     *
     * @param mBitmap 源图
     * @param blank   边距留多少个像素
     * @param color   背景色限定
     * @return 清除后的bitmap
     */
    public static Bitmap clearLRBlank(Bitmap mBitmap, int blank, int color) {
        if (mBitmap != null) {
            int height = mBitmap.getHeight();
            int width = mBitmap.getWidth();
            int left = 0, right = 0;
            int[] pixs = new int[height];
            boolean isStop;
            for (int x = 0; x < width; x++) {
                mBitmap.getPixels(pixs, 0, 1, x, 0, 1, height);
                isStop = false;
                for (int pix : pixs) {
                    if (pix != color) {
                        left = x;
                        isStop = true;
                        break;
                    }
                }
                if (isStop) {
                    break;
                }
            }
            for (int x = width - 1; x > 0; x--) {
                mBitmap.getPixels(pixs, 0, 1, x, 0, 1, height);
                isStop = false;
                for (int pix : pixs) {
                    if (pix != color) {
                        right = x;
                        isStop = true;
                        break;
                    }
                }
                if (isStop) {
                    break;
                }
            }
            if (blank < 0) {
                blank = 0;
            }
            left = Math.max(left - blank, 0);
            right = Math.min(right + blank, width - 1);
            return Bitmap.createBitmap(mBitmap, left, 0, right - left, height);
        } else {
            return null;
        }
    }

    /**
     * 判断写入权限
     *
     * @param context
     * @return
     */
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
}
