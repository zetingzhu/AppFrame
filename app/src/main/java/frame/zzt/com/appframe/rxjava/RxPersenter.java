package frame.zzt.com.appframe.rxjava;

import android.util.Log;

import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;
import frame.zzt.com.appframe.mvp.mvpbase.BaseView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * Created by allen on 18/8/31.
 */

public class RxPersenter extends BasePresenter <RxView>{
    private String TAG = "ActivityRxJava";

    Observable<Integer> observable1 ;
    Consumer<Integer> consumer1 ;

    public RxPersenter(RxView baseView) {
        super(baseView);
    }

    public Observable getObservale1(){
        return  observable1 ;
    }
    public Consumer getConsumer1(){
        return  consumer1 ;
    }
    /**
    Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
    Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
    Schedulers.newThread() 代表一个常规的新线程
    AndroidSchedulers.mainThread() 代表Android的主线程
    */
    public void setSubscribe1(){
        observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
            }
        });

        consumer1 = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };
    }
}
