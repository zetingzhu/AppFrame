package frame.zzt.com.appframe.rxjava;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.icu.text.AlphabeticIndex;
import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.greendao.query.QueryBuilder;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import frame.zzt.com.appframe.MyApplication;
import frame.zzt.com.appframe.greendao.DaoSession;
import frame.zzt.com.appframe.greendao.User;
import frame.zzt.com.appframe.greendao.UserDao;
import frame.zzt.com.appframe.modle.LoginResponse;
import frame.zzt.com.appframe.modle.MyWeather;
import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;
import frame.zzt.com.appframe.retrofit.ApiRetrofitWeather;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static android.R.id.list;


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

                Log.i(TAG, "获取到的数据 开始");
                DaoSession daoSession = MyApplication.getInstance().getDaoSession();
                UserDao userDao = daoSession.getUserDao();
                List<User> mlist1 = userDao.queryRaw("where email='13797745363' ");
                Log.i(TAG, "获取到的数据1：" + mlist1.size());

                QueryBuilder qb = userDao.queryBuilder();
                qb.where(UserDao.Properties.Email.eq("13797745363"));
                List<User> mlist = qb.list();
                Log.i(TAG, "获取到的数据0：" + mlist.size());

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
                        Log.i(TAG, "获取到的数据：" + users.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "获取数据 失败：" + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    /**
     * map是RxJava中最简单的一个变换操作符了, 它的作用就是对上游发送的每一个事件应用一个函数, 使得每一个事件都按照指定的函数去变化.
     */
    public void rxjavaMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, " 获得到的字符串： " + s);
            }
        });
    }


    /**
     * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里.
     * <p>
     * flatMap并不保证事件的顺序,  如果需要保证顺序则需要使用concatMap.
     */
    public void rxjavaFlatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    /**
     * ConcatMap 为有序的排列上游数据
     **/
    public void rxjavaConcatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onNext(6);
                emitter.onNext(7);
            }
        }).concatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                int delayTime = (int) (1 + Math.random() * 10);
                return Observable.fromIterable(list).delay(delayTime, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }


    /**
     * switchMap 如果是同一个线程就没有顺序发出所有数据，如果是异步线程就截取到最后最后 observable 流数据
     */
    public void rxjavaSwitchMap() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onNext(4);
