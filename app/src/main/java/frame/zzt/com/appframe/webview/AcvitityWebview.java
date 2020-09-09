package frame.zzt.com.appframe.webview;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;

/**
 * Created by zeting
 * Date 18/12/10.
 */

public class AcvitityWebview extends BaseAppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        loadPage();

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //下面两个设置禁止手指缩放效果
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);

        // 开启H5(APPCache)缓存功能
        settings.setAppCacheEnabled(false);
        // 应用可以有数据库
//        settings.setDatabaseEnabled(true);
        // 开启 DOM storage 功能
//        settings.setDomStorageEnabled(true);
        // 根据网络连接情况，设置缓存模式，
//        if (Networks.getInstance().isNetConnected()) {
//            settings.setCacheMode(WebSettings.LOAD_DEFAULT);// 根据cache-control决定是否从网络上取数据
//        } else {
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);// 先查找缓存，没有的情况下从网络获取。
//        }
//        // 可以读取文件缓存(manifest生效)
        settings.setAllowFileAccess(false);


        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.addJavascriptInterface(new JsCallAndroidMethod(), "android");
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                //网页完成加载，去掉加载提示框

            }

        });
    }

    /**
     * 提供js调用android方法
     */
    private class JsCallAndroidMethod {
        /*
         * 判断SDk是否大于18   蓝牙才能用
         * */
        @JavascriptInterface
        public boolean judgmentVersion() {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) { // SDK小于18
                return false;
            }
            return true;
        }

    }


    /**
     * 加载页面
     */
    private void loadPage() {

        String url = "https://weidian.com/item.html?itemID=2094394648";
        mWebView.loadUrl(url);

    }// end loadPage
}
