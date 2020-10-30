package com.kting.cacheutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;


import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 物理缓存工具类
 *
 * @author: zeting
 * @date: 2020/10/22
 */
public class MyDiskCacheManager {
    private static final String TAG = MyDiskCacheManager.class.getSimpleName();
    private static volatile MyDiskCacheManager instance;
    private Context context;
    // 文件緩存
    private DiskLruCache diskLruCache;
    // 最大緩存
    private final int maxSize = 5;//单位M

    private MyDiskCacheManager(Context con) {
        this.context = con;
        if (diskLruCache == null) {
            try {
                File directory = CacheUtil.getDiskCacheDir(context, "disk_cache");
                Log.d(TAG, "存储地址：" + directory.getAbsolutePath());
                int appVersion = CacheUtil.getAppVersion(context);
                diskLruCache = DiskLruCache.open(directory, appVersion, 1, maxSize * 1024 * 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static MyDiskCacheManager getInstance(Context context) {
        if (instance == null) {
            synchronized (MyDiskCacheManager.class) {
                if (instance == null) {
                    instance = new MyDiskCacheManager(context);
                }
            }
        }
        return instance;
    }

    /**
     * 保存Object对象，Object要实现Serializable
     *
     * @param key
     * @param value
     */
    public boolean put(String key, Object value) {
        try {
            String md5Key = CacheUtil.toMd5Key(key);
            Log.d(TAG, "存储 key:" + key + " - 加密后 key：" + md5Key);
            DiskLruCache.Editor editor = diskLruCache.edit(md5Key);
            if (editor != null) {
                OutputStream os = editor.newOutputStream(0);
                boolean writeStatus = false;
                if (CacheUtil.writeObject(os, value)) {
                    editor.commit();
                    writeStatus = true;
                } else {
                    editor.abort();
                    writeStatus = false;
                }
                diskLruCache.flush();
                return writeStatus;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存Bitmap
     *
     * @param key
     * @param bitmap
     */
    public boolean putBitmap(String key, Bitmap bitmap) {
        return put(key, CacheUtil.bitmap2Bytes(bitmap));
    }

    /**
     * 保存Drawable
     *
     * @param key
     * @param value
     */
    public boolean putDrawable(String key, Drawable value) {
        return putBitmap(key, CacheUtil.drawable2Bitmap(value));
    }

    /**
     * 根据key获取保存对象
     *
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key) {
        try {
            key = CacheUtil.toMd5Key(key);
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                return (T) CacheUtil.readObject(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Bitmap
     *
     * @param key
     * @return
     */
    public Bitmap getBitmap(String key) {
        byte[] bytes = (byte[]) get(key);
        if (bytes == null) return null;
        return CacheUtil.bytes2Bitmap(bytes);
    }

    /**
     * 获取Drawable
     *
     * @param key
     * @return
     */
    public Drawable getDrawable(String key) {
        byte[] bytes = (byte[]) get(key);
        if (bytes == null) {
            return null;
        }
        return CacheUtil.bitmap2Drawable(context, CacheUtil.bytes2Bitmap(bytes));
    }

    public long size() {
        return diskLruCache.size();
    }

    public void setMaxSize(int maxSize) {
        diskLruCache.setMaxSize(maxSize * 1024 * 1024);
    }

    public File getDirectory() {
        return diskLruCache.getDirectory();
    }

    public long getMaxSize() {
        return diskLruCache.getMaxSize();
    }

    public boolean remove(String key) {
        try {
            key = CacheUtil.toMd5Key(key);
            return diskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void delete() {
        try {
            diskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flush() {
        try {
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            diskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return diskLruCache.isClosed();
    }
}
