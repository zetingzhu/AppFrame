package frame.zzt.com.appframe.Notification;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;
import frame.zzt.com.appframe.mvp.mvpbase.BaseView;
import frame.zzt.com.appframe.widget.MyRecyclerView;


/**
 * 通知栏状态信息
 */
public class ActivityNotification extends BaseAppCompatActivity implements MyNotificationView{

    private final static String TAG = ActivityNotification.class.getSimpleName();

    @BindView(R.id.rv_noti)
    public MyRecyclerView rv_noti;

    private List<MyRecyclerView.MyRecycleListItem> mList;


    private MyNotificationPersenter mNoti ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        mNoti = new MyNotificationPersenter(this , this );
        initData();

        // 授权监听电话通知栏
        initNotificationService();

        // 数据初始化
        mNoti.onCreate();

        initReceiver();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 数据销毁
        mNoti.onDestroy();
    }

    private void initData() {


        mList = new ArrayList<>();
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(0, "简单的一条通知消息"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(1, "界面跳转的一条通知消息"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(2, "更新第二个界面跳转的通知消息"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(3, "清除第一条消息"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(4, "清除第二条带有TAG的消息"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(5, "生成第三条不可消除的通知"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(6, "清除第三条带flag的通知"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(7, "生成有铃声的通知"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(8, "生成有震动灯的通知"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(9, "生成有呼吸灯的通知"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(10, "生成默认铃声、震动、呼吸灯效果的通知"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(11, "循环执行"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(12, "只执行一次"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(13, "取消所有通知"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(14, "生成进度通知消息"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(15, "生成带有 action 按钮的消息"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(16, "绑定服务"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(17, "解绑服务"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(18, "点击的是第几个按钮"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(19, "显示自定义通知栏"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(20, "启动服务"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(21, "更新通知栏样式"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(22, "显示自定义通知栏"));
        mList.add(new MyRecyclerView(this).new MyRecycleListItem(23, "更新自定义通知栏"));

        rv_noti.setMyAdapter(mList, new MyRecyclerView.MyRecycleOnClick() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClickListener(int position) {
                Log.i(TAG, "点击数据：" + position);
                switch (position) {
                    case 0:
                        mNoti.showNotify1();
                        break;
                    case 1:
                        mNoti.sendSimplestNotificationWithAction();
                        break;
                    case 2:
                        mNoti.sendUpdateNotification();
                        break;
                    case 3:
                        mNoti.cancleNotify();
                        break;
                    case 4:
                        mNoti.cancleNotifyAsTag();
                        break;
                    case 5:
                        mNoti.sendFlagNoClearNotification();
                        break;
                    case 6:
                        mNoti.cancleNotifyFlag();
                        break;
                    case 7:
                        mNoti.showNotifyWithRing();
                        break;
                    case 8:
                        mNoti.showNotifyWithVibrate();
                        break;
                    case 9:
                        mNoti.showNotifyWithLights();
                        break;
                    case 10:
                        mNoti.showNotifyWithMixed();
                        break;
                    case 11:
                        mNoti.showInsistentNotify();
                        break;
                    case 12:
                        mNoti.showAlertOnceNotify();
                        break;
                    case 13:
                        mNoti.clearNotify();
                        break;
                    case 14:
                        mNoti.setProgressNotify();
                        break;
                    case 15:
                        mNoti.setNotifyAction();
                        break;
                    case 16:
//                        mNoti.bindService(0);
                        mNoti.skipAction(0);
                        break;
                    case 17:
//                        mNoti.unBindService(2);
                        mNoti.skipAction(2);
                        break;
                    case 18:
                        mNoti.skipAction(1);
                        break;
                    case 19:
                        mNoti.showCustomViewNotification();
                        break;
                    case 20:
                        mNoti.startService();
                        break;
                    case 21:
                        mNoti.notifyCustomViewNotification();
                        break;
                    case 22:
                        mNoti.showNotify();
                        break;
                    case 23:
                        mNoti.refreshNotify();
                        break;
                }
            }
        });
    }


    /**
     * 初始化通知服务
     */
    public void initNotificationService() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion < 18) {
            Log.d(TAG, "currentapiVersion < 18 不使用监听通知栏信息");
        } else {
            if (!isNotificationListenerActived()) {
                Log.d(TAG, "showNotifiListnerPrompt");
                showNotifiListnerPrompt();
            }
        }
    }

    public boolean isNotificationListenerActived() {
        String strListener = Settings.Secure.getString(this.getContentResolver(),
                "enabled_notification_listeners");
        Log.w(TAG , "获取的所有通知：" + strListener);
        return strListener != null
                && strListener
                .contains("frame.zzt.com.appframe/frame.zzt.com.appframe.Notification.NotificationReceiver18");
    }

    public static final Intent NOTIFICATION_LISTENER_INTENT = new Intent(
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");

    private void showNotifiListnerPrompt() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notificationlistener_prompt_title);
        builder.setMessage(R.string.notificationlistener_prompt_content);

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        // Go to notification listener settings
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(NOTIFICATION_LISTENER_INTENT);
            }
        });
        builder.create().show();
    }

    @Override
    public void showMsg(String msg) {
        showtoast(msg);
    }


    /**
     * 初始化广播
     */
    private void initReceiver() {
        ReceiverNotification mRn = new ReceiverNotification();
        mRn.setNotifyUi(new NotifyListener() {
            @Override
            public void notifyUi() {
                Log.d(TAG , "更新界面 -- " );
                mNoti.notifyCustomViewNotification();
            }
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ReceiverNotification.BECEIVER_CHECK);
        registerReceiver(mRn, intentFilter);
    }

}
