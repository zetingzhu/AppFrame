package com.zzt.myviewpager.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zzt.viewpager2.R;

import java.util.Arrays;
import java.util.List;

/**
 * @author: zeting
 * @date: 2020/12/30
 */
public class NestedAdapter extends RecyclerView.Adapter<NestedAdapter.MyHolder> {
    List<String[]> mList;

    public NestedAdapter(List<String[]> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nested_layout, parent, false);
        return new MyHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        NestedVP2Adapter vp2Adapter;
        if (position % 2 == 0) {
            holder.tv_nested_name.setText(String.format("横向 index：%s1", position));
            holder.tv_nested_vp2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
            holder.tv_nested_name.setBackgroundResource(R.color.plum);
            vp2Adapter = new NestedVP2Adapter(Arrays.asList(mList.get(position)), "这个是水平");
        } else {
            holder.tv_nested_name.setText(String.format("垂直 index：%s1", position));
            holder.tv_nested_vp2.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
            holder.tv_nested_name.setBackgroundResource(R.color.lawngreen);
            vp2Adapter = new NestedVP2Adapter(Arrays.asList(mList.get(position)), "这个是垂直");
        }
        holder.vp2_container.disallowParentInterceptDownEvent(false);
        holder.tv_nested_vp2.setAdapter(vp2Adapter);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv_nested_name;
        ViewPager2 tv_nested_vp2;
        ViewPager2Container vp2_container;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_nested_name = itemView.findViewById(R.id.tv_nested_name);
            tv_nested_vp2 = itemView.findViewById(R.id.tv_nested_vp2);
            vp2_container = itemView.findViewById(R.id.vp2_container);
        }
    }
}
