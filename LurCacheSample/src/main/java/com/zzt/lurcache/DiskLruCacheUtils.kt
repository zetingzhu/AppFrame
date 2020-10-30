package com.zzt.lurcache

import android.content.Context
import android.os.Environment
import android.os.Environment.isExternalStorageRemovable
import android.os.Looper
import com.jakewharton.disklrucache.DiskLruCache
import java.io.*

/**
 * @author: zeting
 * @date: 2020/10/21
 */
class DiskLruCacheUtils {
    companion object {
        /**
         * 在cache下的次级目录
         */
        private const val adDir = "AD"

        /**
         * 这里只缓存了一个文件, 所以写死了唯一的文件名
         */
        private const val imgName = "ad_picture"

        var mContext: Context? = null

        fun DiskLruCacheUtils(context: Context) {
            this.mContext = context
        }

        /**
         * 开启DiskLruCache, 从inputStream中获取缓存数据
         */
        fun saveADCache(inputStream: InputStream) {
            // 要求必须子线程执行
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw Exception("CalledFromWrongThreadException")
            }
            // 四个参数的含义: 缓存的目录, 版本号, valueCount(同一个key对应几个value), 缓存大小限制
            // 其中版本号变更后缓存会被清空, 一般无需更改, 填1即可;
            // valueCount一般也设置为1, 即每个key对应一个value
            val diskLruCache = DiskLruCache.open(getADCacheDir(), 1, 1, 1024 * 1024 * 50)
            // 乐观锁思想, 并发时再获取一遍
            val editor = diskLruCache.edit(imgName) ?: diskLruCache.edit(imgName)
            val outputStream = editor?.newOutputStream(0)

            val bufferIn = BufferedInputStream(inputStream)
            val bufferOut = BufferedOutputStream(outputStream)
            try {
                var b: Int
                while (true) {
                    b = bufferIn.read()
                    if (b != -1) {
                        bufferOut.write(b)
                    } else {
                        break
                    }
                }
                // 输出流写完后, 要执行提交操作
                editor?.commit()
            } catch (e: IOException) {
                // 写出失败时, 要回滚
                editor?.abort()
            } finally {
                bufferIn.close()
                bufferOut.close()
                diskLruCache.flush()
            }
        }

        /**
         * 删除缓存
         */
        fun deleteADCache() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw Exception("CalledFromWrongThreadException")
            }
            if (hasADCache()) {
                val diskLruCache = DiskLruCache.open(getADCacheDir(), 1, 1, 1024 * 1024 * 50)
                diskLruCache.remove(imgName)
            }
        }

        /**
         * 检测缓存是否存在
         */
        fun hasADCache(): Boolean {
            val diskLruCache = DiskLruCache.open(getADCacheDir(), 1, 1, 1024 * 1024 * 50)
            return diskLruCache.get(imgName) != null
        }

        /**
         * 获取缓存文件的路径
         */
        fun getADCachePath(): String {
            return getADCacheDir().path + File.separator + imgName + ".0"
        }

        /**
         * 获取缓存存储的位置: data/package/cache/AD/
         */
        private fun getADCacheDir(): File {
            val externalStorageAvailable = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
            val cachePath = if (externalStorageAvailable) mContext?.externalCacheDir?.path else mContext?.cacheDir?.path
            return File(cachePath + File.separator + adDir + File.separator)
        }

        fun getDiskCacheDir(context: Context, uniqueName: String): File? {
            val cachePath: String? = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !isExternalStorageRemovable()) context?.getExternalCacheDir()?.getPath() else context.getCacheDir().getPath()
            return File(cachePath + File.separator.toString() + uniqueName)
        }
    }
}
