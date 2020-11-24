package frame.zzt.com.appframe.retrofit;

import java.util.Map;

import frame.zzt.com.appframe.modle.LoginResponse;
import frame.zzt.com.appframe.modle.MyWeather;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

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
    Call<LoginResponse> getLogin3(@Path("type") String type, @Query("phone") String phone, @Query("password") String password);

    @FormUrlEncoded //POST请求必须添加
    @POST("/login?")
    Call<LoginResponse> postData(@Field("username") String phone, @Field("password") String passwrod);

    @FormUrlEncoded
    @POST("/login?")
    Call<LoginResponse> postMapData(@FieldMap Map<String, String> map);


    // http://api.map.baidu.com/telematics/v3/weather?location=上海&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ

    /**
     * 天气预报
     */
    // https://search.heweather.com/find?location=北京&key=4b61a68895b149f1a5ea53fe43782e17
    @GET("find?key=4b61a68895b149f1a5ea53fe43782e17")
    Observable<MyWeather> getWheatherBeijin(@Query("location") String location);

    /**
     * 天气预报
     */
    //https://search.heweather.com/top?group=cn&key=4b61a68895b149f1a5ea53fe43782e17&number=20
    @GET("top?key=4b61a68895b149f1a5ea53fe43782e17")
    Observable<MyWeather> getWheatherGroup(@Query("group") String group, @Query("number") String number);


    /**
     * 下载文件  @Streaming注解可用于下载大文件
     */
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);

}