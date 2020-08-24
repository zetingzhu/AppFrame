package com.example.headerrecycleview.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headerrecycleview.R;
import com.example.headerrecycleview.entity.ColumnField;

/**
 * @author: zeting
 * @date: 2020/8/19
 */
public class LinearInfoViewHolder extends RecyclerView.ViewHolder {

    public TextView labelView;
    public TextView valueView;

    public LinearInfoViewHolder(@NonNull View itemView) {
        super(itemView);
        labelView = itemView.findViewById(R.id.label);
        valueView = itemView.findViewById(R.id.editValue);
    }

    public void render(ColumnField field) {
        labelView.setText(field.label);
        valueView.setText(field.value);
    }
}
