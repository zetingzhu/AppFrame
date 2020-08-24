package com.zzt.myviewpager;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author: zeting
 * @date: 2020/1/10
 */
public class MyVH extends RecyclerView.ViewHolder {
    Button button;
    LinearLayout item_pager_bg;

    public MyVH(@NonNull View itemView) {
        super(itemView);
        button = itemView.findViewById(R.id.button);
        item_pager_bg = itemView.findViewById(R.id.item_pager_bg);
    }
}
