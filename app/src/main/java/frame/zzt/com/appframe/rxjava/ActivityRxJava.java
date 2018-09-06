package frame.zzt.com.appframe.rxjava;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;
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
 * Created by allen on 18/8/29.
 */

public class ActivityRxJava extends BaseAppCompatActivity implements RxView {

    private String TAG = "ActivityRxJava";
    @BindView(R.id.button1)
    public Button mButton1;

    @BindView(R.id.imageView1)
    public ImageView imageView;

    @BindView(R.id.recycleview)
    public RecyclerView mRecyclerView;

    private List<MyListItem> mList;
    AdapterRecycle mAdapterRecycle;
    MyOnClick mOnClick;

    protected RxPersenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);


        mPresenter = new RxPersenter(this);

        mList = new ArrayList<>();
        mList.add(new MyListItem(0 , "创建Rxjava" ));
        mList.add(new MyListItem(1 , "Consumer subscribe 订阅不同的observer对象来接收数据"));
        mList.add(new MyListItem(2 , "创建上下游线程关系"));
        mList.add(new MyListItem(3 , "Schedulers 线程调度"));
        mList.add(new MyListItem(4 , "Schedulers 线程调度 observeOn 多次切换"));//4
        mList.add(new MyListItem(5 , "读写数据库操作"));//5
        mList.add(new MyListItem(6 , "RxJava Map 变换操作（map与flatMap）操作符的使用"));//6
        mList.add(new MyListItem(7 , "RxJava FlatMap 变换操作（map与flatMap） 操作符的使用 ， 无序的"));//7
        mList.add(new MyListItem(15 , "RxJava concatMap 操作符的使用 ， 有序的"));
        mList.add(new MyListItem(16 , "RxJava switchMap 操作符的使用 , 获取最后一次  "));
        mList.add(new MyListItem(17 , "RxJava switchMap 2 操作符的使用 , 获取最后一次 "));
        mList.add(new MyListItem(8 , "获取北京天气"));//8
        mList.add(new MyListItem(9 , "获取热门城市列表"));//9
        mList.add(new MyListItem(10 , "两个接口连在一起Flat Map操作符 "));//10
        mList.add(new MyListItem(11 , "ZIP 聚合操作 , 将两个observable 流事件合在一起"));
        mList.add(new MyListItem(12 , "ZIP 聚合操作 , 将两个observable 流事件合在一起 查看两个流的执行顺序"));//12
        mList.add(new MyListItem(13 , "ZIP 聚合操作 , 调用两个接口，合并两个接口数据后显示"));
        mList.add(new MyListItem(14 , "测试 Observable 上下游不同步的时候，内存处理关系"));
        mList.add(new MyListItem(18 , "RxJava Flowable 使用"));
        mList.add(new MyListItem(19 , "RxJava Flowable 2 使用"));
        mList.add(new MyListItem(20 , "RxJava Flowable Request N个 使用"));
        mList.add(new MyListItem(21 , "RxJava Flowable 3 使用"));
        mList.add(new MyListItem(22 , "RxJava Flowable FlowableEmitter.Requested "));
        mList.add(new MyListItem(23 , "RxJava Flowable FlowableEmitter.Requested 上下游结合使用"));
        mList.add(new MyListItem(24 , "RxJava Flowable 异步处理"));
        mList.add(new MyListItem(25 , "RxJava Flowable 1 异步处理"));
        mList.add(new MyListItem(26 , "RxJava Flowable Request 异步请求 N个 数据"));
        mList.add(new MyListItem(27 , "RxJava Flowable 读取文件"));
        mList.add(new MyListItem(28 , "Single  -被观察者  RxJava Maybe、Single、Completable、Observable、Flowable"));
        mList.add(new MyListItem(29 , "Completable  -被观察者  RxJava Maybe、Single、Completable、Observable、Flowable"));
        mList.add(new MyListItem(30 , "Maybe  -被观察者  RxJava Maybe、Single、Completable、Observable、Flowable"));
        mList.add(new MyListItem(31 , "just RxJava 创建操作符使用"));
        mList.add(new MyListItem(32 , "fromArray RxJava 创建操作符使用"));
        mList.add(new MyListItem(33 , "fromIterable RxJava 创建操作符使用"));
        mList.add(new MyListItem(34 , "range RxJava 创建操作符使用"));
        mList.add(new MyListItem(35 , "filter distinct RxJava 过滤操作符使用"));


        mAdapterRecycle = new AdapterRecycle();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapterRecycle);
        mOnClick = new MyOnClick() {
            @Override
            public void onClickListener(int position) {
                Log.i(TAG, "点击的是：" + position);
                switch (position) {
                    case 0:
                        Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                Log.d(TAG, "emit 1");
                                emitter.onNext(1);
                                Log.d(TAG, "emit 2");
                                emitter.onNext(2);
                                Log.d(TAG, "emit 3");
                                emitter.onNext(3);
                                Log.d(TAG, "emit complete");
                                emitter.onComplete();
                                Log.d(TAG, "emit 4");
                                emitter.onNext(4);
                            }
                        }).subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.d(TAG, "subscribe");
                            }

                            @Override
                            public void onNext(@NonNull Integer integer) {
                                Log.d(TAG, "onNext -:" + integer);
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.d(TAG, "onError -:" + e);
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete ");
                            }
                        });
                        break;
                    case 1:
                        Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                                Log.d(TAG, "emit 1");
                                emitter.onNext(1);
                                Log.d(TAG, "emit 2");
                                emitter.onNext(2);
                                Log.d(TAG, "emit 3");
                                emitter.onNext(3);
                                Log.d(TAG, "emit complete");
                                emitter.onComplete();
                                Log.d(TAG, "emit 4");
                                emitter.onNext(4);
                            }
                        }).subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                Log.d(TAG, "onNext: " + integer);
                            }
                        });

                        break;
                    case 2:
                        mPresenter.setSubscribe1();
                        mPresenter.getObservale1().subscribe(mPresenter.getConsumer1());
                        break;
                    case 3:
                        /**
                         Scheduler种类

                         Schedulers.io( )：
                         用于IO密集型的操作，例如读写SD卡文件，查询数据库，访问网络等，具有线程缓存机制，在此调度器接收到任务后，先检查线程缓存池中，是否有空闲的线程，如果有，则复用，如果没有则创建新的线程，并加入到线程池中，如果每次都没有空闲线程使用，可以无上限的创建新线程。

                         Schedulers.newThread( )：
                         在每执行一个任务时创建一个新的线程，不具有线程缓存机制，因为创建一个新的线程比复用一个线程更耗时耗力，虽然使用Schedulers.io( )的地方，都可以使用Schedulers.newThread( )，但是，Schedulers.newThread( )的效率没有Schedulers.io( )高。

                         Schedulers.computation()：
                         用于CPU 密集型计算任务，即不会被 I/O 等操作限制性能的耗时操作，例如xml,json文件的解析，Bitmap图片的压缩取样等，具有固定的线程池，大小为CPU的核数。不可以用于I/O操作，因为I/O操作的等待时间会浪费CPU。

                         Schedulers.trampoline()：
                         在当前线程立即执行任务，如果当前线程有任务在执行，则会将其暂停，等插入进来的任务执行完之后，再将未完成的任务接着执行。

                         Schedulers.single()：
                         拥有一个线程单例，所有的任务都在这一个线程中执行，当此线程中有任务执行时，其他任务将会按照先进先出的顺序依次执行。

                         Scheduler.from(@NonNull Executor executor)：
                         指定一个线程调度器，由此调度器来控制任务的执行策略。

                         AndroidSchedulers.mainThread()：
                         在Android UI线程中执行任务，为Android开发定制。

                         */
                        mPresenter.setSubscribe1();
                        mPresenter.getObservale1().subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(mPresenter.getConsumer1());

                        break;
                    case 4:
                        mPresenter.setSubscribe1();
                        mPresenter.getObservale1().subscribeOn(Schedulers.newThread())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnNext(new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer integer) throws Exception {
                                        Log.d(TAG, "After observeOn(mainThread), current thread is: " + Thread.currentThread().getName());
                                    }
                                })
                                .observeOn(Schedulers.io())
                                .doOnNext(new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer integer) throws Exception {
                                        Log.d(TAG, "After observeOn(io), current thread is : " + Thread.currentThread().getName());
                                    }
                                })
                                .subscribe(mPresenter.getConsumer1());

                        break;
                    case 5:
                        mPresenter.readAllUser();
                        break;
                    case 6:
                        mPresenter.rxjavaMap();
                        break;
                    case 7:
                        mPresenter.rxjavaFlatMap();
                        break;
                    case 8:
                        mPresenter.getWheatherBeijin();
                        break;
                    case 9:
                        mPresenter.getWheatherGroup();
                        break;
                    case 10:
                        mPresenter.rxjavaFlatMap10();
                        break;
                    case 11:
                        mPresenter.rxjavaZip();
                        break;
                    case 12:
                        mPresenter.rxjavaZip2();
                        break;
                    case 13:
                        mPresenter.rxjavaZip3();
                        break;
                    case 14:
                        mPresenter.rxjavaOOM();
                        break;
                    case 15:
                        mPresenter.rxjavaConcatMap() ;
                        break;
                    case 16:
                        mPresenter.rxjavaSwitchMap() ;
                        break;
                    case 17:
                        mPresenter.rxjavaSwitchMap2() ;
                        break;
                    case 18 :
                        mPresenter.rxjavaFlowable();
                        break;
                    case 19 :
                        mPresenter.rxjavaFlowable2();
                        break;
                    case 20 :
