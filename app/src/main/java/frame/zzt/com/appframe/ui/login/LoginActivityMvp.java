package frame.zzt.com.appframe.ui.login;


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
import frame.zzt.com.appframe.mvp.mvpbase.BaseActivity;

public class LoginActivityMvp extends BaseActivity<LoginPresenter> implements LoginView {
    @BindView(R.id.email)
    public AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    public EditText mPasswordView;
    @BindView(R.id.login_form)
    public View mProgressView;
    @BindView(R.id.login_progress)
    public View mLoginFormView;
    @BindView(R.id.email_sign_in_button)
    public Button mEmailSignInButton;
    @BindView(R.id.tv_html)
    public TextView tv_html;
    @BindString(R.string.insurance_tarms_text12)
    public String htmlText;
    @BindViews({R.id.button1, R.id.button2})
    public List<Button> btnList;
    @BindView(R.id.button3)
    Button button3;

    @Override
    public void onLoginSucc() {
        showtoast("登录成功");
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        super.initView();
        ButterKnife.bind(this);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //示例代码，示例接口
                presenter.login();

            }
        });
    }


    @OnClick(R.id.email_sign_in_button)
    public void attemptLogin() {
        presenter.login();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
