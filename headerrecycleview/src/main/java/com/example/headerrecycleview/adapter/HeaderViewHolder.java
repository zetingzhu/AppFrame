package com.example.headerrecycleview.adapter;

import android.view.View;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouprecycle.SectionDelegateAdapter;
import com.example.headerrecycleview.R;
import com.example.headerrecycleview.entity.ColumnField;

/**
 * @author: zeting
 * @date: 2020/8/19
 */
public class HeaderViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public HeaderViewHolder(@NonNull View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.header_title);
    }

    public void render(String title){
        titleView.setText(title);
    }
}