//                emitter.onNext(5);
//                emitter.onNext(6);
//                emitter.onNext(7);
//            }
//        }).
        Observable.fromIterable(list)
                .switchMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                Log.i(TAG, "发送的数据："+ integer);
                return Observable.just("I am value " + integer);//.subscribeOn(Schedulers.io()) ;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    /**
     * switchMap 如果是同一个线程就没有顺序发出所有数据，如果是异步线程就截取到最后最后 observable 流数据
     */
    public void rxjavaSwitchMap2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

        Observable.fromIterable(list)
                .switchMap(new Function<Integer, ObservableSource<String>>() {

                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        Log.i(TAG, "发送的数据："+ integer);
                        return Observable.just("integer=" + integer).subscribeOn(Schedulers.newThread());
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        });
    }

    public void getWheatherBeijin() {
        ApiRetrofitWeather.getInstance().getApiService().getWheatherBeijin("北京")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<MyWeather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MyWeather myWeather) {
                        Log.i(TAG, "获取的登录数据login：" + myWeather.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "获取的失败login：" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "获取的成功login：");

                    }
                });
    }

    public void getWheatherGroup() {
        ApiRetrofitWeather.getInstance().getApiService().getWheatherGroup("cn", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<MyWeather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MyWeather myWeather) {
                        Log.i(TAG, "获取的登录数据login：" + myWeather.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "获取的失败login：" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "获取的成功login：");

                    }
                });
    }

    public void rxjavaFlatMap10() {
        ApiRetrofitWeather.getInstance().getApiService().getWheatherGroup("cn", "10")
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<MyWeather, ObservableSource<MyWeather>>() {
                    @Override
                    public ObservableSource<MyWeather> apply(@NonNull MyWeather myWeather) throws Exception {
                        Log.i(TAG, "获取第一次数据：" + myWeather.toString());
                        return ApiRetrofitWeather.getInstance().getApiService().getWheatherBeijin("北京");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<MyWeather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MyWeather myWeather) {
                        Log.i(TAG, "获取第二次数据：" + myWeather.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "获取的失败login：" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "获取的成功login：");
                    }
                });
    }

    public void rxjavaZip() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Log.d(TAG, "emit complete1");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread() );

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");
                Log.d(TAG, "emit B");
                emitter.onNext("B");
                Log.d(TAG, "emit C");
                emitter.onNext("C");
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    Disposable dd;

    public void rxjavaZip2() {
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
//                TimeUnit.MILLISECONDS.sleep(1000);
                SystemClock.sleep(1000);

                Log.d(TAG, "emit 2");
                emitter.onNext(2);
//                TimeUnit.MILLISECONDS.sleep(1000);
                SystemClock.sleep(1000);

                Log.d(TAG, "emit 3");
                emitter.onNext(3);
//                TimeUnit.MILLISECONDS.sleep(1000);
                SystemClock.sleep(1000);

                Log.d(TAG, "emit 4");
                emitter.onNext(4);
//                TimeUnit.MILLISECONDS.sleep(1000);
                SystemClock.sleep(1000);

                Log.d(TAG, "emit complete1");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");
//                TimeUnit.MILLISECONDS.sleep(1000);
                SystemClock.sleep(1000);

                Log.d(TAG, "emit B");
                emitter.onNext("B");
//                TimeUnit.MILLISECONDS.sleep(1000);
                SystemClock.sleep(1000);

                Log.d(TAG, "emit C");
                emitter.onNext("C");
//                TimeUnit.MILLISECONDS.sleep(1000);
                SystemClock.sleep(1000);

                Log.d(TAG, "emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());


        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                dd = d;
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
                if (dd.isDisposed()) {
                    dd.dispose();
                }
            }
        });
    }

    public void rxjavaZip3() {
        Observable<MyWeather> observable1 = ApiRetrofitWeather.getInstance().getApiService().getWheatherBeijin("北京").subscribeOn(Schedulers.io());

        Observable<MyWeather> observable2 = ApiRetrofitWeather.getInstance().getApiService().getWheatherGroup("cn", "10").subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<MyWeather, MyWeather, MyWeather.Basic>() {
            @Override
            public MyWeather.Basic apply(@NonNull MyWeather myWeather, @NonNull MyWeather myWeather2) throws Exception {
                List<MyWeather.HeWeather6> mList1 = myWeather.getHeWeather6();
                List<MyWeather.Basic> mBasic1 = mList1.get(0).getBasic();

                List<MyWeather.HeWeather6> mList2 = myWeather.getHeWeather6();
                List<MyWeather.Basic> mBasic2 = mList2.get(0).getBasic();

                for (MyWeather.Basic mBasic3 : mBasic1) {
                    for (MyWeather.Basic mBasic4 : mBasic2) {
                        if (mBasic3.getCid().equals(mBasic4.getCid())) {
                            return mBasic3;
                        }
                    }
                }
                return null;
            }
        }).subscribe(new Observer<MyWeather.Basic>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(@NonNull MyWeather.Basic basic) {
                Log.d(TAG, "onNext:" + basic.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    public void rxjavaOOM() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {    //无限循环发事件
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread.sleep(2000);
                        Log.d(TAG, "获取数据" + integer);
                    }
                });
    }

    public void rxjavaFlowable() {
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR ) ; //增加了一个参数

        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                s.request(Long.MAX_VALUE);  //注意这句代码
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        upstream.subscribe(downstream);
    }

    /***
     * Flowable里默认有一个大小为128的大小
     */
    Subscription mSubscription;

    public void rxjavaFlowable2() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
