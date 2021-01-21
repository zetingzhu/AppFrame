package frame.zzt.com.appframe.test;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.webkit.WebView;

import frame.zzt.com.appframe.R;


/**
 * @author: zeting
 * @date: 2021/1/4
 * 验证修改屏幕密度后，验证webview 会修改应用密度
 */
public class OptionTradeSignInRules extends Dialog {
    String TAG = getClass().getSimpleName();
    private View rootView;
    private WebView wb_content;
    private String loadUrl = "";
    private int dialogWidth;
    private int dialogWidth1;

    public static void show(Context context, String url) {
        OptionTradeSignInRules signInRules = new OptionTradeSignInRules(context, url);
        signInRules.show();
    }

    public OptionTradeSignInRules(Context context, String url) {
        super(context);
        this.loadUrl = url;
    }

    /**
     * getScreenDensity   : 获取屏幕密度
     */
    public static float getScreenDensity() {

        return Resources.getSystem().getDisplayMetrics().density;
    }

    /**
     * getScreenDensityDpi: 获取屏幕密度 DPI
     */
    public static int getScreenDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics displaysMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaysMetrics);
        Log.d(TAG, "对话框 00 屏幕数据：" + displaysMetrics.density);

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        Log.d(TAG, "对话框 00 屏幕数据：" + displayMetrics.density);

        dialogWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.margin_520dp);
        Log.d(TAG, "对话框 1 屏幕数据：" + dialogWidth + " - " + getScreenDensityDpi() + " - " + getScreenDensity() + " " + (520 * 3.0));
        dialogWidth1 = getDimensionPixelOffset(R.dimen.margin_520dp);
        Log.d(TAG, "对话框 11 屏幕数据：" + dialogWidth1 + " - " + getScreenDensityDpi() + " - " + getScreenDensity());

        rootView = View.inflate(getContext(), R.layout.dialog_sign_in_rules, null);

        dialogWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.margin_520dp);
        Log.d(TAG, "对话框 2 屏幕数据：" + dialogWidth + " - " + getScreenDensityDpi() + " - " + getScreenDensity());
        dialogWidth1 = getDimensionPixelOffset(R.dimen.margin_520dp);
        Log.d(TAG, "对话框 21 屏幕数据：" + dialogWidth1 + " - " + getScreenDensityDpi() + " - " + getScreenDensity());

        DisplayMetrics displayMetrics22 = getContext().getResources().getDisplayMetrics();
        Log.d(TAG, "对话框 22 屏幕数据：" + displayMetrics22.density);

        setContentView(rootView);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }


    public int getDimensionPixelOffset(int id) throws Resources.NotFoundException {
        final TypedValue value = new TypedValue();
        return TypedValue.complexToDimensionPixelOffset(value.data, Resources.getSystem().getDisplayMetrics());
    }

}
