package frame.zzt.com.appframe.tablayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import frame.zzt.com.appframe.R;

/**
 * @author: zeting
 * @date: 2019/11/14
 */
public class ActivityTablayout extends AppCompatActivity {
    TabLayout tablayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tablayout);
        tablayout = findViewById(R.id.tablayout) ;

        tablayout.addTab(tablayout.newTab().setText("111"));
        tablayout.addTab(tablayout.newTab().setText("222"));
        tablayout.addTab(tablayout.newTab().setText("333"));

    }


}
