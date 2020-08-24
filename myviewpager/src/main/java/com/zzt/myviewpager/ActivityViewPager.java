package com.zzt.myviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


/**
 * @author: zeting
 * @date: 2020/1/7
 */
public class ActivityViewPager extends BaseActivityViewPager {

    private final static String TAG = ActivityViewPager.class.getSimpleName();
    ViewPager2 viewPager2;
    ViewPager viewPager1;

    RecyclerView recycleview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        initviewViewpager1();
        initviewViewpager2();
        initviewRecycleView();

        // tablayout 和viewpager 绑定
        tab_layout.setupWithViewPager(viewPager1);
        // tablayout 和viewpager2 绑定
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tab_layout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("Tab " + marray[position]);
            }
        });
        tabLayoutMediator.attach();

        // 动画和方向
        findViewById(R.id.btn_skip).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_skip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewPager.this, ActivityViewPagerFragment.class));
            }
        });

        // 动态添加view
        findViewById(R.id.btn_view_add).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_view_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewPager.this, ActivityViewPagerViewAdd.class));
            }
        });

        // 动态添加fragment
        findViewById(R.id.btn_fragment_add).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_fragment_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityViewPager.this, ActivityViewPagerAdd.class));
            }
        });

    }

    private void initviewViewpager1() {
        viewPager1 = findViewById(R.id.viewpager1);
        viewPager1.setVisibility(View.VISIBLE);
        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return marray.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view = LayoutInflater.from(ActivityViewPager.this).inflate(R.layout.item_viewpager, null);
                Button button = view.findViewById(R.id.button);
                button.setText("Viewpager:  " + marray[position]);
                button.setTag(position);
                button.setAllCaps(false);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("viewpager1 点击了：" + marray[(int) v.getTag()]);
                    }
                });
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "Tab " + marray[position];
            }
        };
        viewPager1.setAdapter(pagerAdapter);
        viewPager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                showToast("viewpager1 滑动选中了第几个：" + position);
                setitem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initviewViewpager2() {
        viewPager2 = findViewById(R.id.viewpager2);
        viewPager2.setVisibility(View.VISIBLE);
        RecyclerView.Adapter adapter2 = new RecyclerView.Adapter<MyVH>() {

            @NonNull
            @Override
            public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ActivityViewPager.this).inflate(R.layout.item_viewpager, parent, false);
                /**
                 * java.lang.IllegalStateException: Pages must fill the whole ViewPager2 (use match_parent)
                 */
//                View view = LayoutInflater.from(ActivityViewPager.this).inflate(R.layout.item_viewpager, null);
                /**
                 * java.lang.IllegalStateException: ViewHolder views must not be attached when created.
                 * Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)
                 */
//                View view = LayoutInflater.from(ActivityViewPager.this).inflate(R.layout.item_viewpager, parent);

                MyVH myVH = new MyVH(view);
                return myVH;
            }

            @Override
            public void onBindViewHolder(@NonNull MyVH holder, int position) {
                holder.button.setText("Viewpager2:  " + marray[position]);
                holder.button.setTag(position);
                holder.button.setAllCaps(false);
                holder.item_pager_bg.setBackgroundColor(ContextCompat.getColor(ActivityViewPager.this, android.R.color.holo_blue_dark));
                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("viewpager2 点击了：" + marray[(int) v.getTag()]);
                    }
                });

            }

            @Override
            public int getItemCount() {
                return marray.length;
            }
        };

        // 设置滚动方向
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter2);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                showToast("viewpager2 滑动选中了第几个：" + position);
                setitem(position);
            }
        });


        /**
         setAdapter() 设置适配器
         setOrientation() 设置布局方向
         setCurrentItem() 设置当前Item下标
         beginFakeDrag() 开始模拟拖拽
         fakeDragBy() 模拟拖拽中
         endFakeDrag() 模拟拖拽结束
         setUserInputEnabled() 设置是否允许用户输入/触摸
         setOffscreenPageLimit()设置屏幕外加载页面数量
         registerOnPageChangeCallback() 注册页面改变回调
         setPageTransformer() 设置页面滑动时的变换效果
         */
    }

    private void initviewRecycleView() {
        recycleview = findViewById(R.id.recycleview);
        recycleview.setVisibility(View.VISIBLE);
        recycleview.setLayoutManager(new LinearLayoutManager(ActivityViewPager.this, RecyclerView.HORIZONTAL, false));
        RecyclerView.Adapter adapter = new RecyclerView.Adapter<MyVH>() {

            @NonNull
            @Override
            public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ActivityViewPager.this).inflate(R.layout.item_viewpager, parent, false);
//                View view = LayoutInflater.from(ActivityViewPager.this).inflate(R.layout.item_viewpager, parent);
                MyVH myVH = new MyVH(view);
                return myVH;
            }

            @Override
            public void onBindViewHolder(@NonNull MyVH holder, int position) {
                holder.button.setText("recycleview:  " + marray[position]);
                holder.button.setAllCaps(false);
                holder.button.setTag(position);
                holder.button.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
                holder.item_pager_bg.setBackgroundColor(ContextCompat.getColor(ActivityViewPager.this, android.R.color.holo_purple));
            }

            @Override
            public int getItemCount() {
                return marray.length;
            }
        };
        recycleview.setAdapter(adapter);
    }


    @Override
    public int getContextView() {
        return R.layout.activity_viewpager;
    }

    @Override
    public void setitem(int position) {
//        viewPager1.setCurrentItem(position);
//        viewPager2.setCurrentItem(position);
//        tab_layout.getTabAt(position).select();
        recycleview.smoothScrollToPosition(position);
    }


}
