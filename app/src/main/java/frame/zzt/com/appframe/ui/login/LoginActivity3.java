package frame.zzt.com.appframe.ui.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zzt.commonmodule.utils.ConfigARouter;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import frame.zzt.com.appframe.R;
import frame.zzt.com.appframe.ui.BaseAppCompatActivity;
import frame.zzt.com.appframe.ui.home.HomeActivity;
import frame.zzt.com.appframe.util.BuildHelper;
import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;

/**
 * Created by allen on 18/8/13.
 */

@Route(path = ConfigARouter.ACTIVITY_APP_LOGIN)
public class LoginActivity3<P extends BasePresenter> extends BaseAppCompatActivity implements LoginView {

    public Context context;
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
    public Button mEmailSignInButton;
    @BindView(R.id.tv_html)
    public TextView tv_html;
    @BindString(R.string.insurance_tarms_text12)
    public String htmlText;
    @BindViews({R.id.button1, R.id.button2})
    public List<Button> btnList;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.button3)
    Button button3;

    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.cb_test)
    CheckBox cb_test;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏系统虚拟按键
//        SystemUtil.hideBottomUIMenu(this);
//        SystemUtil.hideNavigationBar(this);
        context = this;
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);

//        mEmailView.setText("13797745363");
//        mPasswordView.setText("123456");

        System.out.println("----获取信号值：" + getProgressRssi(-30));

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.home_vehicle_tab_false);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.map_location_oval);
//        BitmapDrawable bd = (BitmapDrawable) drawable;

        Bitmap bm = drawableToBitamp(drawable);
        imageView.setImageBitmap(bitmap);

//        imageView.setImageDrawable(drawable);


        Log.w(TAG_BASE, "name:" + BuildHelper.getName());
        Log.w(TAG_BASE, "version:" + BuildHelper.getVersion());
        Log.w(TAG_BASE, "prop:" + BuildHelper.getProp("ro.build.version.emui"));
        Log.w(TAG_BASE, "prop:" + BuildHelper.getProp("ro.product.manufacturer"));
        Log.w(TAG_BASE, "是否是三星:" + BuildHelper.isSamsung());
        Log.w(TAG_BASE, "getMode:" + BuildHelper.getMode());
        Log.w(TAG_BASE, "getBrand:" + BuildHelper.getBrand());
        Log.w(TAG_BASE, "getProduct:" + BuildHelper.getProduct());
        Log.w(TAG_BASE, "getManufacturer:" + BuildHelper.getManufacturer());
        Log.w(TAG_BASE, "getHardWare:" + BuildHelper.getHardWare());
        Log.w(TAG_BASE, "DISPLAY:" + Build.DISPLAY);


        initView();

    }

    private void initView() {

        cb_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG_BASE, "cb_test - setOnClickListener");
            }
        });

        cb_test.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.w(TAG_BASE, "cb_test - setOnCheckedChangeListener");
            }
        });

    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        return bitmap;
    }

    public int getProgressRssi(int rssi) {
        return Math.round(rssi / Math.abs(-100));
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

    @OnClick(R.id.button1)
    public void login01() {
        presenter.login1();
    }

    @OnClick(R.id.button2)
    public void login02() {
        presenter.login2();
    }

    @OnClick(R.id.button3)
    public void login03() {
        presenter.login3();
    }

    @OnClick(R.id.button4)
    public void testBtn() {
        onLoginSucc();
    }


    @Override
    public void onLoginSucc() {
        showtoast("登录成功");
        Intent intent = new Intent();
        intent.setClass(this, HomeActivity.class);
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


}
