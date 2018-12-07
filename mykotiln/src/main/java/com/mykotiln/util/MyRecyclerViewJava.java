package com.mykotiln.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mykotiln.R;

import java.util.List;


/**
 * Created by allen on 18/10/11.
 */

public class MyRecyclerViewJava extends RecyclerView {


    private AdapterRecycle mAdapterRecycle ;
    private Context mContext ;
    private List<MyRecycleListItem> mList;
    private MyRecycleOnClick mOnClick;



    public MyRecyclerViewJava(Context context) {
        super(context);
        initView(context);
    }

    public MyRecyclerViewJava(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public MyRecyclerViewJava(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }


    public void initView(Context mContext ){
        this.mContext = mContext ;
    }


    public void setMyAdapter(List<MyRecycleListItem> mList ,  MyRecycleOnClick mOnClick){
        this.mList = mList ;
        this.mOnClick = mOnClick ;

        mAdapterRecycle = new AdapterRecycle();
        setLayoutManager(new LinearLayoutManager(mContext));
        //添加Android自带的分割线
        addItemDecoration(new DividerItemDecoration(mContext , DividerItemDecoration.VERTICAL));
        setAdapter(mAdapterRecycle);

    }

    public class AdapterRecycle extends Adapter<DateHolder> {

        @Override
        public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
            DateHolder viewHolder = new DateHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DateHolder holder, final int position) {
            holder.mTextview.setText(mList.get(position).getItemId() +" : "+ mList.get(position).getItemValue());
            holder.mTextview.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClick.onClickListener(mList.get(position).getItemId());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public class DateHolder extends ViewHolder {

        TextView mTextview;

        public DateHolder(View itemView) {
            super(itemView);
            mTextview = (TextView) itemView.findViewById(R.id.textView);
        }
    }

}
