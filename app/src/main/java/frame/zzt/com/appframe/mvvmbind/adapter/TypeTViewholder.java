package frame.zzt.com.appframe.mvvmbind.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import frame.zzt.com.appframe.databinding.ItemRecycleType2Binding;

/**
 * 第二个类型设配器
 */
public class TypeTViewholder extends RecyclerView.ViewHolder {
    ItemRecycleType2Binding binding;

    public TypeTViewholder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public ItemRecycleType2Binding getBinding() {
        return binding;
    }


}
