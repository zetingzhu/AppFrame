package frame.zzt.com.appframe.UI.login;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import frame.zzt.com.appframe.MyApplication;
import frame.zzt.com.appframe.greendao.DaoSession;
import frame.zzt.com.appframe.greendao.Location;
import frame.zzt.com.appframe.greendao.LocationDao;
import frame.zzt.com.appframe.greendao.User;
import frame.zzt.com.appframe.greendao.UserDao;
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

                DaoSession daoSession = MyApplication.getInstance().getDaoSession() ;

                UserDao userDao = daoSession.getUserDao();
                User user = new User();
                user.setEmail(baseView.getUserName());
                user.setPassword(baseView.getPassword());
                user.setTime(System.currentTimeMillis() + "" );
                long lon = userDao.insert(user);
                Log.i(TAG ,  "登录保存user数据：" + lon  ) ;

                LocationDao locationDao = daoSession.getLocationDao() ;
                Location loc = new Location();
                loc.setId(System.currentTimeMillis() );
                loc.setLat("1.1111111");
                loc.setLng("2.2222222");
                loc.setTime(System.currentTimeMillis() + "" );
                long lon1 = locationDao.insertOrReplace(loc);
                Log.i(TAG ,  "登录保存location数据：" + lon1  ) ;
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
                        Log.i(TAG ,  "获取的登录数据login：" + loginResponse.toString() ) ;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.i(TAG ,  "获取的失败login：" + e ) ;
//                        baseView.showError(e.getMessage());
                        baseView.onLoginSucc();
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG ,  "获取的成功login："  ) ;
                        baseView.onLoginSucc();
                    }
                });
        /**

         看似很完美, 但我们忽略了一点, 如果在请求的过程中Activity已经退出了, 这个时候如果回到主线程去更新UI, 那么APP肯定就崩溃了, 怎么办呢,
         上一节我们说到了Disposable , 说它是个开关, 调用它的dispose()方法时就会切断水管, 使得下游收不到事件, 既然收不到事件,
         那么也就不会再去更新UI了. 因此我们可以在Activity中将这个Disposable 保存起来, 当Activity退出时, 切断它即可.

         那如果有多个Disposable 该怎么办呢, RxJava中已经内置了一个容器CompositeDisposable,
         每当我们得到一个Disposable时就调用CompositeDisposable.add()将它添加到容器中,
         在退出的时候, 调用CompositeDisposable.clear() 即可切断所有的水管.

         */
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
