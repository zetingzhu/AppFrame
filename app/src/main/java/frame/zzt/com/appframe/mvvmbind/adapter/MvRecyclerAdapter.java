package frame.zzt.com.appframe.mvvmbind.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.databinding.ItemRecycleType1Binding;
import frame.zzt.com.appframe.databinding.ItemRecycleType3Binding;

public class MvRecyclerAdapter extends RecyclerView.Adapter {
    private List<ItemData> mList = new ArrayList<>();

    public MvRecyclerAdapter(List<ItemData> mList) {
        if (mList != null && !mList.isEmpty()) {
            this.mList = mList;
        }
    }

    public void notifyAdapter(List<ItemData> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ItemData.TYPE_1:
                ItemRecycleType1Binding type1Binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recycle_type_1, parent, false);
                return new TypeOViewholder(type1Binding);
            case ItemData.TYPE_2:
                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_type_2, parent, false);
                return new TypeTViewholder(view2);
            default:
                ItemRecycleType3Binding type3Binding = ItemRecycleType3Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new TypeTHViewholder(type3Binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemData itemData = mList.get(position);
        if (holder instanceof TypeOViewholder) {
            ((TypeOViewholder) holder).getBinding().setItemdata(itemData);
            ((TypeOViewholder) holder).getBinding().executePendingBindings();
        } else if (holder instanceof TypeTViewholder) {
            ((TypeTViewholder) holder).getBinding().setItemdata(itemData);
            ((TypeTViewholder) holder).getBinding().executePendingBindings();
        } else {
            ((TypeTHViewholder) holder).getBinding().setItemdata(itemData);
            ((TypeTHViewholder) holder).getBinding().executePendingBindings();
        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
