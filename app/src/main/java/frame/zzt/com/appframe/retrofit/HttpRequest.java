package frame.zzt.com.appframe.retrofit;

import java.util.Map;

import frame.zzt.com.appframe.modle.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by allen on 18/8/13.
 */

public class HttpRequest {
    private static HttpRequest instance;

    public HttpRequest() {
    }

    public static synchronized HttpRequest getInstance() {
        return instance != null ? instance : (instance = new HttpRequest());
    }


    public void getLogin(Map<String, String> map, Callback<String> callback) {
        frame.zzt.com.appframe.retrofit.RetrofitManager manager = frame.zzt.com.appframe.retrofit.RetrofitManager.getInstance();
        Call<String> call = manager.create().getLogin(map);
        manager.enqueue(call, callback);
    }


    public void getLogin2(  Callback<LoginResponse> callback) {
        frame.zzt.com.appframe.retrofit.RetrofitManager manager = frame.zzt.com.appframe.retrofit.RetrofitManager.getInstance();
        Call<LoginResponse> call = manager.create().getLogin2();
        manager.enqueue(call, callback);
    }


    public void getLogin3(  String type, String phone, String password , Callback<LoginResponse> callback) {
        frame.zzt.com.appframe.retrofit.RetrofitManager manager = frame.zzt.com.appframe.retrofit.RetrofitManager.getInstance();
        Call<LoginResponse> call = manager.create().getLogin3(type , phone , password);
        manager.enqueue(call, callback);
    }



}
