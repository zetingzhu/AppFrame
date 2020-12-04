package com.example.viewautosizelayout;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import me.jessyan.autosize.AutoSize;

/**
 * @author: zeting
 * @date: 2020/11/26
 */
public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
        // 这里可以使用AndroidAutoSize ，解决布局中有webview,这种自动适配会失效
        AutoSize.autoConvertDensityOfGlobal((Activity) getContext());
    }
}
