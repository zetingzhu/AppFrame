package frame.zzt.com.appframe.networkopt;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import frame.zzt.com.appframe.MyApplication;

/**
 * Created by zeting
 * Date 19/7/23.
 */

public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();

    public static volatile NetworkManager instance;

    /**
     * 所有的注解方法
     */
    private Map<Object, List<MethodManager>> networkList;

    public static NetworkManager getInstance() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }


    public void init() {
        //
        networkList = new HashMap<>();

        // 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager cmgr = (ConnectivityManager) MyApplication.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cmgr != null) {
                cmgr.registerNetworkCallback(request, networkCallback);
            }
        } else {

        }

    }


    /**
     * 同时分发
     *
     * @param networkType
     */
    public void post(NetworkType networkType) {
        Set<Object> setObj = networkList.keySet();
        // 获取对象，activityNetwork 对象
        for (Object get : setObj) {
            // 所有注解方法
            List<MethodManager> methodList = networkList.get(get);
            if (methodList != null) {
                // 循环所有方法
                for (MethodManager methodManager : methodList) {
                    if (methodManager.getType().isAssignableFrom(networkType.getClass())) {
                        switch (methodManager.getNetType()) {
                            case AUTO:
                                invoke(methodManager, get, networkType);
                                break;
                            case WIFI:
                                invoke(methodManager, get, networkType);
                                break;
                            case GPRS:
                                invoke(methodManager, get, networkType);
                                break;
                            case NONE:
                                invoke(methodManager, get, networkType);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager, Object getter, NetworkType netType) {

        try {
            // activity 中直接参数为 NetworkType 的方法
            methodManager.getMethod().invoke(getter, netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 注册
     *
     * @param obj
     */
    public void registerObserver(Object obj) {
        List<MethodManager> methodList = networkList.get(obj);
        if (methodList == null) {
            methodList = findAnnotationMethod(obj);
            networkList.put(obj, methodList);
        }
    }

    /**
     * 查找类中的方法
     *
     * @param obj
     * @return
     */
    private List<MethodManager> findAnnotationMethod(Object obj) {
        List<MethodManager> methodList = new ArrayList<>();

        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            // 获取注解方法
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }
            // 方法参数校验
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "只有一个参数");
            }

//            method.getTypeParameters()

            // 过滤方法
            MethodManager methodManager = new MethodManager(method, network.netType(), parameterTypes[0]);
            methodList.add(methodManager);
        }

        return methodList;
    }

    /**
     * 移除
     *
     * @param obj
     */
    public void unRegisterObserver(Object obj) {
        if (!networkList.isEmpty()) {
            networkList.remove(obj);
        }
        Log.i(TAG, obj.getClass().getSimpleName() + "注销成功");
    }
}
