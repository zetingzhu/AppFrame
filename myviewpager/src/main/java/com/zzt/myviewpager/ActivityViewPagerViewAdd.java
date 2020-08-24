package com.zzt.myviewpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import java.util.ArrayList;
import java.util.List;


/**
 * @author: zeting
 * @date: 2020/1/7
 * View 添加删除
 */
public class ActivityViewPagerViewAdd extends BaseActivityViewPager implements View.OnClickListener {

    private final static String TAG = ActivityViewPagerViewAdd.class.getSimpleName();
    ViewPager2 viewPager2;

    List<String> itemList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_add).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_del).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_del).setOnClickListener(this);
        findViewById(R.id.btn_test).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_test2).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_test2).setOnClickListener(this);

        findViewById(R.id.tab_layout).setVisibility(View.GONE);
        findViewById(R.id.viewpager1).setVisibility(View.GONE);


        for (int i = 1; i < 7; i++) {
            itemList.add("Item:" + i);
        }

        initviewViewpager2();

    }

    @Override
    public int getContextView() {
        return R.layout.activity_viewpager;
    }

    @Override
    public void setitem(int position) {
    }


    private void initviewViewpager2() {
        viewPager2 = findViewById(R.id.viewpager2);
        viewPager2.setVisibility(View.VISIBLE);
        RecyclerView.Adapter adapter2 = new RecyclerView.Adapter<MyVH>() {

            @NonNull
            @Override
            public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ActivityViewPagerViewAdd.this).inflate(R.layout.item_viewpager, parent, false);
                MyVH myVH = new MyVH(view);
                return myVH;
            }

            @Override
            public void onBindViewHolder(@NonNull MyVH holder, int position) {
                holder.button.setText(itemList.get(position));
                holder.button.setTag(position);
                holder.item_pager_bg.setBackgroundColor(ContextCompat.getColor(ActivityViewPagerViewAdd.this, android.R.color.holo_blue_dark));
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("viewpager2 点击了：" + itemList.get((int) v.getTag()));
                    }
                });

                /**设置布局间距*/
                int padding = (int) dpTopx(10);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.item_pager_bg.getLayoutParams();
                layoutParams.setMargins(padding, 0, padding, 0);
                holder.item_pager_bg.setLayoutParams(layoutParams);
            }

            @Override
            public int getItemCount() {
                return itemList.size();
            }
        };
        // 设置滚动方向
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter2);

        /**设置显示viewpager 显示位置大小*/
        int padding = (int) dpTopx(50);
        RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
        recyclerView.setPadding(padding, 0, padding, 0);
        // 设置裁剪，并且有其他子类填充
        recyclerView.setClipToPadding(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                int item = viewPager2.getCurrentItem();
                itemList.add(item, "前面添加：" + itemList.size());
                viewPager2.getAdapter().notifyItemInserted(item);
                break;
            case R.id.btn_del:
                int item1 = viewPager2.getCurrentItem();
                itemList.remove(item1);
                viewPager2.getAdapter().notifyItemRangeRemoved(item1, 1);
                break;
            case R.id.btn_test:
                viewPager2.beginFakeDrag();
                viewPager2.fakeDragBy(10);
                break;
            case R.id.btn_test2:
                viewPager2.beginFakeDrag();
                viewPager2.fakeDragBy(-10);
                break;
        }
    }
}
