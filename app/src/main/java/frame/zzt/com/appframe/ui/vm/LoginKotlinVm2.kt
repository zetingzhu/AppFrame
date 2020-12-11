package frame.zzt.com.appframe.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import frame.zzt.com.appframe.retrofit.loginvm.LoginKotlinApi

/**
 * @author: zeting
 * @date: 2020/12/7
 *
 */
class LoginKotlinVm2 : ViewModel() {
    private val requestMap = MutableLiveData<Map<String, String>>()
    private val api = LoginKotlinApi.getApi()
    var loginStatus: LiveData<HttpResponse<Int>> = Transformations.switchMap(requestMap) {
        api.getLoginStatus3(it)
    }

    var LoginData: LiveData<Int> = Transformations.map(loginStatus) {

        it.data
    }

    fun loadData(hashMap: HashMap<String, String>) {
        requestMap.value = hashMap
    }
}