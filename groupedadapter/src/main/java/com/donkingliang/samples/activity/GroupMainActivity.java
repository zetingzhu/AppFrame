package com.donkingliang.samples.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.zzt.commonmodule.utils.ConfigARouter;
import com.zzt.groupedadapter.R;

@Route(path = ConfigARouter.ACTIVITY_RECYCLEVIEW_GROUP)
public class GroupMainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_sticky_list).setOnClickListener(this);
        findViewById(R.id.btn_group_list).setOnClickListener(this);
        findViewById(R.id.btn_no_header).setOnClickListener(this);
        findViewById(R.id.btn_no_footer).setOnClickListener(this);
        findViewById(R.id.btn_grid_1).setOnClickListener(this);
        findViewById(R.id.btn_grid_2).setOnClickListener(this);
        findViewById(R.id.btn_expandable).setOnClickListener(this);
        findViewById(R.id.btn_expandable).setOnClickListener(this);
        findViewById(R.id.btn_various).setOnClickListener(this);
        findViewById(R.id.btn_various_child).setOnClickListener(this);
        findViewById(R.id.btn_binding_adapter).setOnClickListener(this);
        findViewById(R.id.btn_binding_adapter).setOnClickListener(this);
        findViewById(R.id.btn_empty_list).setOnClickListener(this);

        ARouter.getInstance().inject(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_sticky_list) {
            StickyActivity.openActivity(this);
        } else if (id == R.id.btn_group_list) {
            GroupedListActivity.openActivity(this);
        } else if (id == R.id.btn_no_header) {
            NoHeaderActivity.openActivity(this);
        } else if (id == R.id.btn_no_footer) {
            NoFooterActivity.openActivity(this);
        } else if (id == R.id.btn_grid_1) {
            Grid1Activity.openActivity(this);
        } else if (id == R.id.btn_grid_2) {
            Grid2Activity.openActivity(this);
        } else if (id == R.id.btn_expandable) {
            ExpandableActivity.openActivity(this);
        } else if (id == R.id.btn_various) {
            VariousActivity.openActivity(this);
        } else if (id == R.id.btn_various_child) {
            VariousChildActivity.openActivity(this);
        } else if (id == R.id.btn_binding_adapter) {
            BindingActivity.openActivity(this);
        } else if (id == R.id.btn_empty_list) {
            EmptyActivity.openActivity(this);
        }
    }
}
