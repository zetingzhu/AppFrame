package frame.zzt.com.appframe.ui.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.widget.CircleProgressBar;

/**
 * Created by allen on 18/8/8.
 */

public class ActivityFirst extends Activity {
    private static final String TAGG = ActivityFirst.class.getSimpleName();
    @BindView(R.id.circleProgressBar)
    public CircleProgressBar circleProgressBar; // 自定义的进度条
    @BindView(R.id.seekbar)
    public SeekBar seekbar; // 拖动条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);


//		circleProgressBar.setFirstColor(Color.LTGRAY);
//		circleProgressBar.setColorArray(colors); //觉得进度条颜色丑的，这里可以自行传入一个颜色渐变数组。
        circleProgressBar.setArcWidth(30);
        circleProgressBar.setCircleWidth(1);
        circleProgressBar.setProgress(60, -60); // 使用数字过渡动画

        seekbar.setMax(100);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                circleProgressBar.setProgress(seekbar.getProgress(), -seekbar.getProgress()); // 使用数字过渡动画
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // circleProgressBar.setProgress(progress); //不使用动画
//                    circleProgressBar.setProgress(progress, true); // 使用数字过渡动画
                }
            }
        });
    }

}
