package frame.zzt.com.appframe.seekbar;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;

/**
 * Created by zeting
 * Date 19/6/28.
 */

public class ActivitySeekBar extends AppCompatActivity {

    private static final String TAG = ActivitySeekBar.class.getSimpleName() ;
    @BindView(R.id.sb_myseekbar)
    public MySeekBar sb_myseekbar ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seek_bar);

        ButterKnife.bind(this);

        initView() ;
    }

    private void initView() {

        // 设置滚动条显示数据
        sb_myseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "滑动改变的值：" + progress + " - " + fromUser);

                sb_myseekbar.setProgressFloat((float) progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 切换时候停止动画.并且设置状态为暂停状态
                Log.d(TAG, "滑动改变：onStartTrackingTouch "  );
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "滑动改变：onStopTrackingTouch "  );
            }
        });
    }
}
