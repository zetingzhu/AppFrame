package com.example.grouprecycle;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: zeting
 * @date: 2020/8/19
 *  分组适配器 ViewHolder
 */
public interface IDelegateAdapter {

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type);

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position);

    public int getItemViewType(int position);

    public int getItemCount();
}
