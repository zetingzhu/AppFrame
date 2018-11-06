package frame.zzt.com.appframe.rxbus;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * 有背压处理（Backpressure）的 Rxbus
 */

public class RxBusBackpressure {

    private static volatile RxBusBackpressure instance;
    private final FlowableProcessor<Object> mBus;

    private RxBusBackpressure() {
        // toSerialized method made bus thread safe
        mBus = PublishProcessor.create().toSerialized();
    }

    public static RxBusBackpressure getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = RxBusBackpressure.Holder.BUS;
                }
            }
        }
        return instance;
    }

    public void post(Object obj) {
        mBus.onNext(obj);
    }

    public <T> Flowable<T> toFlowable(Class<T> tClass) {
        return mBus.ofType(tClass);
    }

    public Flowable<Object> toFlowable() {
        return mBus;
    }

    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

    private static class Holder {
        private static final RxBusBackpressure BUS = new RxBusBackpressure();
    }

}
