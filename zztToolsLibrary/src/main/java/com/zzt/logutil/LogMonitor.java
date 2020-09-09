package com.zzt.logutil;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

/**
 * @author: zeting
 * @date: 2020/8/25
 */
public class LogMonitor {
    private static LogMonitor sInstance = new LogMonitor();
    //HandlerThread 这个其实就是一个thread，只不过相对于普通的thread 他对外暴露了一个looper而已。方便
    //我们和handler配合使用
    private HandlerThread mLogThread = new HandlerThread("BLOCKINFO");
    private Handler mIoHandler;
    //这个时间戳的值，通常设置成不超过1000，你可以调低这个数值来优化你的代码。数值越低 暴露的信息就越多
    private static final long TIME_BLOCK = 1000L;

    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
//            Log.d("BLOCK", "开始写入日志");
            StringBuilder sb = new StringBuilder();
            //把ui线程的block的堆栈信息都打印出来 方便我们定位问题
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.e("BLOCK", "开始写入日志" + sb.toString());
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }

    public void startMonitor() {
        //在time之后 再启动这个runnable 如果在这个time之前调用了removeMonitor 方法，那这个runnable肯定就无法执行了
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    public void removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
    }


}
