package com.example.grouprecycle;

import android.graphics.Rect;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author: zeting
 * @date: 2020/8/19
 * 添加头部的点击事件
 */
public abstract class AddHeadClickListener implements RecyclerView.OnItemTouchListener {

    private static final String TAG = AddHeadClickListener.class.getSimpleName();

    //手势探测器
    private GestureDetectorCompat mGestureDetectorCompat;
    private RecyclerView mRecyclerView;
    private StickyHeaderItemDecoration itemDecoration;

    public AddHeadClickListener(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        mGestureDetectorCompat = new GestureDetectorCompat(mRecyclerView.getContext(), new HeadTouchHelperGestureListener());
        if (mRecyclerView != null && mRecyclerView.getItemDecorationCount() > 0) {
            itemDecoration = (StickyHeaderItemDecoration) mRecyclerView.getItemDecorationAt(0);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    /**
     * 是否在头部内
     *
     * @param rect
     * @param x
     * @param y
     * @return
     */
    public boolean findHeaderClickView(Rect rect, int x, int y) {
        if (rect != null) {
            if (rect.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    //点击
    public abstract void onItemClick(int position);


    public class HeadTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        // 普通的点击事件
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (itemDecoration != null) {
                Rect rect = itemDecoration.getStickyHeaderRect();
                if (findHeaderClickView(rect, (int) e.getX(), (int) e.getY())) {
                    Log.d(TAG, "头部被点击了 点中了头");
                    onItemClick(itemDecoration.getStickyHeaderPosition());
                    return true;
                }
            }
            return false;
        }

    }

}
