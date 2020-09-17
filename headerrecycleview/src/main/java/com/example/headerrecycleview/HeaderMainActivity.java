package com.example.headerrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzt.commonmodule.utils.ConfigARouter;

/**
 * @author: zeting
 * @date: 2020/8/19
 */
@Route(path = ConfigARouter.ACTIVITY_RECYCLEVIEW_HEADER )
public class HeaderMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.header_activity_main);

        ARouter.getInstance().inject(this);
    }

    public void linearlayout(View view) {
        Intent intent = new Intent(this,LinearlayoutActivity.class);
        startActivity(intent);
    }

    public void gridlayout(View view) {
        Intent intent = new Intent(this,GridlayoutActivity.class);
        startActivity(intent);
    }
}
