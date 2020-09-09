package frame.zzt.com.appframe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import frame.zzt.com.appframe.bluetooth.ActivityBluetooth7;

/**
 * 自定义的 ScrollView
 * Created by zeting
 * Date 18/12/24.
 * <p>
 * Android设置ScrollView回到顶部的三种方式
 * 一、ScrollView.scrollTo(0,0)  直接置顶，瞬间回到顶部，没有滚动过程，其中Y值可以设置为大于0的值，使Scrollview停在指定位置;
 * <p>
 * 二、ScrollView.fullScroll(View.FOCUS_UP)  类似于手动拖回顶部,有滚动过程;
 * <p>
 * 三、ScrollView.smoothScrollTo(0, 0) 类似于手动拖回顶部,有滚动过程，其中Y值可以设置为大于0的值，使Scrollview停在指定位置。
 */

public class MyScrollView extends ScrollView {

    public final static String TAG = ActivityBluetooth7.class.getSimpleName();

    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;

    private IScrollChangedListener mSmartScrollChangedListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScanScrollChangedListener(IScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, " - ACTION_DOWN ");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, " - ACTION_MOVE ");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, " - ACTION_UP ");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        Log.d(TAG , " - scrollY：" + scrollY + " - clampedY:" + clampedY ) ;
        if (clampedY) {
            if (scrollY <= 0) {
                isScrolledToTop = clampedY;
                isScrolledToBottom = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = clampedY;
            }
            notifyScrollChangedListeners();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d(TAG, " - l：" + l + " - t:" + t + " - oldl:" + oldl + " - oldt:" + oldt);
        if (android.os.Build.VERSION.SDK_INT < 9) {  // API 9及之后走onOverScrolled方法监听
            if (getScrollY() == 0) {    // 小心踩坑1: 这里不能是getScrollY() <= 0
                isScrolledToTop = true;
                isScrolledToBottom = false;
            } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
                // 小心踩坑2: 这里不能是 >=
                // 小心踩坑3（可能忽视的细节2）：这里最容易忽视的就是ScrollView上下的padding　
                isScrolledToBottom = true;
                isScrolledToTop = false;
            } else {
                isScrolledToTop = false;
                isScrolledToBottom = false;
            }
            notifyScrollChangedListeners();
        }
        // 有时候写代码习惯了，为了兼容一些边界奇葩情况，上面的代码就会写成<=,>=的情况，结果就出bug了
        // 我写的时候写成这样：getScrollY() + getHeight() >= getChildAt(0).getHeight()
        // 结果发现快滑动到底部但是还没到时，会发现上面的条件成立了，导致判断错误
        // 原因：getScrollY()值不是绝对靠谱的，它会超过边界值，但是它自己会恢复正确，导致上面的计算条件不成立
        // 仔细想想也感觉想得通，系统的ScrollView在处理滚动的时候动态计算那个scrollY的时候也会出现超过边界再修正的情况
    }

    private void notifyScrollChangedListeners() {
//        Log.d(TAG , " - notifyScrollChangedListeners -  Top:" + isScrolledToTop + "-  Bottom:" + isScrolledToBottom  ) ;
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }

    /**
     * 定义监听接口
     */
    public interface IScrollChangedListener {
        void onScrolledToBottom();

        void onScrolledToTop();
    }


}
