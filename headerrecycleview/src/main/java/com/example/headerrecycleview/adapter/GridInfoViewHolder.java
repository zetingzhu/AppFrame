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
public class GridInfoViewHolder extends RecyclerView.ViewHolder {

    public TextView labelView;
    public GridInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        labelView = itemView.findViewById(R.id.label);
    }

    public void render(String title){
        labelView.setText(title);
    }

}
