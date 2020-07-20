package frame.zzt.com.appframe.eventbus;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * EventBus 发送消息的界面
 * Created by zeting
 * Date 18/11/28.
 */

public class ActivityEventBusOpt extends BaseAppCompatActivity {
    private final static String TAG = ActivityEventBusOpt.class.getSimpleName() ;

    @BindView(R.id.tv_msg)
    TextView tv_msg ;
    @BindView(R.id.btn_skip)
    Button btn_skip ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this );

        tv_msg.setText("发送消息界面");
        btn_skip.setText("我发送消息到上一个界面接收");
    }

    @OnClick(R.id.btn_skip)
    public void onClickSend(){
        EventBus.getDefault().post(new MessageEvent("嘿嘿，来了老弟。。。" + System.currentTimeMillis() ));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
