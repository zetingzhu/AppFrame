package frame.zzt.com.appframe.greenUtil;

import android.content.Context;

import frame.zzt.com.appframe.greendao.DaoMaster;
import frame.zzt.com.appframe.greendao.DaoSession;


/**
 * 数据库dao的操作类
 */
public class MyGreenUtil {

    private DaoMaster mDaoMaster;
    private String DatabasePath =  "appFrameDb"  ;
    private DaoSession mDaoSession;
    private DaoMaster.OpenHelper mOpenHelper;

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public DaoMaster getDaoMaster(Context context) {
        if (mDaoMaster == null) {
            mOpenHelper = new DaoMaster.DevOpenHelper(context, DatabasePath, null);
            mDaoMaster = new DaoMaster(mOpenHelper.getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public DaoSession getDaoSession(Context context) {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster(context);
            }
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    /**
     * 关闭
     */
    public void closeDaoSession() {
        if (mDaoSession != null) {
            mDaoSession.clear();
            mDaoSession = null;
        }
    }

    public void closeHelper() {
        if (mOpenHelper != null) {
            mOpenHelper.close();
            mOpenHelper = null;
        }
    }

    /**
     * 关闭数据库连接
     */
    public void closeDBConncetion() {
        closeDaoSession();
        closeHelper();
    }


}
