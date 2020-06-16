package com.mykotiln.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.mykotiln.R;
import com.mykotiln.util.MyRecyclerViewJava;

/**
 * Java Activity 类的创建
 */
public class ActivityJava extends BaseActivity {

    private KotilnPersenter mPer;
    private MyRecyclerViewJava rv_kotlin_list ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        mPer = new KotilnPersenter(this);
        initView();
    }

    private void initView() {
        rv_kotlin_list = (MyRecyclerViewJava) findViewById(R.id.rv_kotlin_list);
        mPer.setItemOperateJava(rv_kotlin_list);

    }

}
