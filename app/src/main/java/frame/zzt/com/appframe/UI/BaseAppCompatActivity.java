package frame.zzt.com.appframe.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import frame.zzt.com.appframe.Util.MyMessageUtil;
import frame.zzt.com.appframe.modle.BaseModel;
import frame.zzt.com.appframe.mvp.mvpbase.BaseView;

/**
 * Created by allen on 18/8/21.
 */

public class BaseAppCompatActivity extends AppCompatActivity implements BaseView {
    public final static String TAG_BASE =  BaseAppCompatActivity.class.getSimpleName() ;

    protected ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * @param s
     */
    public void showtoast(String s) {
        MyMessageUtil.getInstance(this).setMessage(s);
    }

    protected void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    protected void showLoadingDialog() {

        if (dialog == null) {
            dialog = new ProgressDialog(BaseAppCompatActivity.this);
        }
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }


    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    @Override
    public void showError(String msg) {
        showtoast(msg);
    }


    @Override
    public void onErrorCode(BaseModel model) {
        showtoast("网络错误" + model.getCode());
    }


}
