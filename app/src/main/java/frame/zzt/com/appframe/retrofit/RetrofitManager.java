package frame.zzt.com.appframe.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import frame.zzt.com.appframe.MyApplication;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by allen on 17/11/14.
 */
public class RetrofitManager {

    private String TAG = "ApiRetrofit";
    private String url_host = "http://admin.vipcare.com/";

    private static RetrofitManager instance;

    private RetrofitManager() {
    }

    public static synchronized RetrofitManager getInstance() {
        return instance != null ? instance : (instance = new RetrofitManager());
    }

    /**
     * 获取一个服务对象，使用Gson转换器，用于json数据的交互
     *
     * @return
     */
    public ApiServer create() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url_host)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(initClient())
                .build();
        return retrofit.create(ApiServer.class);
    }


    /**
     * 执行
     *
     * @param call
     * @param callback
     */
    public void enqueue(Call call, Callback callback) {
        if (!isNetConnected()) {
            MyApplication.getInstance().postWorkRunnable(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.getInstance(), "连接失败，请检查网络后重试", Toast.LENGTH_SHORT).show();
                }
            });
        }
        call.enqueue(callback);
    }


    /**
     * 判断网络状态
     *
     * @return
     */
    public boolean isNetConnected() {
        ConnectivityManager cm = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }


    /**
     * 初始化 超时  打印日志
     *
     * @return
     */
    private OkHttpClient initClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e( TAG ,"httpLog :" + message);
            }
        });
        loggingInterceptor.setLevel(level);

        // 添加公共的头信息
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("Cookie", "JSESSIONID")
                        .build();
                return chain.proceed(request);
            }
        };


        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
