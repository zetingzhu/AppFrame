package frame.zzt.com.appframe.UI.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;

/**
 * Created by allen on 18/8/29.
 */

public class ActivityRxJava extends Activity {

    private String TAG = "ActivityRxJava";
    @BindView(R.id.button1)
    public Button mButton1;

    @BindView(R.id.imageView1)
    public ImageView imageView;

    @BindView(R.id.recycleview)
    public RecyclerView mRecyclerView;

    private List<String> mList;
    AdapterRecycle mAdapterRecycle;
    MyOnClick mOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rx_java);
        ButterKnife.bind(this);

        mList = new ArrayList<>();
        mList.add("1");
        mList.add("2");


        mAdapterRecycle = new AdapterRecycle();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapterRecycle);
        mOnClick = new MyOnClick() {
            @Override
            public void onClickListener(int position) {
                Log.i(TAG, "点击的是：" + position);
                switch (position){
                    case 1:

                        break;
                    case 2:
                        break;

                }
            }
        };
    }

    public class AdapterRecycle extends RecyclerView.Adapter<DateHolder> {

        @Override
        public DateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle, parent, false);
            DateHolder viewHolder = new DateHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(DateHolder holder, final int position) {
            holder.mTextview.setText(mList.get(position));
            holder.mTextview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClick.onClickListener(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    public class DateHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView)
        TextView mTextview;

        public DateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface MyOnClick {
        void onClickListener(int position);
    }

}
