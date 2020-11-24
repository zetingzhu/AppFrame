package frame.zzt.com.appframe.observable;

/**
 * 被观察者接口定义
 */
public interface MyObserverable {

    void register(MyObserver myObserver);

    void remove(MyObserver myObserver);

    void removes();

    void send(NewsModel model);

}