package frame.zzt.com.appframe.observable;

/**
 * 对于用户的抽象
 */
public class User implements MyObserver {
    private String mName;

    public User(String name) {
        mName = name;
    }

    @Override
    public void receiveNews(NewsModel model) {
        System.out.println(mName + " receive news:" + model.getTitle() + "  " + model.getContent());
    }
}