//                        mSubscription.request(2);
//                        mSubscription.cancel();
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public void rxjavaFlowableRequest(int n) {
        if (mSubscription != null) {
            mSubscription.request(n); //在外部调用request请求上游
        }
    }

    /**
     * BackpressureStrategy.ERROR       ERROR 最大存储 128 位
     * BackpressureStrategy.BUFFER      BUFFER 没有上线可以无限存储
     * BackpressureStrategy.DROP        Drop 就是直接把存不下的事件丢弃,  满了128 个后面的丢掉舍弃，下游获取到96个的时候整理上游被压数据，重新存储
     * BackpressureStrategy.LATEST      Latest 就是只保留最新的事件，替换最后一个
     * BackpressureStrategy.MISSING      MISSING 不丢弃，不缓存
     */
    public void rxjavaFlowable3() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 10000; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(128);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    public void rxjavaFlowableRequested() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "current requested: " + emitter.requested());
                    }
                }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(10);  //我要打十个!
                        s.request(100); //再给我一百个！
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    /**
     * emitter.requested() 当减到0时，则代表下游没有处理能力了，这个时候你如果继续发送事件，会抛出 MissingBackpressureException 异常
     */
    public void rxjavaFlowableRequested1() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "before emit, requested = " + emitter.requested());

                        Log.d(TAG, "emit 1");
                        emitter.onNext(1);
                        Log.d(TAG, "after emit 1, requested = " + emitter.requested());

                        Log.d(TAG, "emit 2");
                        emitter.onNext(2);
                        Log.d(TAG, "after emit 2, requested = " + emitter.requested());

                        Log.d(TAG, "emit 3");
                        emitter.onNext(3);
                        Log.d(TAG, "after emit 3, requested = " + emitter.requested());

                        Log.d(TAG, "emit complete");
                        emitter.onComplete();

                        Log.d(TAG, "after emit complete, requested = " + emitter.requested());

                    }
                }, BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
