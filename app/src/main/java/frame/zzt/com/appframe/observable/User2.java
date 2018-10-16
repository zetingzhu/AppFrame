package frame.zzt.com.appframe.observable;

import java.util.Observable;
import java.util.Observer;

/**
 * 对于用户的抽象
 */
public class User2 implements Observer {
    private String mName;

    public User2(String name) {
        mName = name;
    }

    @Override
    public void update(Observable observable, Object data) {
        NewsModel model = (NewsModel) data;
        System.out.println(mName + " receive news:" + model.getTitle() + "  " + model.getContent());
    }
}
