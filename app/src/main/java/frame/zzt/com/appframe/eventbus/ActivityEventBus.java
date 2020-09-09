package frame.zzt.com.appframe.eventbus;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * EventBus 接收消息的界面
 * Created by zeting
 * Date 18/11/28.
 */

public class ActivityEventBus extends BaseAppCompatActivity {
    private final static String TAG = ActivityEventBus.class.getSimpleName();

    @BindView(R.id.tv_msg)
    TextView tv_msg;
    @BindView(R.id.btn_skip)
    Button btn_skip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this);

        tv_msg.setText("接收消息界面");
        btn_skip.setText("跳转到下一个界面");

        //注册事件
        EventBus.getDefault().register(this);
    }

    @OnClick(R.id.btn_skip)
    public void onClickSend() {
        startActivity(new Intent(this, ActivityEventBusOpt.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent) {
        Log.d(TAG, "我收到的消息：" + messageEvent.getMessage());
        tv_msg.setText(messageEvent.getMessage());
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }
}
