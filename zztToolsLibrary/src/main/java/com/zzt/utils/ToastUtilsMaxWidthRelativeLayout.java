package com.zzt.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author: zeting
 * @date: 2021/1/7
 */
public class ToastUtilsMaxWidthRelativeLayout extends RelativeLayout {

    private static final int SPACING = SizeUtils.dp2px(80);

    public ToastUtilsMaxWidthRelativeLayout(Context context) {
        super(context);
    }

    public ToastUtilsMaxWidthRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ToastUtilsMaxWidthRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMaxSpec = MeasureSpec.makeMeasureSpec(ScreenUtils.getAppScreenWidth(getContext()) - SPACING, MeasureSpec.AT_MOST);
        super.onMeasure(widthMaxSpec, heightMeasureSpec);
    }
}
