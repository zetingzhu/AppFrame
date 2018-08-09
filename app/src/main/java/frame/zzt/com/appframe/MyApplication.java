package frame.zzt.com.appframe;

import android.app.Application;

import frame.zzt.com.appframe.greenUtil.MyGreenUtil;
import frame.zzt.com.appframe.greendao.DaoSession;

/**
 * Created by allen on 18/8/7.
 */

public class MyApplication extends Application {

    MyGreenUtil mMyGreenUtil ;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyGreenUtil = new MyGreenUtil() ;
    }

    public DaoSession getDaoSession() {
        return mMyGreenUtil.getDaoSession(getApplicationContext());
    }


}
