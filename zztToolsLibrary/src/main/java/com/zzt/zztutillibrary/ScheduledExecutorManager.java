package com.zzt.zztutillibrary;

import java.util.concurrent.*;

/**
 * 定时线程工具类
 * Created by zeting
 * Date 2019-08-09.
 */
public class ScheduledExecutorManager   {

    private ScheduledExecutorService mExecutorService ;

    /**
     * 创建一个核心线程
     */
    public ScheduledExecutorManager( ) {
        startExecutorService(1) ;
    }

    /**
     * 创建多个核心线程
     * @param corePoolSize
     */
    public ScheduledExecutorManager( int corePoolSize ) {
        startExecutorService( corePoolSize ) ;
    }


    /**
     * 两次执行任务的起始时间规律相同,如果任务没有结束会延迟任务结束再次执行
     * @param command
     * @param initialDelay
     * @param period
     * @param unit
     * 在 initialDelay 后开始执行，然后在 initialDelay+period 后执行，接着在 initialDelay + 2 * period 后执行，依此类推。
     * 意思是下一次执行任务的时间与任务执行过程花费的时间无关，只与period有关！
     * 如果此任务的任何一个执行要花费比其周期更长的时间，则将推迟后续执行，但不会同时执行。 
     * 如果任务的任何一个执行遇到异常，则后续执行都会被取消。否则，只能通过执行程序的取消或终止方法来终止该任务。
     */
    public void scheduleAtFixedRate(Runnable command,
                                    long initialDelay,
                                    long period,
                                    TimeUnit unit){
        mExecutorService.scheduleAtFixedRate(command , initialDelay , period , unit ) ;
    }

    /**
     * 在每一次执行终止和下一次执行开始之间都存在给定的延迟。
     * 前后两次任务执行的时间间隔相同
     * @param command
     * @param initialDelay
     * @param period
     * @param unit
     */
    public void scheduleWithFixedDelay(Runnable command,
                                    long initialDelay,
                                    long period,
                                    TimeUnit unit){
        mExecutorService.scheduleWithFixedDelay(command , initialDelay , period , unit ) ;
    }

    /**
     * 创建线程池
     */
    private void  startExecutorService( int corePoolSize ){
        // 启动车辆定时器
        if ( scheduledIsTerminated() ){
            if ( corePoolSize > 1 ){
                mExecutorService = Executors.newScheduledThreadPool(corePoolSize) ;
            } else {
                mExecutorService = Executors.newSingleThreadScheduledExecutor();
            }
        }
    }

    /**
     * 判断线程池当前执行线程状态
     * @return true  线程池已关闭 false 线程池没有关闭
     */
    private boolean scheduledIsTerminated(){
        if ( mExecutorService != null){
            return mExecutorService.isTerminated() ;
        }
        if (mExecutorService == null){
            return true ;
        }
        return false ;
    }

    /**
     *  停止线程池
     */
    public void stopExecutorService (){
        if (mExecutorService != null){
            mExecutorService.shutdownNow() ;
            mExecutorService = null ;
        }
    }


}
