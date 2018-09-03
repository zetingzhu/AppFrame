package frame.zzt.com.appframe.rxjava;

import android.app.ActionBar;
import android.icu.text.AlphabeticIndex;
import android.util.Log;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import frame.zzt.com.appframe.MyApplication;
import frame.zzt.com.appframe.greendao.DaoSession;
import frame.zzt.com.appframe.greendao.User;
import frame.zzt.com.appframe.greendao.UserDao;
import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by allen on 18/8/31.
 */

public class RxPersenter extends BasePresenter<RxView> {
    private String TAG = "ActivityRxJava";

    Observable<Integer> observable1;
    Consumer<Integer> consumer1;

    public RxPersenter(RxView baseView) {
        super(baseView);
    }

    public Observable getObservale1() {
        return observable1;
    }

    public Consumer getConsumer1() {
        return consumer1;
    }

    /**
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
     * Schedulers.newThread() 代表一个常规的新线程
     * AndroidSchedulers.mainThread() 代表Android的主线程
     */
    public void setSubscribe1() {
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


    public void readAllUser() {
        Observable.create(new ObservableOnSubscribe<List<User>>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<List<User>> e) throws Exception {

                        Log.i(TAG , "获取到的数据 开始"  );
                        DaoSession daoSession = MyApplication.getInstance().getDaoSession() ;
                        UserDao userDao = daoSession.getUserDao();
                        List<User> mlist1 = userDao.queryRaw("where email='13797745363' ");
                        Log.i(TAG , "获取到的数据1："+ mlist1.size()  );

                        QueryBuilder qb = userDao.queryBuilder();
                        qb.where(UserDao.Properties.Email.eq("13797745363"));
                        List<User> mlist = qb.list();
                        Log.i(TAG , "获取到的数据0："+ mlist.size()  );

                        e.onNext(mlist1);
                        e.onComplete();
                    }

                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<User> users) {
                        Log.i(TAG , "获取到的数据："+ users.toString() );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG , "获取数据 失败："+ e );
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
