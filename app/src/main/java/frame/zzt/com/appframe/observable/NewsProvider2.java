package frame.zzt.com.appframe.observable;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 对于报社的抽象，实现了被观察者接口，每隔2s发送一次报纸
 */
public class NewsProvider2 extends Observable {
    private static final long DELAY = 2 * 1000;

    public NewsProvider2() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            private int titleCount = 1;
            private int contentCount = 1;

            @Override
            public void run() {
                setChanged(); //调用setChagned方法，将changed域设置为true，这样才能通知到观察者们
                notifyObservers(new NewsModel("NewsProvider2 title:" + titleCount++, "content:" + contentCount++));
            }
        }, DELAY, 1000);
    }
}
