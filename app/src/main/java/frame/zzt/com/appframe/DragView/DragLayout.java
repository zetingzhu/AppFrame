package frame.zzt.com.appframe.DragView;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 添加子view 可以上下滑动
 *
 * @author: zeting
 * @date: 2020/3/2
 */
public class DragLayout extends LinearLayout {
    private static final String TAG = DragLayout.class.getSimpleName();

    private View dragView;// 可以拖动的View
    // 滑动监听
    private ViewDragHelper viewDragHelper;
    /**
     * 上下文对象
     */
    private Context mContext;

    // 布局的宽度
    private int layoutWidth;
    // 滑动布局宽度
    private int dragViewWidth;
    // 滑动布局高度
    private int dragViewHeight;

    public DragLayout(@NonNull Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {

        viewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() {

            @Override
            public boolean tryCaptureView(View child, int pointerId) {
//                return child == dragView;
                return true;
            }

            /**
             * 当View停止拖拽的时候调用的方法
             * @param releasedChild
             * @param xvel
             * @param yvel
             */
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);

            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return layoutWidth - child.getWidth();
            }

            /**
             * 垂直滑动
             * @param child
             * @param top
             * @param dy
             * @return
             */
            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                Log.i(TAG, "----left:" + child.getLeft() + "- top:" + top + "- dy:" + dy);
//                dragView.layout(layoutWidth - child.getWidth(), top, layoutWidth, top + child.getHeight());

                //控制子view拖曳不能超过最顶部
                int paddingTop = getPaddingTop();
                if (top < paddingTop) {
                    return paddingTop;
                }

                //控制子view不能越出底部的边界。
                int pos = getHeight() - child.getHeight();
                if (top > pos) {
                    return pos;
                }
                return top;
            }

        });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.AT_MOST);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec), MeasureSpec.AT_MOST);

        this.dragView = getChildAt(0);
//        dragView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
//        int dragHeight = dragView.getMeasuredHeight();
//        int dragWidth = dragView.getMeasuredWidth();
//        setMeasuredDimension(dragWidth, dragHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        layoutWidth = getMeasuredWidth();
        dragViewWidth = dragView.getMeasuredWidth();
        dragViewHeight = dragView.getMeasuredHeight();

        dragView.layout(layoutWidth - dragViewWidth, b - dragViewHeight, r, b);
    }
}
