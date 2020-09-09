package com.zzt.logutil;

import android.os.Looper;
import android.util.Log;
import android.util.Printer;

/**
 * @author: zeting
 * @date: 2020/8/25
 * 确定思路设计抓取卡顿信息的方案。
 * 通过setMessageLogging方法来设置我们自定义的printer。
 * 自定义的printer 要重写 println 方法，判断如果是dispatch方法前后的日志格式输出，那么就要计算时间戳。
 * 超过这个时间戳就认为卡顿了，输出线程上下文堆栈信息 看看是哪里，哪个方法出现了卡顿。
 * <p>
 * 作者：DK_BurNIng
 * 链接：https://juejin.im/post/6844903567522791431
 * 来源：掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
public class CustomPrinterForGetBlockInfo {

    public static void start() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            //日志输出有很多种格式，我们这里只捕获ui线程中dispatch上下文的日志信息
            //所以这里定义了2个key值，注意不同的手机这2个key值可能不一样，有需要的话这里要做机型适配，
            //否则部分手机这里可能抓取不到日志信息
            private static final String START = ">>>>> Dispatching";
            private static final String END = "<<<<< Finished";

            @Override
            public void println(String x) {
//                Log.d("BLOCK", "开始写入日志 开始");
                //这里的思路就是如果发现在打印dispatch方法的 start信息，
                //那么我们就在 “时间戳” 之后 post一个runnable
                if (x.startsWith(START)) {
                    LogMonitor.getInstance().startMonitor();
                }
                //因为我们start 不是立即start runnable 而是在“时间戳” 之后 那么如果在这个时间戳之内
                //dispacth方法执行完毕以后的END到来，那么就会remove掉这个runnable
                //所以 这里就知道 如果dispatch方法执行时间在时间戳之内 那么我们就认为这个ui没卡顿，不输出任何卡顿信息
                //否则就输出卡顿信息 这里卡顿信息主要用StackTraceElement 来输出
                if (x.startsWith(END)) {
                    LogMonitor.getInstance().removeMonitor();
                }
//                Log.d("BLOCK", "开始写入日志 结束");
            }
        });
    }
}

