package com.gzy.lifeassistant;

import android.app.Application;

/**
 * MyApplication
 *
 * @author gaozongyang
 * @date 2018/12/3
 */
public class MyApplication extends Application {

//    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化GreenDao
        initGreenDao();
    }

    private void initGreenDao() {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "life-assistant-db");
//        Database db = helper.getWritableDb();
//        daoSession = new DaoMaster(db).newSession();
    }

//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
}
