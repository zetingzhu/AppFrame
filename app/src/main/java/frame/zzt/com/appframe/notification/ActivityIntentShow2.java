package frame.zzt.com.appframe.notification;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * 显示的通知界面
 */

public class ActivityIntentShow2 extends BaseAppCompatActivity {
    private final static String TAG = ActivityNotification.class.getSimpleName() ;

    @BindView(R.id.tv_msg)
    public TextView tv_msg ;

    private String showMsg ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_intent2);
        ButterKnife.bind(this);

        Intent mIntent = getIntent();
        showMsg = mIntent.getStringExtra("msg");
        Log.d(TAG , "通知栏跳转 -onCreate- msg:" + showMsg );
        if (!TextUtils.isEmpty(showMsg)) {
            tv_msg.setText(showMsg);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG , "通知栏跳转 -onStart-  "   );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG , "通知栏跳转 -onResume-  "  );
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG , "通知栏跳转 -onNewIntent-  "  );
    }
}