//                        mPresenter.rxjavaFlowableRequest(2);
//                        mPresenter.rxjavaFlowableRequest(128);
                        mPresenter.rxjavaFlowableRequest(50);
                        break;
                    case 21 :
                        mPresenter.rxjavaFlowable3();
                        break;
                    case 22 :
                        mPresenter.rxjavaFlowableRequested();
                        break;
                    case 23 :
                        mPresenter.rxjavaFlowableRequested1();
                        break;
                    case 24 :
                        mPresenter.rxjavaFlowableAsyn();
                        break;
                    case 25 :
                        mPresenter.rxjavaFlowableAsyn1();
                        break;
                    case 26 :
                        mPresenter.rxjavaFlowableAsynRequest(100);
                        break;
                    case 27 :
                        mPresenter.rxjavaFlowableAsynReadText(ActivityRxJava.this);
                        break;
                    case 28 :
                        mPresenter.rxjavaSingle( );
                        break;
                    case 29 :
                        mPresenter.rxjavaCompletable();
                        break;
                    case 30 :
                        mPresenter.rxjavaMaybe();
                        break;
                    case 31 :
                        mPresenter.rxjavaJust();
                        break;
                    case 32 :
                        mPresenter.rxjavaFromArray();;
                        break;
                    case 33 :
                        mPresenter.rxjavaFromIterable();
                        break;
                    case 34 :
                        mPresenter.rxjavaRange();
                        break;
                    case 35 :
                        mPresenter.rxjavaFilterDistinct();
                        break;
                    case 36 :
                        break;
                    case 37 :
                        break;
                    case 38 :
                        break;
                    case 39 :
                        break;
                    case 40 :
                        break;
                    case 41 :
                        break;
                }
            }
        };
    }

    public class AdapterRecycle extends RecyclerView.Adapter<DateHolder> {

        @Override
        public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
            DateHolder viewHolder = new DateHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DateHolder holder, final int position) {
            holder.mTextview.setText(mList.get(position).getItemId() +" : "+ mList.get(position).getItemValue());
            holder.mTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClick.onClickListener(mList.get(position).getItemId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public class DateHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView mTextview;

        public DateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MyOnClick {
        void onClickListener(int position);
    }

    public class MyListItem{
        public int itemId ;
        public String itemValue ;

        public MyListItem(int itemId, String itemValue) {
            this.itemId = itemId;
            this.itemValue = itemValue;
        }

        public int getItemId() {
            return itemId;
        }

        public String getItemValue() {
            return itemValue;
        }
    }

}
