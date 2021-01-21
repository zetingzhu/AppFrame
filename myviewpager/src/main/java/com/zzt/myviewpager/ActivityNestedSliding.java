package com.zzt.myviewpager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zzt.myviewpager.adapter.NestedAdapter;
import com.zzt.viewpager2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 嵌套滑动
 */
public class ActivityNestedSliding extends AppCompatActivity {
    private RecyclerView rv_outer_layer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_sliding);
        rv_outer_layer = findViewById(R.id.rv_outer_layer);

        List<String[]> mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add(new String[]{"数据:" + i + "_1", "数据:" + i + "_2", "数据:" + i + "_3", "数据:" + i + "_5", "数据:" + i + "_7", "数据:" + i + "_9"});
        }
        NestedAdapter nestedAdapter = new NestedAdapter(mList);
        rv_outer_layer.setLayoutManager(new LinearLayoutManager(ActivityNestedSliding.this, LinearLayoutManager.VERTICAL, false));
        rv_outer_layer.setAdapter(nestedAdapter);

    }
}
