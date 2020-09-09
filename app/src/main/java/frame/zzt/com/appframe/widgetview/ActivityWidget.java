package frame.zzt.com.appframe.widgetview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;

/**
 * Created by zeting
 * Date 19/6/28.
 */

public class ActivityWidget extends AppCompatActivity {

    private static final String TAG = ActivityWidget.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_widget);

        ButterKnife.bind(this);

        initView();
    }

    private void initView() {

    }
}
