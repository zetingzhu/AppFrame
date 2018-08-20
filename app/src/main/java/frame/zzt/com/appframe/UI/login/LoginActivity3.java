package frame.zzt.com.appframe.UI.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.UI.home.HomeActivity;
import frame.zzt.com.appframe.Util.MyMessageUtil;
import frame.zzt.com.appframe.modle.BaseModel;
import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;

/**
 * Created by allen on 18/8/13.
 */

public class LoginActivity3<P extends BasePresenter> extends AppCompatActivity implements LoginView {

    public Context context;
    private ProgressDialog dialog;
    protected LoginPresenter presenter;

    @BindView(R.id.email)
    public AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    public EditText mPasswordView;
    @BindView(R.id.login_form)
    public View mProgressView;
    @BindView(R.id.login_progress)
    public View mLoginFormView;
    @BindView(R.id.email_sign_in_button)
    public Button mEmailSignInButton ;
    @BindView(R.id.tv_html)
    public TextView tv_html ;
    @BindString(R.string.insurance_tarms_text12)
    public String htmlText ;
    @BindViews({R.id.button1 , R.id.button2})
    public List<Button> btnList ;
    @BindView(R.id.button3)
    Button button3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter =  new LoginPresenter(this);

        mEmailView.setText("13797745363");
        mPasswordView.setText("123456");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @OnClick(R.id.email_sign_in_button)
    public void attemptLogin() {
        // TODO 调用登录方法
//        presenter.login(mEmailView.getText().toString() , mPasswordView.getText().toString());
        presenter.login();
    }

    @OnClick( R.id.button1)
    public void login01(){
        presenter.login1();
    }

    @OnClick( R.id.button2)
    public void login02(){
        presenter.login2();
    }

    @OnClick( R.id.button3)
    public void login03(){
        presenter.login3();
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
    public void onLoginSucc() {
        showtoast("登录成功");
        Intent intent = new Intent();
        intent.setClass(this , HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public String getUserName() {
        return mEmailView.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordView.getText().toString();
    }

    @Override
    public void onErrorCode(BaseModel model) {
        showtoast("网络错误"+model.getCode()  );
    }


    /**
     * @param s
     */
    public void showtoast(String s) {
        MyMessageUtil.getInstance(this).setMessage(s);
    }

    private void closeLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    private void showLoadingDialog() {

        if (dialog == null) {
            dialog = new ProgressDialog(context);
        }
        dialog.setCancelable(false);
        dialog.show();
    }
}
