package com.example.databindsample;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author: zeting
 * @date: 2020/6/2
 * recycleview 网格分割线
 */
public class RecycleGridSpaceDivider extends RecyclerView.ItemDecoration {
    private int mHorizontalSpacing = 0;
    private int mVerticalSpacing = 0;

    public RecycleGridSpaceDivider(int mHorizontalSpacing, int mVerticalSpacing) {
        this.mHorizontalSpacing = mHorizontalSpacing;
        this.mVerticalSpacing = mVerticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        //得到View的位置
        int position = parent.getChildAdapterPosition(view);
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            int spanCount = layoutManager.getSpanCount();
            int column = position % spanCount;
            outRect.left = mHorizontalSpacing * column / spanCount;
            outRect.right = mHorizontalSpacing * (spanCount - 1 - column) / spanCount;
            if (position >= spanCount) {
                outRect.top = mVerticalSpacing;
            }
//            Log.e("RecycleGridSpaceDivider", "mHorizontalSpacing===" + outRect.left);
//            Log.e("RecycleGridSpaceDivider", "mHorizontalSpacing===" + outRect.right);
//            Log.e("RecycleGridSpaceDivider", "mHorizontalSpacing===" + mHorizontalSpacing);
//            Log.e("RecycleGridSpaceDivider", "column===" + column);
//            Log.e("RecycleGridSpaceDivider", "RecycleGridSpaceDivider===" + spanCount);

        }
    }
}
