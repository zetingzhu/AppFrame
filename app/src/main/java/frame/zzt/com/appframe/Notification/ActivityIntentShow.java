package frame.zzt.com.appframe.Notification;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.BaseAppCompatActivity;

/**
 * 显示的通知界面
 */

public class ActivityIntentShow extends BaseAppCompatActivity {
    private final static String TAG = ActivityNotification.class.getSimpleName() ;

    @BindView(R.id.tv_msg)
    public TextView tv_msg ;

    private String showMsg ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_intent);
        ButterKnife.bind(this);

        Intent mIntent = getIntent();
        showMsg = mIntent.getStringExtra("msg");

        if (!TextUtils.isEmpty(showMsg)) {
            tv_msg.setText(showMsg);
        }

    }
}