//                        s.request(10);  //
                        s.request(2);  // 如果只请求2个，上游继续发送的话会导致异常
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * flowable 异步的值大小是多少
     */
    public void rxjavaFlowableAsyn() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "current requested: " + emitter.requested());
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
                        s.request(1000);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * 每消费96个事件便会自动触发内部的request()去设置上游的requested的值
     */
    Subscription mSubscriptionAsyn;

    public void rxjavaFlowableAsynRequest(int n) {
        if (mSubscriptionAsyn != null) {
            mSubscriptionAsyn.request(n);
        }
    }

    public void rxjavaFlowableAsyn1() {
        Flowable
                .create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                        Log.d(TAG, "First requested = " + emitter.requested());
                        boolean flag;
                        for (int i = 0; ; i++) {
                            flag = false;
                            while (emitter.requested() == 0) {
                                if (!flag) {
                                    Log.d(TAG, "Oh no! I can't emit value!");
                                    flag = true;
                                }
                            }
                            emitter.onNext(i);
                            Log.d(TAG, "emit " + i + " , requested = " + emitter.requested());
                        }
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscriptionAsyn = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    /**
     * 异步读取显示文件内容
     */
    public void rxjavaFlowableAsynReadText(final Context con) {
        Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> emitter) throws Exception {
                        try {
//                            FileReader reader = new FileReader(  "将进酒.txt" );
                            InputStreamReader reader = new InputStreamReader(con.getResources().getAssets().open("将进酒.txt"));
                            BufferedReader br = new BufferedReader(reader);

                            String str;

                            while ((str = br.readLine()) != null && !emitter.isCancelled()) {
                                while (emitter.requested() == 0) {
                                    if (emitter.isCancelled()) {
                                        break;
                                    }
                                }
                                emitter.onNext(str);
                            }

                            br.close();
                            reader.close();

                            emitter.onComplete();
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    }
                }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<String>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        mSubscription = s;
                        s.request(1);
                    }

                    @Override
                    public void onNext(String string) {
                        Log.w(TAG, "  " + string);
                        try {
                            Thread.sleep(2000);
                            mSubscription.request(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }

    /**
     类型	        描述
     Observable<T>	能够发射0或n个数据，并以成功或错误事件终止。
     Flowable<T>	能够发射0或n个数据，并以成功或错误事件终止。 支持Backpressure，可以控制数据源发射的速度。
     Single<T>	    只发射单个数据或错误事件。
     Completable	它从来不发射数据，只处理 onComplete 和 onError 事件。可以看成是Rx的Runnable。
     Maybe<T>	    能够发射0或者1个数据，要么成功，要么失败。
     */

    /**
     * Single
     * <p>
     * 只发射一条单一的数据，或者一条异常通知，不能发射完成通知，其中数据与通知只能发射一个。
     * <p>
     * 1、方法void onSuccess(T t)用来发射一条单一的数据，且一次订阅只能调用一次，不同于Observale的发射器ObservableEmitter中的void onNext(@NonNull T value)方法，在一次订阅中，可以多次调用多次发射。
     * 2、方法void onError(Throwable t)等同于ObservableEmitter中的void onError(@NonNull Throwable error)用来发射一条错误通知
     * 3、方法onSuccess与onError只可调用一个
     * 若先调用onError 在调用onSuccess 则会导致onSuccess无效，
     * 若先调用onSuccess 在调用 onError 则会抛出io.reactivex.exceptions.UndeliverableException异常。
     */
    public void rxjavaSingle() {
        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Integer> e) throws Exception {
//                try {
//                    e.onSuccess(1);
//                    TimeUnit.MILLISECONDS.sleep(2000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                    e.onError(e1);
//                }
                e.onError(new Exception("Single error exception"));
                e.onSuccess(1);
            }
        }).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onSuccess(@NonNull Integer integer) {
                Log.d(TAG, "onSuccess: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.w(TAG, "onError: ", e);
            }
        });

    }

    /**
     * Completable
     * <p>
     * 只发射一条完成通知，或者一条异常通知，不能发射数据，其中完成通知与异常通知只能发射一个
     * 1、方法 onComplete()等同于Observale的发射器ObservableEmitter中的onComplete()，用来发射完成通知。
     * 2、方法 onError(Throwable e)等同于ObservableEmitter中的onError(Throwable e)，用来发射异常通知。
     * 3、方法 onComplete 与 onError只可调用一个
     * 若先调用 onError 在调用 onComplete 则会导致onSuccess无效，
     * 若先调用 onComplete 在调用 onError 则会抛出io.reactivex.exceptions.UndeliverableException异常。
     */
    public void rxjavaCompletable() {
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {
                e.onComplete();
//                e.onError(new Exception("Completable error exception "));
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.w(TAG, "onError: ", e);
            }
        });
    }

    /**
     * Maybe
     * <p>
     * 可发射一条单一的数据，以及发射一条完成通知，或者一条异常通知，其中完成通知和异常通知只能发射一个，发射数据只能在发射完成通知或者异常通知之前，否则发射数据无效。
     *
     * 1、onSuccess 方法可以多次调用，下游只会执行第一次
     * 2、onSuccess onComplete onErroe 只能执行一种，其他不能执行 执行多个将不显示
     *      若先执行 onSuccess 或者 onCompleate 在执行 onError 会抛出异常
     *
     */
    public void rxjavaMaybe() {
        Maybe.create(new MaybeOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull MaybeEmitter<Integer> e) throws Exception {
//                e.onSuccess(1);
//                e.onSuccess(2);
//                e.onSuccess(3);
                e.onComplete();
//                e.onError(new Exception("Maybe error exception "));
            }
        })
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        Log.d(TAG, "onSuccess: " + integer );
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.w(TAG, "onError: " ,  e);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /**
     Operators操作符

     create:基础创建操作符
     just:创建一个Observable，可接受一个或多个参数，将每个参数逐一发送
     fromArray:创建一个Observable，接受一个数组，并将数组中的数据逐一发送
     fromIterable：</b>创建一个Observable，接受一个可迭代对象，并将可迭代对象中的数据逐一发送
     range：</b>创建一个Observable，发送一个范围内的整数序列


     */

    public void rxjavaJust(){
        Observable.just(1 ,  2 , 3).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    public void rxjavaFromArray(){
        Integer [] str = new Integer[]{1 ,  2 , 3};
        Observable.fromArray(str)
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    public void rxjavaFromIterable(){
        List<Integer> mList = new ArrayList<>();
        mList.add(1);
        mList.add(2);
        mList.add(3);
        mList.add(4);
        Observable.fromIterable(mList)
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

   public void rxjavaRange(){
        Observable.range(1 ,  20).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    /**
     过滤操作（filter与distinct）：
     filter：filter使用Predicate 函数接口传入条件值，来判断Observable发射的每一个值是否满足这个条件，如果满足，则继续向下传递，如果不满足，则过滤掉。
     distinct:过滤掉重复的数据项，过滤规则为：只允许还没有发射过的数据项通过。
     */

    public void rxjavaFilterDistinct(){
        Observable
                .fromArray(1 , 1 , 2, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 9 )
//                .distinct()
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer );
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }


    /**
     * Observer只接收PublishSubject被订阅之后发送的数据。
     */
    public void rxjavaPublishSubject(){
        PublishSubject<String> subject = PublishSubject.create();
        subject.onNext("publicSubject1");
        subject.onNext("publicSubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("publicSubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("publicSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("publicSubject:complete");  //输出 publicSubject onComplete
            }
        });
        subject.onNext("publicSubject3");
        subject.onNext("publicSubject4");
        subject.onComplete();
        subject.onNext("publicSubject5");
    }

    /**
     * 该subject只有在被观察者（subject）结束的时候（即执行了onCompleted）后观察者才能够接收到事件，并且也只有能接收到最后一个事件。
     */
    public void rxjavaAsyncSubject(){
        AsyncSubject<String> subject = AsyncSubject.create();
        subject.onNext("asyncSubject1");
        subject.onNext("asyncSubject2");
        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("asyncSubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("asyncSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("asyncSubject:complete");  //输出 asyncSubject onComplete
            }
        });
        subject.onNext("asyncSubject3");
        subject.onNext("asyncSubject4");
        subject.onComplete();
        subject.onNext("asyncSubject5");
    }

    /**
     * Observer会接收到BehaviorSubject被订阅之前的最后一个数据，再接收订阅之后发射过来的数据。如果BehaviorSubject被订阅之前没有发送任何数据，则会发送一个默认数据。
     */
    public void rxjavaBehaviorSubject(){
        BehaviorSubject<String> subject = BehaviorSubject.createDefault("behaviorSubject0");
        subject.onNext("behaviorSubject1");
        subject.onNext("behaviorSubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("behaviorSubject:"+s);  //输出asyncSubject:asyncSubject3
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("behaviorSubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("behaviorSubject:complete");  //输出 behaviorSubject onComplete
            }
        });
        subject.onNext("behaviorSubject3");
        subject.onNext("behaviorSubject4") ;
    }

    /**
     * ReplaySubject会发射所有来自原始Observable的数据给观察者，无论它们是何时订阅的。
     * 事件不会直接被消化。下个观察者订阅的时候可以再次接收到事件。
     */
    public void rxjavaReplaySubject(){
        ReplaySubject<String> subject = ReplaySubject.create();
        subject.onNext("replaySubject1");
        subject.onNext("replaySubject2");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("replaySubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("replaySubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("replaySubject:complete");  //输出 replaySubject onComplete
            }
        });

        subject.onNext("replaySubject3");
        subject.onNext("replaySubject4");
    }

    public void rxjavaReplaySubject1(){
        ReplaySubject<String> subject = ReplaySubject.createWithSize(2);
        subject.onNext("replaySubject1");
        subject.onNext("replaySubject2");
        subject.onNext("replaySubject3");
        subject.onNext("replaySubject4");

        subject.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("replaySubject:"+s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                System.out.println("replaySubject onError");  //不输出（异常才会输出）
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("replaySubject:complete");  //输出 replaySubject onComplete
            }
        });

        subject.onNext("replaySubject5");
        subject.onNext("replaySubject6");
    }


    public void RxJavaMapRequest() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                Request.Builder builder = new Request.Builder()
                        .url(" https://search.heweather.com/find?key=4b61a68895b149f1a5ea53fe43782e17&location=上海")
                        .get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        }).map(new Function<Response, MyWeather>() {
            @Override
            public MyWeather apply(@NonNull Response response) throws Exception {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        Log.e(TAG, "map:转换前:" + response.body());
                        return new Gson().fromJson(body.string(), MyWeather.class);
                    }
                }
                return null;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<MyWeather>() {
                    @Override
                    public void accept(@NonNull MyWeather s) throws Exception {
                        Log.e(TAG, "doOnNext: 保存成功：" + s.toString() + "\n");
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyWeather>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MyWeather myWeather) {
                        Log.i(TAG, "获取天气数据 onNext ：" + myWeather.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "获取天气数据 onError ：" + e);
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "获取天气数据 onComplete ：");

                    }
                });
    }

    public void rxjavaConcat() {
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onComplete();
                e.onNext("3");
                e.onNext("4");
            }
        });
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>(){
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext("5");
                e.onNext("6");
                e.onComplete();
                e.onNext("7");
            }
        });
        Observable.concat(observable1, observable2)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe ：" + d.isDisposed() );
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Log.i(TAG, "onNext ：" + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError ：" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete ：" );
                    }
                });
    }

    public class Student {
        public String name;//学生名字
        public int id;
        public List<Source> mSources;//每个学生的所有课程

        public Student() {
        }
        public Student(String name, int id, List<Source> sources) {
            this.name = name;
            this.id = id;
            mSources = sources;
        }
    }

    public class Source {
        public int sourceId;//id
        public String name;//课程名
        public int score;//成绩

        public Source(int sourceId, String name, int score) {
            this.sourceId = sourceId;
            this.name = name;
            this.score = score;
        }
    }

    public List<Student> getStudentData(){
        List<Student> mStList = new ArrayList<>();
        List<Source> mSoList0 = new ArrayList<>();
        Source mSou0 = new Source( 1 , "语文" , 100 );
        Source mSou1 = new Source( 2 , "数学" , 99 );
        Source mSou2 = new Source( 2 , "英语" , 90 );
        mSoList0.add(mSou0);
        mSoList0.add(mSou1);
        mSoList0.add(mSou2);
        Student mStu0 = new Student("张一"  , 1  , mSoList0);
        mStList.add(mStu0);

        List<Source> mSoList1 = new ArrayList<>();
        Source mSou3 = new Source( 1 , "语文" , 90 );
        Source mSou4 = new Source( 2 , "数学" , 89 );
        Source mSou5 = new Source( 2 , "英语" , 88 );
        mSoList1.add(mSou3);
        mSoList1.add(mSou4);
        mSoList1.add(mSou5);
        Student mStu1 = new Student("张二"  , 2  , mSoList1);
        mStList.add(mStu1);

        return mStList ;
    }

    /**
     *  举例flatmap 使用，传统方法
     */
    public void rxjavaFlatMapEG1(){
        //开启一个线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 从服务器获取班级所有同学信息
                List<Student> students = getStudentData();
                for(int i=0;i<students.size();i++){
                    List<Source> sources = students.get(i).mSources;
                    String stu = "student Name:"+students.get(i).name;
                    Log.i(TAG,stu);
                    for (int index=0;index<sources.size();index++){
                        final Source source = sources.get(index);
                        //主线程更改UI
                        String content = "sourceName:"+source.name +" source score:"+source.score;
                        Log.i(TAG,content);
                    }
                }
            }
        }).start();
    }

    public void rxjavaFlatMapEG2(){
        Observable.fromIterable(getStudentData())
                .flatMap(new Function<Student, ObservableSource<Source>>() {
                    @Override
                    public ObservableSource<Source> apply(@NonNull Student student) throws Exception {
                        String stu = "student Name:"+student.name ;
                        Log.i(TAG,stu);
                        return Observable.fromIterable(student.mSources);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Source>() {
                    @Override
                    public void accept(@NonNull Source source) throws Exception {
                        String content = "sourceName:"+source.name +" source score:"+source.score;
                        Log.i(TAG,content);

                    }
                });
    }

    public void rxjavaBuffer(){
        Observable.just(1, 2, 3, 4, 5, 6).repeat(2)
                .buffer(3).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.i(TAG, "integers:" + integers );
            }
        });
    }

    public void rxjavaGroupBy(){
        Observable.range(7 , 7).groupBy(new Function<Integer, Object>() {
            @Override
            public Object apply(@NonNull Integer integer) throws Exception {
                return integer > 10 ? "1组" : "2组" ;
            }
        }).subscribe(new Consumer<GroupedObservable<Object, Integer>>() {
            @Override
            public void accept(final GroupedObservable<Object, Integer> objectIntegerGroupedObservable) throws Exception {
                Log.i(TAG, "onNext  - 1 :" + objectIntegerGroupedObservable.getKey() );
                objectIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe:" + d.isDisposed() );
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        System.out.println(  objectIntegerGroupedObservable.getKey() + ":" + String.valueOf(integer));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError:" + e.getMessage() );
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete" );
                    }
                });
            }
        });
    }

    public void rxjavaflatMapIterable () {
        Observable.range(1, 7).flatMapIterable(new Function<Integer, Iterable<?>>() {
            @Override
            public Iterable<String> apply(@NonNull Integer integer) throws Exception {
                ArrayList<String> mList = new ArrayList<>();
                mList.add("数字 : " + integer);
                return mList;
            }
        }).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe" + d.isDisposed());
            }

            @Override
            public void onNext(@NonNull Object o) {
                Log.i(TAG, "onNext" + o.toString() );
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError" );
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete" );
            }
        });
    }


    //throttleWithTimeout操作符
    //源发射数据时，如果两次数据的发射间隔小于指定时间，就会丢弃前一次的数据,直到指定时间内都没有新数据发射时才进行发射
    public void rxjavathrottleWithTimeout() {

        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> oe) throws Exception {

                oe.onNext(1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                oe.onNext(2);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                oe.onNext(3);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                oe.onNext(4);
                oe.onNext(5);
                oe.onComplete();

            }
        })
                .throttleWithTimeout(800, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(TAG, "onSubscribe" + d.isDisposed());
                    }

                    @Override
                    public void onNext(@NonNull Object o) {
                        Log.i(TAG, "onNext" + o.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });
    }


    public void rxjavamerge() {
        //merge操作符，会将多个Observable对象合并到一个Observable对象中进行发送
        Observable<Integer> firstObservable = Observable.just(0, 1, 2 ).subscribeOn(Schedulers.io()).delay(500,TimeUnit.MILLISECONDS);
        Observable<Integer> secondObservable = Observable.just(3 , 4, 5)
                .delay(100,TimeUnit.MILLISECONDS);
        Observable
                .merge(firstObservable, secondObservable).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "onNext" + integer );
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "onComplete");
            }
        });
    }

    public void rxjavaretry() {
        //retry操作符，当遇到exception时会进行重试，重试次数可以由用户进行定义
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> e) throws Exception {
                for (int i = 0; i < 5; i++) {
                    if (i > 1) {
//                        e.onError(new Throwable("User Alex Defined Error"));
                        e.onError(new Exception("Maybe error exception "));
                    }
                    e.onNext(i);
                }
            }
        } ).retry(2).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i(TAG, "onSubscribe" + d.isDisposed());
            }

            @Override
            public void onNext(@NonNull Object o) {
                Log.i(TAG, "onNext" + o.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });
    }

    public void rxjavaClodObservble() {
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("subscriber1: "+aLong);
            }
        };
        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("   subscriber2: "+aLong);
            }
        };
        Observable<Long> observable = Observable.interval(500, TimeUnit.MILLISECONDS , AndroidSchedulers.mainThread())
                .take(10)
                .observeOn(Schedulers.newThread());
        observable.subscribe(subscriber1);
        observable.subscribe(subscriber2);


    }

    public void rxjavaHotObservble() {
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("subscriber1: "+aLong);
            }
        };
        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("   subscriber2: "+aLong);
            }
        };
        Consumer<Long> subscriber3 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("      subscriber3: "+aLong);
            }
        };
        ConnectableObservable<Long> observable = Observable.interval(500, TimeUnit.MILLISECONDS , Schedulers.newThread())
                .take(10)
                .observeOn(Schedulers.newThread()).publish();
        observable.connect();

        observable.subscribe(subscriber1);
        observable.subscribe(subscriber2);

        observable.subscribeOn(Schedulers.io());
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observable.subscribe(subscriber3);
    }

    public void rxjavaHotObservble2() {
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("subscriber1: "+aLong);
            }
        };
        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("   subscriber2: "+aLong);
            }
        };
        Consumer<Long> subscriber3 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("      subscriber3: "+aLong);
            }
        };
        Observable<Long> observable = Observable.interval(500, TimeUnit.MILLISECONDS , Schedulers.newThread())
                .take(6)
                .observeOn(Schedulers.newThread()) ;
        PublishSubject<Long> subject = PublishSubject.create();
        observable.subscribe(subject);

        subject.subscribe(subscriber1);
        subject.subscribe(subscriber2);
        SystemClock.sleep(1000);
        subject.subscribe(subscriber3);
    }


    public void rxjavaClodObservble1() {
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("subscriber1: " + aLong);
            }
        };
        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("   subscriber2: " + aLong);
            }
        };
        ConnectableObservable<Long> connectableObservable = Observable.interval(500, TimeUnit.MILLISECONDS , Schedulers.newThread())
                .take(10)
                .observeOn(Schedulers.newThread()).publish();
        connectableObservable.connect();
        Observable<Long> observable = connectableObservable.refCount();

        Disposable disposable1 = observable.subscribe(subscriber1);
        Disposable disposable2 = observable.subscribe(subscriber2);

        SystemClock.sleep(2000);
        disposable1.dispose();
        disposable2.dispose();

        System.out.println("重新开始数据流");

        disposable1 = observable.subscribe(subscriber1);
        disposable2 = observable.subscribe(subscriber2);
    }
    public void rxjavaClodObservble2() {
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("subscriber1: " + aLong);
            }
        };
        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("   subscriber2: " + aLong);
            }
        };
        Consumer<Long> subscriber3 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("      subscriber3: "+aLong);
            }
        };
        ConnectableObservable<Long> connectableObservable = Observable.interval(500, TimeUnit.MILLISECONDS , Schedulers.newThread())
                .take(10)
                .observeOn(Schedulers.newThread()).publish();
        connectableObservable.connect();
        Observable<Long> observable = connectableObservable.refCount();

        Disposable disposable1 = observable.subscribe(subscriber1);
        Disposable disposable2 = observable.subscribe(subscriber2);
        observable.subscribe(subscriber3);

        SystemClock.sleep(2000);
        disposable1.dispose();
        disposable2.dispose();

        System.out.println("重新开始数据流");

        disposable1 = observable.subscribe(subscriber1);
        disposable2 = observable.subscribe(subscriber2);
    }

}
