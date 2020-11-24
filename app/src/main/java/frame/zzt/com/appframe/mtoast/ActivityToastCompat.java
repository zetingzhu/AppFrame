package frame.zzt.com.appframe.mtoast;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import frame.zzt.com.appframe.R;

/**
 * @author: zeting
 * @date: 2020/7/2
 */
public class ActivityToastCompat extends AppCompatActivity {
    private final static String TAG = ActivityToastCompat.class.getSimpleName();
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:

                    ToastCompat.makeText(ActivityToastCompat.this, "toask数据：" + random.nextInt(100), ToastCompat.LENGTH_SHORT).show();
                    handler.sendEmptyMessageDelayed(0, 1000);
                    break;
                case 1:
                    toastCompat.setText("这个是不会一直出现的：" + random.nextInt(100));
                    toastCompat.setDuration(1000);
                    toastCompat.show();
                    handler.sendEmptyMessageDelayed(1, 1000);
                    break;
            }
            return false;
        }
    });
    Random random = new Random();
    ToastCompat toastCompat;

    PermissionUtils permissionUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        findViewById(R.id.button_0).setOnClickListener(v -> {
                    ToastCompat.makeText(ActivityToastCompat.this, "这个是特殊toast：" + random.nextInt(100), ToastCompat.LENGTH_SHORT).show();
                }
        );
        findViewById(R.id.button_1).setOnClickListener(v ->
                handler.sendEmptyMessageDelayed(0, 100)
        );

        ((Button) findViewById(R.id.button_2)).setText("系统的");
        findViewById(R.id.button_2).setOnClickListener(v ->
                Toast.makeText(ActivityToastCompat.this, "这个是系统toast", ToastCompat.LENGTH_SHORT).show()
        );


        toastCompat = new ToastCompat(ActivityToastCompat.this);

        findViewById(R.id.button_3).setOnClickListener(v -> {

                    toastCompat.setText("第三个按钮事件：" + random.nextInt(100));
                    toastCompat.setDuration(2000);
                    toastCompat.show();
                }
        );
        ((Button) findViewById(R.id.button_4)).setText("连续提示");
        findViewById(R.id.button_4).setOnClickListener(v -> {
                    handler.sendEmptyMessageDelayed(1, 100);
                }
        );
        ((Button) findViewById(R.id.button_5)).setText("权限申请");
        ((Button) findViewById(R.id.button_6)).setText("权限申请");
        ((Button) findViewById(R.id.button_7)).setText("权限申请");
        findViewById(R.id.button_5).setOnClickListener(v -> {
                    permissionUtils.checkPermission(true);
                }
        );
        findViewById(R.id.button_6).setOnClickListener(v -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        permissionUtils.requestDrawOverLays();
                    }
                }
        );
        findViewById(R.id.button_7).setOnClickListener(v -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        permissionUtils.requestCanWrite();
                    }
                }
        );

        /**
         * 申请权限
         */
        String[] strings1 = new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW};
        String[] strings2 = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR};
        permissionUtils = new PermissionUtils(this, strings2, new PermissionUtils.PermissionListener() {
            @Override
            public void requestPermissions(boolean status) {
                Log.w(TAG, "权限是否申请成功了：" + status);
            }

            @Override
            public void requestPermissionsRefusal() {
                Log.w(TAG, "权限已经拒绝了");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
