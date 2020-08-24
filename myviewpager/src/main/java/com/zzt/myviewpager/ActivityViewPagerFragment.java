package com.zzt.myviewpager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;
import androidx.viewpager2.widget.ViewPager2;


import com.zzt.myviewpager.anim.DepthPageTransformer;
import com.zzt.myviewpager.anim.RotationPageTransformer;
import com.zzt.myviewpager.anim.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: zeting
 * @date: 2020/1/7
 * 跳转 , 滑动方向 ，动画 ，生命周期
 */
public class ActivityViewPagerFragment extends BaseActivityViewPager implements View.OnClickListener {

    private final static String TAG = ActivityViewPagerFragment.class.getSimpleName();
    ViewPager2 viewPager2;
    ViewPager viewPager1;

    List<Fragment> fragmentList1 = new ArrayList<>();
    List<Fragment> fragmentList2 = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_horizontal).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_vertical).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_horizontal).setOnClickListener(this);
        findViewById(R.id.btn_vertical).setOnClickListener(this);

        for (int i = 0; i < marray.length; i++) {
            fragmentList1.add(ItemFragment.newFragment("" + i, 0));
            fragmentList2.add(ItemFragment.newFragment("" + i, 1));
        }


        findViewById(R.id.btn_depath).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_zoom).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_rotation).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_depath).setOnClickListener(this);
        findViewById(R.id.btn_zoom).setOnClickListener(this);
        findViewById(R.id.btn_rotation).setOnClickListener(this);

        initviewViewpager1();
        initviewViewpager2();
    }

    @Override
    public int getContextView() {
        return R.layout.activity_viewpager;
    }

    @Override
    public void setitem(int position) {
        viewPager1.setCurrentItem(position);
        viewPager2.setCurrentItem(position);
        tab_layout.getTabAt(position).select();
    }

    private void initviewViewpager1() {
        viewPager1 = findViewById(R.id.viewpager1);
        FragmentPagerAdapter adapter1 = new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public int getCount() {
                return marray.length;
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
//                return ItemFragment.newFragment(marray, position, 0);
                return fragmentList1.get(position);
            }
        };
        viewPager1.setAdapter(adapter1);
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showToast("viewpager fragment 1 滑动选中了第几个：" + position);
                setitem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initviewViewpager2() {
        viewPager2 = findViewById(R.id.viewpager2);
        FragmentStateAdapter adapter2 = new FragmentStateAdapter(ActivityViewPagerFragment.this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
//                return ItemFragment.newFragment(marray, position, 1);
                return fragmentList2.get(position);
            }

            @Override
            public int getItemCount() {
                return marray.length;
            }

            @Override
            public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
                super.onBindViewHolder(holder, position, payloads);
                Log.i(ItemFragment.TAG, "  onBindViewHolder      - index:" + position);
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
                showToast("viewpager fragment 2 滑动选中了第几个：" + position);
                setitem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        /**
         * 关闭预加载
         */
//        ((RecyclerView) viewPager2.getChildAt(0)).getLayoutManager().setItemPrefetchEnabled(false);

        /**
         *  默认缓存数量
         */
//        ((RecyclerView) viewPager2.getChildAt(0)).setItemViewCacheSize(0);

        /**
         * 离屏加载
         */
//        viewPager2.setOffscreenPageLimit(1);

        /**
         * 添加动画
         */
//        viewPager2.setPageTransformer(new DepthPageTransformer());
//        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_horizontal:
                viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                break;
            case R.id.btn_vertical:
                viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
                break;
            case R.id.btn_depath:
                viewPager2.setPageTransformer(new DepthPageTransformer());
                break;
            case R.id.btn_zoom:
                viewPager2.setPageTransformer(new ZoomOutPageTransformer());
                break;
            case R.id.btn_rotation:
                viewPager2.setPageTransformer(new RotationPageTransformer());
                break;
        }
    }


}
