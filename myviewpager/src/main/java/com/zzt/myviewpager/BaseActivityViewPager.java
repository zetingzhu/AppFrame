package com.zzt.myviewpager;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

/**
 * @author: zeting
 * @date: 2020/1/7
 *  一个布局的整体控制
 */
public abstract class BaseActivityViewPager extends AppCompatActivity {
    private final static String TAG = BaseActivityViewPager.class.getSimpleName();
    protected String[] marray = new String[]{"1", "2", "3", "4", "5", "6", "7"};
    protected TabLayout tab_layout;
    protected Toast mToast;

    public abstract int getContextView();

    public abstract void setitem(int position);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContextView());


        findViewById(R.id.btn_skip).setVisibility(View.GONE);
        findViewById(R.id.recycleview).setVisibility(View.GONE);
        findViewById(R.id.btn_view_add).setVisibility(View.GONE);
        findViewById(R.id.btn_fragment_add).setVisibility(View.GONE);
        findViewById(R.id.btn_add).setVisibility(View.GONE);
        findViewById(R.id.btn_del).setVisibility(View.GONE);
        findViewById(R.id.btn_vertical).setVisibility(View.GONE);
        findViewById(R.id.btn_horizontal).setVisibility(View.GONE);
        findViewById(R.id.btn_depath).setVisibility(View.GONE);
        findViewById(R.id.btn_zoom).setVisibility(View.GONE);
        findViewById(R.id.btn_rotation).setVisibility(View.GONE);
        findViewById(R.id.btn_test).setVisibility(View.GONE);
        findViewById(R.id.btn_test2).setVisibility(View.GONE);


        tab_layout = findViewById(R.id.tab_layout);
        if (tab_layout != null) {
            for (int i = 0; i < marray.length; i++) {
                tab_layout.addTab(tab_layout.newTab().setText("Tab " + marray[i]));
            }
            tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    setitem(tab.getPosition());
                    Log.d(TAG, "onTabSelected:" + tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    Log.d(TAG, "onTabUnselected:" + tab.getPosition());
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    Log.d(TAG, "onTabReselected:" + tab.getPosition());
                }
            });
        }
    }


    public void showToast(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(BaseActivityViewPager.this, str, Toast.LENGTH_SHORT);
        }
        mToast.setText(str);
        mToast.setGravity(Gravity.CENTER, 0, 50);
        mToast.show();
    }


    public float dpTopx(float value) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        );
    }
}
