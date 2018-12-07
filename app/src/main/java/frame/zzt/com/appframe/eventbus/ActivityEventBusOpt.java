package frame.zzt.com.appframe.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

/**
 * Created by zeting
 * Date 18/11/28.
 */

public class ActivityEventBusOpt extends BaseAppCompatActivity {

    @BindView(R.id.tv_msg)
    TextView tv_msg ;
    @BindView(R.id.btn_skip)
    Button btn_skip ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_eventbus);
        ButterKnife.bind(this );
    }

    @OnClick(R.id.btn_skip)
    public void onClickSend(){
        startActivity(new Intent(this, ));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMoonEvent(MessageEvent messageEvent){
        tv_msg.setText(messageEvent.getMessage());
    }


    @Override
    protected void onStart() {
        super.onStart();
        //注册事件
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //取消注册事件
        EventBus.getDefault().unregister(this);
    }
}
