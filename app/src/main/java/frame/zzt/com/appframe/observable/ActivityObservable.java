package frame.zzt.com.appframe.observable;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;
import frame.zzt.com.appframe.widget.MyRecyclerView;

/**
 * Created by allen on 18/10/16.
 */

public class ActivityObservable extends BaseAppCompatActivity implements MyObservableView {

    private final static String TAG = ActivityObservable.class.getSimpleName();

    @BindView(R.id.rv_observable)
    MyRecyclerView rv_observable;


    private List<MyRecyclerView.MyRecycleListItem> mList;
    NewsProvider provider;
    NewsProvider2 provider2;
    User user;
    User2 user2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_observable);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    private void initView() {
        mList = new ArrayList<>();
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(0, "自己写的观察者模式 observable observer "));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(1, "解除绑定观察者"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(2, "集成系统的观察者模式 observable observer "));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(3, "解除绑定观察者"));

        rv_observable.setMyAdapter(mList, new MyRecyclerView.MyRecycleOnClick() {
            @Override
            public void onClickListener(int position) {
                Log.i(TAG, "点击数据：" + position);
                switch (position) {
                    case 0:
                        provider = new NewsProvider();
                        for (int i = 0; i < 10; i++) {
                            user = new User("user:" + i);
                            provider.register(user);
                        }
                        break;
                    case 1:
                        if (provider != null) {
                            provider.removes();
                        }
                        break;
                    case 2:
                        provider2 = new NewsProvider2();
                        for (int i = 0; i < 10; i++) {
                            user2 = new User2("user:" + i);
                            provider2.addObserver(user2);
                        }
                        break;
                    case 3:
                        if (provider2 != null) {
                            provider2.deleteObservers();
                        }
                        break;
                }
            }
        });
    }


}
