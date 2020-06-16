package frame.zzt.com.appframe.mvvmbind.adapter;

import androidx.recyclerview.widget.RecyclerView;

import frame.zzt.com.appframe.databinding.ItemRecycleType3Binding;

/**
 * 第二个类型设配器
 */
public class TypeTHViewholder extends RecyclerView.ViewHolder {
    ItemRecycleType3Binding binding;

    public TypeTHViewholder(ItemRecycleType3Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ItemRecycleType3Binding getBinding() {
        return binding;
    }


}
