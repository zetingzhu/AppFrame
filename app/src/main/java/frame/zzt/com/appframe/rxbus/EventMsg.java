package frame.zzt.com.appframe.rxbus;

/**
 * RxBus 消息处理类
 */

public class EventMsg {
    private int index;
    private String msg;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "EventMsg{" +
                "index=" + index +
                ", msg='" + msg + '\'' +
                '}';
    }
}