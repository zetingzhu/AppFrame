package frame.zzt.com.appframe.rxjava;

import android.app.Activity;
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
import frame.zzt.com.appframe.UI.login.LoginPresenter;
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

    private List<String> mList;
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
        mList.add("创建Rxjava");
        mList.add("subscribe 订阅不同的observer对象来接收数据");
        mList.add("创建上下游线程关系");
        mList.add("线程调度");
        mList.add("线程observeOn 多次切换");//4


        mAdapterRecycle = new AdapterRecycle();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapterRecycle);
        mOnClick = new MyOnClick() {
            @Override
            public void onClickListener(int position) {
                Log.i(TAG, "点击的是：" + position);
                switch (position){
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
                                Log.d(TAG, "onError -:" + e );
                            }

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "onComplete "  );
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
                        break ;
                    case 3:
                        mPresenter.setSubscribe1();
                        mPresenter.getObservale1().subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(mPresenter.getConsumer1());

                        break ;
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

                    break ;
                    case 5:
                    break;
                    case 6:
                    break ;
                    case 7:
                    break ;
                    case 8:
                    break ;
                    case 9:
                    break ;

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
            holder.mTextview.setText(mList.get(position));
            holder.mTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClick.onClickListener(position);
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

}
