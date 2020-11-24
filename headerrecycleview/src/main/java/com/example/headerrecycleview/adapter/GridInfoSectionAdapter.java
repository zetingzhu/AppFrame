package com.example.headerrecycleview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grouprecycle.SectionDelegateAdapter;
import com.example.headerrecycleview.R;
import com.example.headerrecycleview.entity.ColumnField;


/**
 * @author: zeting
 * @date: 2020/8/19
 */
public class GridInfoSectionAdapter extends SectionDelegateAdapter {


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(@NonNull ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_detail_header, viewGroup, false);
        return new HeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(@NonNull ViewGroup viewGroup) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(@NonNull ViewGroup viewGroup) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_grid_detail_item, viewGroup, false);
        return new GridInfoViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, Object headerObject) {
        String title = (String) headerObject;
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
        headerViewHolder.render(title);
    }

    @Override
    public void onBindFooterViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, Object footerObject) {
    }

    @Override
    public void onBindContentViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int contentPostion, Object contentObject) {
        String label = (String) contentObject;
        GridInfoViewHolder gridInfoViewHolder = (GridInfoViewHolder) viewHolder;
        gridInfoViewHolder.render(label);
    }
}
