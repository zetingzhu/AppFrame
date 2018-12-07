package frame.zzt.com.appframe.eventbus;

/**
 * Created by zeting
 * Date 18/11/28.
 */

public class MessageEvent {
    public String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
