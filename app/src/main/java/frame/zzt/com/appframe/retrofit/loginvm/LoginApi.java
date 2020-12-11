package frame.zzt.com.appframe.retrofit.loginvm;

import androidx.lifecycle.LiveData;

import java.util.HashMap;
import java.util.Map;

import frame.zzt.com.appframe.ui.vm.HttpResponse;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * @author: zeting
 * @date: 2020/12/7
 */
public interface LoginApi {

    String baseUrl = "http://test-m.daily-fx.net";

    @FormUrlEncoded
    @POST("/user/info/check/account/number/status?")
    LiveData<HttpResponse<Integer>> getLoginStatus1(@Field("telCode") String telCode, @Field("mobileNum") String mobileNum);

    @FormUrlEncoded
    @POST("/user/info/check/account/number/status?")
    LiveData<HttpResponse<Integer>> getLoginStatus2(@FieldMap Map<String, String> body);

    @FormUrlEncoded
    @POST("/user/info/check/account/number/status?")
    LiveData<HttpResponse<Integer>> getLoginStatus3(@FieldMap Map<String, String> body);


    static LoginApi getApi() {
        return ApiRetrofitUtils.getInstance().getApiService(baseUrl, LoginApi.class);
    }

}
