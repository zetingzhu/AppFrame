package com.example.databindsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by lin on 2018/1/5.
 */

public class BadgeViewUtil {

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }

}
