package com.gzy.lifeassistant;

import android.app.Application;

/**
 * App
 *
 * @author gaozongyang
 * @date 2018/12/3
 */
public class App extends Application {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    /**
     * 外部获取单例
     *
     * @return Application
     */
    public static App getInstance() {
        return instance;
    }
}
