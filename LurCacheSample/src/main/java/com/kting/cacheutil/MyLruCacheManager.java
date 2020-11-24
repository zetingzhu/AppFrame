package com.kting.cacheutil;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.collection.LruCache;

/**
 * 内存缓存内的封装
 *
 * @author: zeting
 * @date: 2020/10/21
 */
public class MyLruCacheManager<V> extends LruCache<String, V> {
    private static volatile MyLruCacheManager instance = null;
    // 设置缓存的个数
    private static final int maxSize = 10;

    private MyLruCacheManager(int maxSize) {
        super(maxSize);
    }

    public static <V> MyLruCacheManager getInstance() {
        if (instance == null) {
            synchronized (MyLruCacheManager.class) {
                if (instance == null) {
                    instance = new MyLruCacheManager<V>(maxSize);
                }
            }
        }
        return instance;
    }

    @Override
    protected int sizeOf(@NonNull String key, @NonNull V value) {
        if (value instanceof Bitmap) {
            return ((Bitmap) value).getByteCount();
        }
        return super.sizeOf(key, value);
    }
}