package frame.zzt.com.appframe.ui.vm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;
import java.util.Map;

import frame.zzt.com.appframe.modle.LoginResponse;
import frame.zzt.com.appframe.retrofit.loginvm.ApiRetrofitUtils;
import frame.zzt.com.appframe.retrofit.loginvm.LoginApi;
import frame.zzt.com.appframe.retrofit.loginvm.RequestBody;
import kotlinx.coroutines.Dispatchers;
import retrofit2.Call;
import retrofit2.Callback;

import static frame.zzt.com.appframe.ui.BaseAppCompatActivity.TAG_BASE;

/**
 * @author: zeting
 * @date: 2020/12/7
 */
public class LoginVm extends ViewModel {

    MutableLiveData<HttpResponse<Integer>> loginStatus;

    // 获取登录状态
    public MutableLiveData<HttpResponse<Integer>> getLoginStatus() {
        if (loginStatus == null) {
            loginStatus = new MutableLiveData<>();
        }
        return loginStatus;
    }

    public void requestLoginStatus(String telCode, String mobileNum) {
        HashMap<String, String> map = new HashMap<>();
        map.put("telCode", telCode);
        map.put("username", mobileNum);

        LiveData<HttpResponse<Integer>> bannerList = LoginApi.getApi().getLoginStatus3(RequestBody.getParamMap(map));
        if (bannerList != null) {
            Log.w(TAG_BASE, "这里返回数据" + bannerList.getValue());
        }
//        getLoginStatus().postValue(bannerList.getValue());
    }

}
