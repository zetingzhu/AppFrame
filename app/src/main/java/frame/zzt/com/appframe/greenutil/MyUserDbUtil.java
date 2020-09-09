package frame.zzt.com.appframe.greenutil;

import android.content.Context;

import frame.zzt.com.appframe.MyApplication;
import frame.zzt.com.appframe.greendao.DaoSession;
import frame.zzt.com.appframe.greendao.User;
import frame.zzt.com.appframe.greendao.UserDao;

/**
 * Created by allen on 18/8/7.
 */

public class MyUserDbUtil {


    private UserDao userDao;

    private static MyUserDbUtil instance = null;

    public static synchronized MyUserDbUtil getInstance(Context mContext) {
        if (instance == null) {
            instance = new MyUserDbUtil(mContext);
        }
        return instance;
    }

    public UserDao getUserDao() {
        if (userDao == null) {

        }
        return userDao;
    }

    private MyUserDbUtil(Context context) {
        DaoSession daoSession = ((MyApplication) context.getApplicationContext()).getDaoSession();
        userDao = daoSession.getUserDao();
    }

    public void insertUser(User mUser) {
        userDao.insert(mUser);
    }

}
