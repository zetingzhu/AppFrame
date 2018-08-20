package frame.zzt.com.appframe.Retrofit.httpbase;

import java.io.Serializable;

/**
 * Created by allen on 18/8/13.
 */

public class BaseModel<T> implements Serializable {
    private int errcode;
    private String errmsg;
    private T result;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}