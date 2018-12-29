package frame.zzt.com.appframe.UI.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import frame.zzt.com.appframe.Bluetooth.ActivityBluetooth4;
import frame.zzt.com.appframe.Bluetooth.ActivityBluetooth5;
import frame.zzt.com.appframe.Bluetooth.ActivityBluetooth6;
import frame.zzt.com.appframe.Bluetooth.ActivityBluetooth7;
import frame.zzt.com.appframe.Notification.ActivityNotification;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.Bluetooth.ActivityBluetooth;
import frame.zzt.com.appframe.Bluetooth.ActivityBluetooth2;
import frame.zzt.com.appframe.Bluetooth.ActivityBluetooth3;
import frame.zzt.com.appframe.ReadCode.ActivityReadCodeClass;
import frame.zzt.com.appframe.UI.Activity.ActivityFirst;
import frame.zzt.com.appframe.eventbus.ActivityEventBus;
import frame.zzt.com.appframe.kotlin.ActivityKotlin;
import frame.zzt.com.appframe.UI.Activity.ActivityProgress2;
import frame.zzt.com.appframe.observable.ActivityObservable;
import frame.zzt.com.appframe.rxjava.ActivityRxJava;
import frame.zzt.com.appframe.UI.Activity.ActivitySurfaceView;
import frame.zzt.com.appframe.doodleview.DoodleViewActivity;
import frame.zzt.com.appframe.rxjava.ActivityRxJavaUse;
import frame.zzt.com.appframe.slidelock.ActivitySlideLock;
import frame.zzt.com.appframe.touchAnim.ActivityTouchAnim;

/**
 * Created by allen on 18/8/8.
 */

public class FirstFragment extends Fragment {

    @BindView(R.id.listView_first)
    public ListView mListView;

    @BindString(R.string.tab_item_first)
    public String title;

    @BindString(R.string.tab_item_desc)
    public String desc;

    private View mRootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_first, container, false);

        unbinder = ButterKnife.bind(this, mRootView);

        initView();

        return mRootView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    private void initView() {
        // 添加ListItem，设置事件响应
        mListView.setAdapter(new DemoListAdapter());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View v, int index,
                                    long arg3) {
                onListItemClick(index);
            }
        });
    }

    void onListItemClick(int index) {
        Intent intent;
        intent = new Intent(getActivity(), DEMOS[index].demoClass);
        this.startActivity(intent);
    }

    private DemoInfo[] DEMOS = {
            new DemoInfo(R.string.tab_item_first, R.string.tab_item_desc, ActivityFirst.class),
            new DemoInfo(R.string.tab_item_bluetooth, R.string.tab_item_bluetooth_desc, ActivityBluetooth.class),
            new DemoInfo(R.string.tab_item_bluetooth2, R.string.tab_item_bluetooth_desc, ActivityBluetooth2.class),
            new DemoInfo(R.string.tab_item_bluetooth3, R.string.tab_item_bluetooth_desc, ActivityBluetooth3.class),
            new DemoInfo(R.string.tab_item_bluetooth4, R.string.tab_item_bluetooth_desc, ActivityBluetooth4.class),
            new DemoInfo(R.string.tab_item_bluetooth5, R.string.tab_item_bluetooth_desc, ActivityBluetooth5.class),
            new DemoInfo(R.string.tab_item_bluetooth6, R.string.tab_item_bluetooth6_desc, ActivityBluetooth6.class),
            new DemoInfo(R.string.tab_item_bluetooth7, R.string.tab_item_bluetooth6_desc, ActivityBluetooth7.class),
            new DemoInfo(R.string.tab_item_surfaceView, R.string.tab_item_surfaceView_desc , ActivitySurfaceView.class),
            new DemoInfo(R.string.tab_item_doodleview  , R.string.tab_item_doodleview_desc   , DoodleViewActivity.class),
            new DemoInfo(R.string.tab_item_rxjava  , R.string.tab_item_rxjava_desc   , ActivityRxJava.class),
            new DemoInfo(R.string.tab_item_rxjava_use  , R.string.tab_item_rxjava_use_desc  , ActivityRxJavaUse.class),
            new DemoInfo(R.string.tab_item_pro, R.string.tab_item_pro_desc, ActivityProgress2.class),
            new DemoInfo(R.string.tab_item_Kotlin , R.string.tab_item_Kotlin_desc, ActivityKotlin.class),
            new DemoInfo(R.string.tab_item_Notification , R.string.tab_item_Notification_desc , ActivityNotification.class),
            new DemoInfo(R.string.tab_item_observable  , R.string.tab_item_observable_desc , ActivityObservable.class),
            new DemoInfo(R.string.tab_item_eventbus  , R.string.tab_item_eventbus_desc , ActivityEventBus.class),
            new DemoInfo(R.string.tab_item_read_clazz  , R.string.tab_item_read_clazz_desc , ActivityReadCodeClass.class),
            new DemoInfo(R.string.tab_item_anim  , R.string.tab_item_anim_desc , ActivitySlideLock.class),
    };

    public class DemoListAdapter extends BaseAdapter {
        public DemoListAdapter() {
            super();
        }

        @Override
        public View getView(int index, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.list_info_item, null);
                myViewHolder = new MyViewHolder(convertView);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.title.setText(DEMOS[index].title);
            myViewHolder.desc.setText(DEMOS[index].desc);
            if (index >= 25) {
                myViewHolder.title.setTextColor(Color.YELLOW);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return DEMOS.length;
        }

        @Override
        public Object getItem(int index) {
            return DEMOS[index];
        }

        @Override
        public long getItemId(int id) {
            return id;
        }

        class MyViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.desc)
            TextView desc;

            public MyViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
