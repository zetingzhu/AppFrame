package frame.zzt.com.appframe.mvvmbind.adapter;

import androidx.recyclerview.widget.RecyclerView;

import frame.zzt.com.appframe.databinding.ItemRecycleType1Binding;

/**
 * 第一个类型设配器
 */
public class TypeOViewholder extends RecyclerView.ViewHolder {
    ItemRecycleType1Binding binding;

    public ItemRecycleType1Binding getBinding() {
        return binding;
    }

    public TypeOViewholder(ItemRecycleType1Binding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

}
