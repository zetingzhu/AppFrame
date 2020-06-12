package frame.zzt.com.appframe.singleList;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by lin on 2017/2/20.
 * 可以设置头部以及尾部的adapter
 * 子类继承时注意getItemViewType
 */

public abstract class RecyclerAdapter extends RecyclerView.Adapter {

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private View headerView;
    private View bottomView;
    private OnViewHolderCreatedListener listener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER)
            return new HeaderViewHolder(headerView);
        else if (viewType == ITEM_TYPE_BOTTOM)
            return new BottomViewHolder(bottomView);
        else
            return onCreateHolder(parent, viewType);
    }

    @Override
    public int getItemCount() {
        return (headerView == null ? 0 : 1) + getContentItemCount() + (bottomView == null ? 0 : 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position))
            return ITEM_TYPE_HEADER;
        else if (isBottomView(position))
            return ITEM_TYPE_BOTTOM;
        else
            return ITEM_TYPE_CONTENT;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isBottomView(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    public View getHeaderView() {
        return headerView;
    }

    public void addHeaderView(View view) {
        headerView = view;
    }

    public void addBottomView(View view) {
        bottomView = view;
    }

    public boolean isHeaderView(int position) {
        return headerView != null && position < 1;
    }

    public boolean isBottomView(int position) {
        return bottomView != null && position >= ((headerView == null ? 0 : 1) + getContentItemCount());
    }

    public int convert(int position) {
        return position - (getHeaderView() == null ? 0 : 1);
    }

    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    public abstract int getContentItemCount();

    public abstract Object getItem(int position);

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
            if (listener != null)
                listener.onHeaderViewCreated();
        }
    }

    class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setOnViewHolderCreatedListener(OnViewHolderCreatedListener listener) {
        this.listener = listener;
    }

    public interface OnViewHolderCreatedListener {
        void onHeaderViewCreated();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        public RecyclerHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(itemClickDelegate);
        }
    }

    private View.OnClickListener itemClickDelegate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                int position = ((RecyclerView.LayoutParams) v.getLayoutParams()).getViewLayoutPosition();
                position -= getHeaderView() == null ? 0 : 1;
                itemClickListener.onItemClick(getItem(position), v, position);
            }
        }
    };

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(Object object, View view, int position);
    }
}
