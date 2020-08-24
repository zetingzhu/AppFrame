package com.example.headerrecycleview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouprecycle.AddHeadClickListener;
import com.example.grouprecycle.GroupRecycleViewAdapter;
import com.example.grouprecycle.StickyHeaderItemDecoration;
import com.example.headerrecycleview.adapter.LinearInfoSectionAdapter;
import com.example.headerrecycleview.entity.ColumnField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zeting
 * @date: 2020/8/19
 */
public class LinearlayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        RecyclerView recyclerView = findViewById(R.id.recycleview);
        GroupRecycleViewAdapter groupAdapter = new GroupRecycleViewAdapter();
        LinearInfoSectionAdapter sectionAdapter1 = new LinearInfoSectionAdapter();
        sectionAdapter1.setHeader("基本信息");
        List<ColumnField> columnFields1 = new ArrayList<>();
        columnFields1.add(new ColumnField("标题", "测试"));
        columnFields1.add(new ColumnField("记录时间", "2019-04-16 15：36"));
        columnFields1.add(new ColumnField("记录人员", "陈小"));
        columnFields1.add(new ColumnField("信息内容", "卫生检查不合格"));
        sectionAdapter1.setContentData(columnFields1);
        sectionAdapter1.setFooter("结束");
        LinearInfoSectionAdapter sectionAdapter2 = new LinearInfoSectionAdapter();
        sectionAdapter2.setHeader("工作票信息");
        List<ColumnField> columnFields2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            columnFields2.add(new ColumnField("工作票信息" + i, "工作票" + i));
        }
        sectionAdapter2.setContentData(columnFields2);
        sectionAdapter2.setFooter("结束");
        LinearInfoSectionAdapter sectionAdapter3 = new LinearInfoSectionAdapter();
        sectionAdapter3.setHeader("多媒体信息");
        List<ColumnField> columnFields3 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            columnFields3.add(new ColumnField("多媒体文件" + i, "多媒体" + i));
        }
        sectionAdapter3.setContentData(columnFields3);

        groupAdapter.addSectionAdapter(sectionAdapter1);
        groupAdapter.addSectionAdapter(sectionAdapter2);
        groupAdapter.addSectionAdapter(sectionAdapter3);
        recyclerView.setAdapter(groupAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //标题悬浮吸顶 + 分割线
        recyclerView.addItemDecoration(new StickyHeaderItemDecoration(this, DividerItemDecoration.VERTICAL));
        // 设置头部点击事件
        recyclerView.addOnItemTouchListener(new AddHeadClickListener(recyclerView) {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(LinearlayoutActivity.this, "头部被点击了", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
