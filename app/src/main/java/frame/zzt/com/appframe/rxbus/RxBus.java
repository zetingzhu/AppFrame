package frame.zzt.com.appframe.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 没有背压处理的
 * <p>
 * 发送数据
 * EventMsg eventMsg = new EventMsg();
 * eventMsg.setIndex(index);
 * eventMsg.setMsg("这是是从Activity界面发送过来的数据");
 * RxBus.getInstance().post(eventMsg);
 * <p>
 * 获取数据的时候
 * RxBus.getInstance().toObservable().map(new Function<Object, EventMsg>() {
 *
 * @Override public EventMsg apply(Object o) throws Exception {
 * return (EventMsg) o;
 * }
 * }).subscribe(new Consumer<EventMsg>() {
 * @Override public void accept(EventMsg eventMsg) throws Exception {
 * if (eventMsg != null) {
 * Log.i(TAG, "通过 RxBus 获取到的数据：" + eventMsg.toString() );
 * }
 * }
 * });
 */


public class RxBus {
    private static volatile RxBus instance;
    private final Subject<Object> mBus;

    private RxBus() {
        // toSerialized method made bus thread safe
        mBus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = Holder.BUS;
                }
            }
        }
        return instance;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Observable<T> toObservable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Observable<Object> toObservable() {
        return mBus;
    }

    public boolean hasObservers() {
        return mBus.hasObservers();
    }

    private static class Holder {
        private static final RxBus BUS = new RxBus();
    }
}
