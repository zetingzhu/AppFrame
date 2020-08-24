package com.zzt.myviewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;


import com.zzt.myviewpager.data.ItemData;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: zeting
 * @date: 2020/1/7
 * Fragment 添加删除
 */
public class ActivityViewPagerAdd extends BaseActivityViewPager implements View.OnClickListener {

    private final static String TAG = ActivityViewPagerAdd.class.getSimpleName();
    ViewPager2 viewPager2;

    private Long nextValue = 1L;

    List<ItemData> itemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_add).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_del).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_del).setOnClickListener(this);

        findViewById(R.id.tab_layout).setVisibility(View.GONE);
        findViewById(R.id.viewpager1).setVisibility(View.GONE);

        for (int i = 1; i < 7; i++) {
            itemList.add(new ItemData("Item:" + i, nextValue));
            nextValue++;
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
        FragmentStateAdapter adapter2 = new FragmentStateAdapter(ActivityViewPagerAdd.this) {

            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return ItemFragment.newFragment(itemList.get(position).getContent(), 1);
            }

            @Override
            public int getItemCount() {
                return itemList.size();
            }

            @Override
            public long getItemId(int position) {
                /** 这个地方，需要重写  */
//                return super.getItemId(position);
                return itemList.get(position).getPage();
            }

            @Override
            public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
                super.onDetachedFromRecyclerView(recyclerView);
            }

            @Override
            public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
                super.onBindViewHolder(holder, position, payloads);
                Log.i(ItemFragment.TAG, "  onBindViewHolder      - index:" + itemList.get(position).getContent());
            }
        };
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setitem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                int item = viewPager2.getCurrentItem();
                addList(item, "添加Item:" + nextValue);
                viewPager2.getAdapter().notifyDataSetChanged();
                viewPager2.setCurrentItem(item, false);
                break;
            case R.id.btn_del:
                int item1 = viewPager2.getCurrentItem();
                if (itemList.size() > 0) {
                    itemList.remove(item1);
                    viewPager2.getAdapter().notifyDataSetChanged();
                }
                break;
        }
    }

    public void addList(int item, String str) {
        itemList.add(item, new ItemData(str, nextValue));
        nextValue++;
    }

}
