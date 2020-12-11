package frame.zzt.com.appframe.ui.vm;

import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lin on 2018/8/29.
 */
public class HttpResponse<T> implements Serializable {

    private boolean success;
    private String errorCode;
    private String errorInfo;
    private T data;

    public HttpResponse(String errorCode, String errorInfo, T data) {
        this.success = true;
        this.errorCode = errorCode;
        this.errorInfo = errorInfo;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", errorInfo='" + errorInfo + '\'' +
                ", data=" + data +
                '}';
    }
}
