package frame.zzt.com.appframe.retrofit;

import java.util.Map;

import frame.zzt.com.appframe.modle.LoginResponse;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by allen on 18/8/13.
 */

public interface ApiServer {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json; charset=utf-8"
    })
    @GET("TestToolforApp/Login/login")
    Call<String> getLogin(@QueryMap Map<String, String> map);

    @GET("TestToolforApp/Login/login")
    Observable<LoginResponse> Login1(@Query("phone") String phone, @Query("password") String password);

    @GET("TestToolforApp/Login/login?phone=13797745363&password=123456")
    Call<LoginResponse> getLogin2();

    @GET("TestToolforApp/login/{type}")
    Call<LoginResponse> getLogin3(@Path("type") String type,@Query("phone") String phone, @Query("password") String password);

    @FormUrlEncoded //POST请求必须添加
    @POST("/login?")
    Call<LoginResponse> postData(@Field("username") String phone,@Field("password") String passwrod);

    @FormUrlEncoded
    @POST("/login?")
    Call<LoginResponse> postMapData(@FieldMap Map<String,String> map);


    // http://api.map.baidu.com/telematics/v3/weather?location=上海&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ

}