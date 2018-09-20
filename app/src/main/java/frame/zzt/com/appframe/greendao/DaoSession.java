package frame.zzt.com.appframe.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import frame.zzt.com.appframe.greendao.Location;
import frame.zzt.com.appframe.greendao.User;

import frame.zzt.com.appframe.greendao.LocationDao;
import frame.zzt.com.appframe.greendao.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig locationDaoConfig;
    private final DaoConfig userDaoConfig;

    private final LocationDao locationDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        locationDaoConfig = daoConfigMap.get(LocationDao.class).clone();
        locationDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        locationDao = new LocationDao(locationDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(Location.class, locationDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        locationDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public LocationDao getLocationDao() {
        return locationDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}