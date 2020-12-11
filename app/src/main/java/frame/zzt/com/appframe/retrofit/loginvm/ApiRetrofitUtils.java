package frame.zzt.com.appframe.retrofit.loginvm;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import frame.zzt.com.appframe.ui.vm.HttpResponse;
import kotlin.jvm.functions.Function3;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * @author: zeting
 * @date: 2020/12/7
 * Retrofit 工具封装
 */
public class ApiRetrofitUtils {
    private static final String TAG = ApiRetrofitUtils.class.getSimpleName();

    private static ApiRetrofitUtils apiRetrofit;
    private static OkHttpClient client;
    private static Retrofit retrofit;

    public static ApiRetrofitUtils getInstance() {
        if (apiRetrofit == null) {
            synchronized (Object.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofitUtils();
                }
            }
        }
        return apiRetrofit;
    }


    private static synchronized OkHttpClient getOkHttpClient() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long startTime = System.currentTimeMillis();
                Response response = chain.proceed(chain.request());
                long endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                MediaType mediaType = response.body().contentType();
                String content = response.body().string();
                Log.e(TAG, "----------Request Start----------------");
                Log.e(TAG, "| " + request.toString() + request.headers().toString());
                Log.e(TAG, "| Response:" + content);
                Log.e(TAG, "----------Request End:" + duration + "毫秒----------");
                return response.newBuilder()
                        .body(ResponseBody.create(mediaType, content))
                        .build();
            }
        };
        return client = new OkHttpClient.Builder()
                //添加log拦截器
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public static synchronized Retrofit getRetrofit(String baseUrl) {
        return retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                //支持LiveData
                .addCallAdapterFactory(new LiveDataCallAdapterFactory1())
//                .addCallAdapterFactory(new LiveDataCallAdapterFactory(  ))
                .client(getOkHttpClient())
                .build();
    }

    public <T> T getApiService(String ShowUrl, Class<T> service) {
        Retrofit retrofit = getRetrofit(ShowUrl);
        T t = retrofit.create(service);
        return t;
    }

}