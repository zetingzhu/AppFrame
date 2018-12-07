package com.mykotiln.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mykotiln.R;
import com.mykotiln.util.MyRecycleListItem;
import com.mykotiln.util.MyRecyclerViewJava;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Activity 类的创建
 */
public class ActivityJava extends BaseActivity {

    private KotilnPersenter mPer;
    private RecyclerView rv_kotlin_list ;
    private List<MyRecycleListItem> mList ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        mPer = new KotilnPersenter(this);
        initView();
    }

    private void initView() {
        rv_kotlin_list = (MyRecyclerViewJava) findViewById(R.id.rv_kotlin_list);

//        mList = new ArrayList<>();
//        mList.add( new MyRecycleListItem(0, "第一列点击事件"));
//        rv_kotlin_list.setMyAdapter(mList, new MyRecyclerViewJava.MyRecycleOnClick() {
//            @Override
//            public void onClickListener(int position) {
//                switch (position){
//                    case 0:
//                        mPer.showToast("点击了第一列");
//                        break;
//                }
//            }
//        });
//        mPer.setItemOperate(rv_kotlin_list);

        View.OnClickListener mcl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        rv_kotlin_list.setOnClickListener(mcl);

    }

}
