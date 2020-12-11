package frame.zzt.com.appframe.retrofit.loginvm

import androidx.lifecycle.LiveData
import frame.zzt.com.appframe.ui.vm.HttpResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * @author: zeting
 * @date: 2020/12/7
 */
interface LoginKotlinApi {
    companion object {
        fun getApi(): LoginKotlinApi {
            return ApiRetrofitUtils.getInstance().getApiService(LoginApi.baseUrl, LoginKotlinApi::class.java)
        }
    }

    @FormUrlEncoded
    @POST("/user/info/check/account/number/status?")
    fun getLoginStatus3(@FieldMap body: Map<String, String>): LiveData<HttpResponse<Int>>

}