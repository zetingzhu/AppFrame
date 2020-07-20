package frame.zzt.com.appframe.ui.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.widget.CarStartAnimation;
import frame.zzt.com.appframe.widget.ProgressBarStart;
import frame.zzt.com.appframe.widget.progress.DialProgress;
import frame.zzt.com.appframe.widget.progress.WaveProgress;

/**
 * 启动按钮需要的进度条
 */

public class ActivityProgress2 extends Activity {
    @BindView(R.id.circleProgressBar)
    public ProgressBarStart circleProgressBar; // 自定义的进度条
    @BindView(R.id.seekbar)
    public SeekBar seekbar; // 拖动条
    @BindView(R.id.custom_car_start_status1)
    public CarStartAnimation progressbar1; // 蓝牙车辆启动动画


    /**


     <frame.zzt.com.appframe.widget.progress.DialProgress
     android:id="@+id/dial_progress_bar"
     android:layout_width="300dp"
     android:layout_height="300dp"
     android:layout_gravity="center_horizontal"
     android:padding="@dimen/medium"
     app:animTime="1000"
     app:arcColors="@array/gradient_arc_color"
     app:arcWidth="@dimen/large"
     app:dialIntervalDegree="3"
     app:dialWidth="2dp"
     app:hint="当前时速"
     app:hintSize="@dimen/text_size_25"
     app:maxValue="300"
     app:startAngle="135"
     app:sweepAngle="270"
     app:unit="km/h"
     app:unitSize="@dimen/text_size_25"
     app:value="300"
     app:valueSize="@dimen/text_size_35" />

     <frame.zzt.com.appframe.widget.progress.WaveProgress
     android:id="@+id/wave_progress_bar"
     android:layout_width="300dp"
     android:layout_height="300dp"
     android:layout_gravity="center_horizontal"
     app:darkWaveAnimTime="1000"
     app:darkWaveColor="@color/dark"
     app:lightWaveAnimTime="2000"
     app:lightWaveColor="@color/light"
     app:lightWaveDirect="R2L"
     app:lockWave="false"
     app:valueSize="@dimen/text_size_35"
     app:waveHeight1="30dp"
     app:waveNum="1" />

     */
    @BindView(R.id.dial_progress_bar)
    public DialProgress mDialProgress;
    @BindView(R.id.wave_progress_bar)
    public WaveProgress mWaveProgress;


    private Random mRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_2);
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


        progressbar1.setProgressLineWidth(70);
        progressbar1.setTimeMillis(12000);
        progressbar1.setProgress(0);
        progressbar1.setOutLineColor(Color.TRANSPARENT);
        progressbar1.setProgressColor(getResources().getColor(R.color.color_1e74b6));
        progressbar1.setProgressType(CarStartAnimation.ProgressType.COUNT);
        progressbar1.reStart();
        progressbar1.isSetTimeout(false);
        progressbar1.setTimeout(15 * 1000);
        progressbar1.setText("动画中");
        progressbar1.setCountdownProgressListener(30, new CarStartAnimation.OnCountdownProgressListener() {
            @Override
            public void onProgress(int what, int progress) {

            }

            @Override
            public void onProgressSucced(long time) {

            }

            @Override
            public void onProgressTimeout() {

            }
        });

        mRandom = new Random();
        mWaveProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaveProgress .setValue(mRandom.nextFloat() * mDialProgress.getMaxValue());
            }
        });


        mDialProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialProgress.setValue(mRandom.nextFloat() * mWaveProgress.getMaxValue());
            }
        });

    }

}
