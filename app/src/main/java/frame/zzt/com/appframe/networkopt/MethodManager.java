package frame.zzt.com.appframe.networkopt;

import java.lang.reflect.Method;

/**
 * Created by zeting
 * Date 19/7/23.
 */

public class MethodManager {
    private Class<?> type;// 参数类型
    private NetworkType netType;//网络类型
    private Method mMethod;// 方法名


    public MethodManager(Method method, NetworkType netType, Class<?> type) {
        mMethod = method;
        this.netType = netType;
        this.type = type;

        //
//        mMethod.invoke();
    }

    public Method getMethod() {
        return mMethod;
    }

    public void setMethod(Method method) {
        mMethod = method;
    }

    public NetworkType getNetType() {
        return netType;
    }

    public void setNetType(NetworkType netType) {
        this.netType = netType;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }
}
