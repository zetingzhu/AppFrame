package frame.zzt.com.appframe.UI.login;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import frame.zzt.com.appframe.modle.LoginResponse;
import frame.zzt.com.appframe.modle.BaseObserver;
import frame.zzt.com.appframe.mvp.mvpbase.BasePresenter;
import frame.zzt.com.appframe.retrofit.HttpRequest;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by allen on 18/8/13.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    public final static String TAG = LoginActivity3.class.getSimpleName() ;

    HttpRequest http ;

    public LoginPresenter(LoginView baseView) {
        super(baseView);
        http = HttpRequest.getInstance();
    }

    public void login(){
        baseView.showLoading();
        Log.i(TAG ,  "登录获取的用户名：" + baseView.getUserName() + "-密码：" + baseView.getPassword() ) ;

        Map map = new HashMap();
        map.put("phone", baseView.getUserName());
        map.put("password", baseView.getPassword());

        http.getLogin(map, new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG ,  "获取的成功 login3：" + response.body()   ) ;
                baseView.onLoginSucc();
                baseView.hideLoading();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.i(TAG ,  "获取的失败 login3："   ) ;
                baseView.showError("向服务端请求数据超时，请稍后重试");
                baseView.hideLoading();
            }
        });
    }

    public void login1 () {
//        addDisposable(apiServer.Login1(baseView.getUserName() , baseView.getPassword() ), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(Object o) {
//                Log.i(TAG ,  "获取的成功login：" + o .toString()) ;
//                baseView.onLoginSucc();
//
//            }
//
//            @Override
//            public void onError(String msg) {
//                Log.i(TAG ,  "获取的失败login：" + msg ) ;
//                baseView.showError(msg);
//
//            }
//        });

        apiServer.Login1(baseView.getUserName(), baseView.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull LoginResponse loginResponse) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        new DisposableObserver(){

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    public void login2(){
        baseView.showLoading();
        Log.i(TAG ,  "登录获取的用户名：" + baseView.getUserName() + "-密码：" + baseView.getPassword() ) ;

        Map map = new HashMap();
        map.put("phone", baseView.getUserName());
        map.put("password", baseView.getPassword());

        http.getLogin2( new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.i(TAG ,  "获取的成功 login3：" + response.body()   ) ;
                baseView.onLoginSucc();
                baseView.hideLoading();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG ,  "获取的失败 login3："   ) ;
                baseView.showError("向服务端请求数据超时，请稍后重试");
                baseView.hideLoading();
            }
        });
    }

    public void login3(){
        baseView.showLoading();
        Log.i(TAG ,  "登录获取的用户名：" + baseView.getUserName() + "-密码：" + baseView.getPassword() ) ;

        Map map = new HashMap();
        map.put("phone", baseView.getUserName());
        map.put("password", baseView.getPassword());

        http.getLogin3( "login" ,baseView.getUserName() , baseView.getPassword(), new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.i(TAG ,  "获取的成功 login3：" + response.body()   ) ;
                baseView.onLoginSucc();
                baseView.hideLoading();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.i(TAG ,  "获取的失败 login3："   ) ;
                baseView.showError("向服务端请求数据超时，请稍后重试");
                baseView.hideLoading();
            }
        });
    }


}
