package com.zzt.myviewpager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zzt.viewpager2.R;

import java.util.List;

/**
 * @author: zeting
 * @date: 2020/12/30
 */
public class NestedVP2Adapter extends RecyclerView.Adapter<NestedVP2Adapter.MyHolderVp2> {

    List<String> vpList;
    String strTitle;

    public NestedVP2Adapter(List<String> vpList, String str) {
        this.vpList = vpList;
        this.strTitle = str;
    }

    @NonNull
    @Override
    public MyHolderVp2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nested_vp2_layout, parent, false);
        return new MyHolderVp2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderVp2 holder, int position) {
        holder.tv_vp2_name.setText(strTitle);
        holder.tv_vp2_content.setText(String.format("这个是内容：%s1", vpList.get(position)));
//        if (position % 2 == 0) {
//            holder.ll_vp2_bg.setBackgroundResource(R.color.papayawhip);
//        } else {
//            holder.ll_vp2_bg.setBackgroundResource(R.color.powderblue);
//        }
    }

    @Override
    public int getItemCount() {
        return vpList.size();
    }

    class MyHolderVp2 extends RecyclerView.ViewHolder {
        TextView tv_vp2_name;
        TextView tv_vp2_content;
        LinearLayout ll_vp2_bg;

        public MyHolderVp2(@NonNull View itemView) {
            super(itemView);
            tv_vp2_name = itemView.findViewById(R.id.tv_vp2_name);
            tv_vp2_content = itemView.findViewById(R.id.tv_vp2_content);
            ll_vp2_bg = itemView.findViewById(R.id.ll_vp2_bg);
        }
    }
}
