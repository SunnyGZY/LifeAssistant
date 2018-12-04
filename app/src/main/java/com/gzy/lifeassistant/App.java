package com.gzy.lifeassistant;

import android.app.Application;

import com.gzy.lifeassistant.model.db.DaoMaster;
import com.gzy.lifeassistant.model.db.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * App
 *
 * @author gaozongyang
 * @date 2018/12/3
 */
public class App extends Application {

    private DaoSession daoSession;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 初始化GreenDao
        initGreenDao();
    }

    /**
     * 外部获取单例
     *
     * @return Application
     */
    public static App getInstance() {
        return instance;
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "life-assistant-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
