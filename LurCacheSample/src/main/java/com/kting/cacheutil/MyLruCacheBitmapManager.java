package com.kting.cacheutil;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 用来缓存图片bitmap
 *
 * @author: zeting
 * @date: 2020/10/21
 */
public class MyLruCacheBitmapManager extends androidx.collection.LruCache<String, android.graphics.Bitmap> {
    private static volatile MyLruCacheBitmapManager instance = null;
    // 设置内存大小
    private static final int maxSize = 1024 * 1024 * 20;

    private MyLruCacheBitmapManager(int maxSize) {
        super(maxSize);
    }

    public static MyLruCacheBitmapManager getInstance() {
        if (instance == null) {
            synchronized (MyLruCacheBitmapManager.class) {
                if (instance == null) {
                    instance = new MyLruCacheBitmapManager(maxSize);
                }
            }
        }
        return instance;
    }

    @Override
    protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
        return value.getByteCount();
    }

    @Override
    protected void entryRemoved(boolean evicted, @NonNull String key, @NonNull Bitmap oldValue, @Nullable Bitmap newValue) {
        super.entryRemoved(evicted, key, oldValue, newValue);
        // 释放
//        if (oldValue != null) {
//            oldValue.recycle();
//            System.gc();
//        }
    }
}