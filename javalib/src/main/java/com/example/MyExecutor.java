package com.example;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import sun.applet.Main;

/**
 * 线程池
 * Created by zeting
 * Date 19/2/12.
 */

public class MyExecutor {
    /**
     * int corePoolSize  => 该线程池中核心线程数最大值
     * int maximumPoolSize  => 该线程池中线程总数最大值
     * long keepAliveTime  => 该线程池中非核心线程闲置超时时长
     * TimeUnit unit  => keepAliveTime的单位
     * BlockingQueue workQueue  => 该线程池中的任务队列
     */
    ThreadPoolExecutor mThread = new ThreadPoolExecutor( 3 , 10 , 5 , TimeUnit.SECONDS , new DelayQueue() );

    /**
     CachedThreadPool()
    可缓存线程池：
    线程数无限制（没有核心线程，全部是非核心线程）
    有空闲线程则复用空闲线程，若无空闲线程则新建线程
    一定程序减少频繁创建/销毁线程，减少系统开销
    适用场景：适用于耗时少，任务量大的情况
     */

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
    FixedThreadPool()
    定长线程池：
    有核心线程，核心线程数就是线程的最大数量（没有非核心线程）
    可控制线程最大并发数（同时执行的线程数）
    超出的线程会在队列中等待
    创建方法：
    */
    //nThreads => 最大线程数即maximumPoolSize
    ExecutorService fixedThreadPool1 = Executors.newFixedThreadPool( 10 );

    //threadFactory => 创建线程的方法！
    ExecutorService fixedThreadPool2 = Executors.newFixedThreadPool(10, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable , "线程报错了，就不要继续浪了" );
        }
    });

    /**
    ScheduledThreadPool()
    定时线程池：
    支持定时及周期性任务执行。
    有核心线程，也有非核心线程
            非核心线程数量为无限大
    适用场景：适用于执行周期性任务
    */
    //nThreads => 最大线程数即maximumPoolSize
    ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

    /**
    SingleThreadExecutor()
    单线程化的线程池：
    有且仅有一个工作线程执行任务
    所有任务按照指定顺序执行，即遵循队列的入队出队规则
    适用场景：适用于有顺序的任务应用场景
    */
    ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    /**
     * 创建一个单线程定时任务
     */
    ExecutorService singleScheduledThreadPool = Executors.newSingleThreadScheduledExecutor() ;



    public static void main(String[] args){

        Runnable command =  new Runnable(){
            @Override
            public void run(){
                try {
                    System.out.println("-:" +  Thread.currentThread().getName());
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < 100 ; i++) {
            ThreadPoolManager.getInstance().execute(command);
        }

    }


}
