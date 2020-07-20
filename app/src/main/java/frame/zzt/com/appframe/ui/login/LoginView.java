package frame.zzt.com.appframe.ui.login;


import frame.zzt.com.appframe.mvp.mvpbase.BaseView;

public interface LoginView extends BaseView {

    void onLoginSucc();

    String getUserName();

    String getPassword();

}
